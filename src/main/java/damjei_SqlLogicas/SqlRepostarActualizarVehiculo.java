/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_SqlLogicas;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * Actualizamos el kilometraje del vehiculo cada vez que se haga un repostage y
 * comprobamos si tiene revisiones
 *
 * @author Ivimar
 */
public class SqlRepostarActualizarVehiculo {

    public static final String MANTENIMIENTO_NOK = " Tiene Revisiones pendientes por Mantenimiento";
    public static final String MANTENIMIENTO_OK = " No hay Revisiones de Mantenimiento pendientes ";
    public static final String DELVEHICULO = " del vehículo ";

    boolean conexio = false;
    boolean controlAviso = false;
    boolean estadoRevision = false;
    String avisoOK;
    String avisoNOK;
    String resultado;

    Gson gson = new Gson();

    ConexionBD cn = new ConexionBD();
    Connection con = cn.establecerConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;

    public SqlRepostarActualizarVehiculo() {
    }

    /**
     * Actualizo los kilometros del vehiculo con los datos del repostaje
     *
     * @param kilometros
     * @param idvehiculo
     * @return
     * @throws SQLException
     */
    public String SqlRepostarActualizarVehiculos(float kilometros, int idvehiculo) throws SQLException {
        float Kilometros_actuales = kilometros;
        int mantenimientoid = 0;
        int idrevision = 0;

        String sqlActr = """
                    UPDATE public.vehiculos
                     SET  kilometros_actuales=?
                     WHERE idvehiculo =? """;

        ps = con.prepareStatement(sqlActr);
        ps.setFloat(1, Kilometros_actuales);
        ps.setInt(2, idvehiculo);

        int result = ps.executeUpdate();

        if (result != 1) {
            
            ps.close(); // Cerrar el PreparedStatement
            cn.cerrarConexion();
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("correcte", conexio);
            return resultado = gson.toJson(obtResposta);

            } else {
            
            String sqlVh = "SELECT fecha_alta FROM public.vehiculos WHERE idvehiculo = ?";

            ps = con.prepareStatement(sqlVh);
            ps.setInt(1, idvehiculo);
            rs = ps.executeQuery();

            if (!rs.next()) {
                ps.close(); // Cerrar el PreparedStatement
                cn.cerrarConexion();
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", conexio);
                return resultado = gson.toJson(obtResposta);

            } else {

                Date fecha_alta = rs.getDate(1);

                //obtenmos las fechas y kilometros de las revisiones del vehiculo
                String sqlRev = "SELECT idrevision, fecha_revision, kilometros_revision, mantenimientoid FROM public.revisiones WHERE vehiculoid = ? ";
                ps = con.prepareStatement(sqlRev);
                ps.setInt(1, idvehiculo);
                ResultSet rsRev = ps.executeQuery();

                while (rsRev.next()) {
                    //obtenemos los kilometros de mantenimientos de cada revision
                    idrevision = rsRev.getInt(1);
                    Date fecha_revision = rsRev.getDate(2);
                    float Kilometros_revision = rsRev.getFloat(3);
                    mantenimientoid = rsRev.getInt(4);
                    

                    String sqlMtto = "SELECT nombre, kilometros_mantenimiento FROM public.mantenimiento WHERE idmantenimiento = ?";
                    ps = con.prepareStatement(sqlMtto);
                    ps.setInt(1, mantenimientoid);
                    ResultSet rsMtto = ps.executeQuery();

                    if (rsMtto.next()) {
                        //obtenemos los kilometros de cada mantenimiento y el nombre de cada mantenimiento
                        String nombre_mtto = rsMtto.getString(1);
                        Float kilometros_mantenimiento = rsMtto.getFloat(2);
                        

                        if (kilometros_mantenimiento != 0) {

                            float diferenciaKm = Kilometros_actuales - Kilometros_revision;
                            controlAviso = diferenciaKm > kilometros_mantenimiento;//si es mayor, controlAviso es true, 
                            if (controlAviso) {
                                avisoNOK = MANTENIMIENTO_NOK + nombre_mtto + DELVEHICULO + idvehiculo;
                                conexio = true;
                                estadoRevision = false;
                                ActuralizarRevisiones(idrevision);

                            } else {
                                conexio = true;
                                estadoRevision = true;
                                avisoOK = MANTENIMIENTO_OK + DELVEHICULO + idvehiculo;
                                ActuralizarRevisiones(idrevision);

                            }

                        } 
                        if (!controlAviso || nombre_mtto.equalsIgnoreCase("ITV")) {//aqui tendremos que los KM estan realizados al no ser que sea revisión ITV
                            // Obtenemos la fecha actual
                            Calendar fecha_actual = Calendar.getInstance();
                            // Obtenemos la fecha de la última revisión en calendar
                            Calendar ultimaRevision = Calendar.getInstance();
                            ultimaRevision.setTime(fecha_revision);

                            // Obtenemos la diferencia en años entre las dos fechas
                            int diferciaFechas = fecha_actual.get(Calendar.YEAR) - ultimaRevision.get(Calendar.YEAR);
                            //con el siguiebte if corregimos la diferencia en años, para que sea real la resta, si los meses transcurridos no son real
                            //si la fecha ultima revision fuera dic 2022 y la actual ene 2023 la diferencia seria un 1 pero no real, por eso se resta diferciaFechas-1
                            if (fecha_actual.get(Calendar.MONTH) < ultimaRevision.get(Calendar.MONTH)
                                    || (fecha_actual.get(Calendar.MONTH) == ultimaRevision.get(Calendar.MONTH))
                                    && fecha_actual.get(Calendar.DAY_OF_MONTH) < ultimaRevision.get(Calendar.DAY_OF_MONTH) || diferciaFechas > 1 ) {
                                diferciaFechas--;
                            }
                            // Si la revisiones es de ITV, debemos comprobamos fechas par que no avise los primeros 5 años
                            if (nombre_mtto.equalsIgnoreCase("ITV")) {
                                //obtenemos la fecha de alta para primera ITV
                                Calendar fechaAlta = Calendar.getInstance();
                                fechaAlta.setTime(fecha_alta);
                                // Obtenemos la diferencia en años entre las dos fechas
                                int diferciaPrimeraItv = fecha_actual.get(Calendar.YEAR) - fechaAlta.get(Calendar.YEAR);
                                //Verifica si ha pasado los 5 primeros años de matriculacion y ya le toca ITV por cada año
                                if (diferciaPrimeraItv >= 5 && diferciaFechas >= 1) {

                                    avisoNOK = MANTENIMIENTO_NOK + nombre_mtto + DELVEHICULO + idvehiculo;
                                    estadoRevision = false;
                                    conexio = true;
                                    ActuralizarRevisiones(idrevision);

                                }
                                // Verificar si ha pasado más de 1 año desde la última revisión para los mantenimientos anuales
                            } else if (diferciaFechas > 1) {
                                //hay una revisión pendiente por fecha
                                avisoNOK = MANTENIMIENTO_NOK + nombre_mtto + DELVEHICULO + idvehiculo;
                                estadoRevision = false;
                                conexio = true;
                                ActuralizarRevisiones(idrevision);

                                //}
                            } else {
                                // Llegando aqui es que no hay revisiones de mantenimiento pendientes
                                conexio = true;
                                estadoRevision = true;
                                avisoOK = MANTENIMIENTO_OK + DELVEHICULO + idvehiculo;
                                ActuralizarRevisiones(idrevision);

                            }

                        }

                    }

                }

            }
        }

        if (avisoNOK
                != null) {

            if (!avisoNOK.isEmpty()) {//hay una revision pendiente
                conexio = true;
                estadoRevision = false;
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", conexio);
                obtResposta.addProperty("aviso", avisoNOK);
                obtResposta.addProperty("estado_revision", estadoRevision);
                resultado = gson.toJson(obtResposta);
            }

        } else { //NO hay una revision pendiente
            conexio = true;
            estadoRevision = true;
            avisoOK = MANTENIMIENTO_OK;
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("correcte", conexio);
            obtResposta.addProperty("aviso", avisoOK);
            obtResposta.addProperty("estado_revision", estadoRevision);
            resultado = gson.toJson(obtResposta);
        }

        rs.close(); // Cerrar el ResultSet

        ps.close(); // Cerrar el PreparedStatement

        cn.cerrarConexion();
        return resultado;
    }

    /**
     * Actualizamos el estado de la clase revisiones de la revison que
     * corresponda
     *
     * @param idrevision
     * @throws SQLException
     */
    public void ActuralizarRevisiones(int idrevision) throws SQLException {

        String sqlActRev = """
                    UPDATE public.revisiones
                     SET  estado_revision=?
                     WHERE  idrevision =? """;

        ps = con.prepareStatement(sqlActRev);
        ps.setBoolean(1, estadoRevision);
        ps.setInt(2, idrevision);

        int rsRevi = ps.executeUpdate();

        if (rsRevi != 1) {
            rs.close(); // Cerrar el ResultSet
            ps.close(); // Cerrar el PreparedStatement
            cn.cerrarConexion();
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("aviso", avisoNOK);
            obtResposta.addProperty("correcte", conexio);
            resultado = gson.toJson(obtResposta);
        }

    }

}
