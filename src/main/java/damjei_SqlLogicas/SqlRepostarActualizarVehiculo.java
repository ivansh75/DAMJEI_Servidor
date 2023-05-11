/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_SqlLogicas;

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

    boolean conexio = false;
    boolean controlAviso = false;
    boolean estadoRevision = false;
    String aviso;

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
    public boolean SqlRepostarActualizarVehiculos(float kilometros, int idvehiculo) throws SQLException {
        float Kilometros_actuales = kilometros;
        int mantenimientoid = 0;
        int idrevision = 0;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

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
            return conexio;

        } else {

            String sqlVh = "SELECT fecha_alta FROM public.vehiculos WHERE idvehiculo = ?";

            ps = con.prepareStatement(sqlVh);
            ps.setInt(1, idvehiculo);
            rs = ps.executeQuery();

            if (rs.next()) {
                Date fecha_alta = rs.getDate(1);

                int resultVh = ps.executeUpdate();
                if (resultVh != 1) {
                    ps.close(); // Cerrar el PreparedStatement
                    cn.cerrarConexion();
                    return conexio;
                } else {

                    //obtenmos las fechas y kilometros de las revisiones del vehiculo
                    String sqlRev = "SELECT Fecha_revision, kilometros_revision, mantenimientoid, idrevision FROM public.revisiones WHERE vehiculoid = ? ";
                    ps = con.prepareStatement(sqlRev);
                    ps.setInt(1, idvehiculo);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        //obtenemos los kilometros de mantenimientos
                        Date fecha_revision = rs.getDate(1);
                        float Kilometros_revision = rs.getFloat(2);
                        mantenimientoid = rs.getInt(3);
                        idrevision = rs.getInt(4);

                        String sqlMtto = "SELECT kilometros_mantenimiento, nombre FROM public.mantenimiento";
                        ps = con.prepareStatement(sqlMtto);
                        ResultSet rsMtto = ps.executeQuery();

                        while (rsMtto.next()) {

                            Float kilometros_mantenimiento = rsMtto.getFloat(1);
                            String nombre_mtto = rsMtto.getString(2);

                            if (kilometros_mantenimiento != null) {

                                float diferenciaKm = Kilometros_revision - Kilometros_actuales;
                                controlAviso = diferenciaKm > kilometros_mantenimiento;//si es mayor controlAviso es true, 
                                aviso = "Existen mantenimientos pendientes por Kilometrso";

                            } else if (!controlAviso || nombre_mtto.equalsIgnoreCase("ITV")) {//aqui tendremos que los KM estan realizados al no ser que sea revisión ITV
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
                                        || (fecha_actual.get(Calendar.MONTH) == ultimaRevision.get(Calendar.MONTH)
                                        && fecha_actual.get(Calendar.DAY_OF_MONTH) < ultimaRevision.get(Calendar.DAY_OF_MONTH))) {
                                    diferciaFechas--;

                                    // Verificamos si han pasado más de 5 años desde la última revisión y si el id es de ITV que sabemos que es 1
                                    if (nombre_mtto.equalsIgnoreCase("ITV")) {
                                        //obtenemos la fecha de alta para primera ITV
                                        Calendar fechaAlta = Calendar.getInstance();
                                        fechaAlta.setTime(fecha_alta);
                                        // Obtenemos la diferencia en años entre las dos fechas
                                        int diferciaPrimeraItv = fecha_actual.get(Calendar.YEAR) - fechaAlta.get(Calendar.YEAR);

                                        if (diferciaPrimeraItv >= 5 && diferciaFechas >= 1) {

                                            aviso = "Es necesario realizar mantenimiento de ITV";
                                        }
                                        // Verificar si ha pasado más de 1 año desde la última revisión
                                    } else if (diferciaFechas > 1) {

                                        aviso = "Existen mantenimientos pendientes por fecha";
                                    }
                                } else {
                                    // Llegando aqui es que no hay revisiones de mantenimiento pendientes
                                    aviso = "No hay mantenimientos pendientes ni por KM ni por fecha";
                                    conexio = true;
                                    estadoRevision = true;

                                }

                            }

                        }

                    }
                }
                String sqlActRev = """
                    UPDATE public.revisiones
                     SET  estado_revision=?
                     WHERE  idrevision =? """;

                ps = con.prepareStatement(sqlActRev);
                ps.setBoolean(1, estadoRevision);
                ps.setInt(2, idrevision);

                int rsRev = ps.executeUpdate();

                if (rsRev != 1) {
                    rs.close(); // Cerrar el ResultSet
                    ps.close(); // Cerrar el PreparedStatement
                    cn.cerrarConexion();
                    System.out.println(aviso);
                    return conexio;
                }

            } else {
                rs.close(); // Cerrar el ResultSet
                ps.close(); // Cerrar el PreparedStatement
                cn.cerrarConexion();
                System.out.println(aviso);
                return conexio;
            }

            rs.close(); // Cerrar el ResultSet
            ps.close(); // Cerrar el PreparedStatement
            cn.cerrarConexion();
            return conexio;
        }

    }
}
