/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static damjei_GestionClases.GestionConductor.CONDUCTOR;
import static damjei_GestionClases.GestionConductor.EMPLEADOID;
import static damjei_GestionClases.GestionConductor.IDCONDUCTOR;
import static damjei_GestionClases.GestionEmpleados.FECHA_CADUCIDAD;
import static damjei_GestionClases.GestionEmpleados.FECHA_CARNET;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ivimar
 */
public class SqlConductor {

    String resposta;
    boolean conexio = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * Constructor Simple
     */
    public SqlConductor() {
    }

    /**
     *
     * @param datos
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public String InsertarConductor(ArrayList<String> datos) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idrepostar = Integer.parseInt("nextval ('sec_idrepostar')");
        Date fecha_1 = format.parse(datos.get(1));
        Date fecha_2 = format.parse(datos.get(2));
        int empleadoid = Integer.parseInt(datos.get(3));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     INSERT INTO public.conductor(
                      idconductor, fecha_carnet, fecha_caducidad_carnet, empleadoid)
                      VALUES (nextval('sec_idconductor'), ?, ?, ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idvehiculo);
        ps.setDate(1, new java.sql.Date(fecha_1.getTime()));
        ps.setDate(2, new java.sql.Date(fecha_2.getTime()));
        ps.setInt(3, empleadoid);

        int result = ps.executeUpdate();
        conexio = result != 0; //si la conexion obtiene un resultado conexio = true sino false
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        resposta = gson.toJson(obtResposta);

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta;

    }

    /**
     * Eliminamos el conductor por dni empleado o por idconductor
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public String EliminarConductor(String id) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean esDni = ComprobarID(id);

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (esDni) {

            String sqldni = """
                     SELECT conductorid FROM public.empleados
                      WHERE dni = ?""";

            ps = con.prepareStatement(sqldni);
            //ps.setInt(1,idempleado);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = String.valueOf(rs.getString(1));

            }

        }

        String sql = """
                     DELETE FROM public.conductor
                      WHERE idconductor = ?""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setInt(1, Integer.parseInt(id));

        int result = ps.executeUpdate();
        conexio = result != 0; //si la conexion obtiene un resultado conexio = true sino false
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        resposta = gson.toJson(obtResposta);

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta;

    }

    /**
     * obtenemos la clase a la que se quiere listar o el de un conductor por su idconductor
     *
     * @param variable
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarConductor(String variable) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (variable.equalsIgnoreCase(CONDUCTOR)) {

            String sqlc = "SELECT * FROM " + variable + "\n ORDER BY idconductor ASC ";
            ps = con.prepareStatement(sqlc);

        } else {

            String sqli = """
                 SELECT * FROM public.conductor
                 WHERE idconductor = ?""";

            ps = con.prepareStatement(sqli);
            ps.setInt(1, Integer.parseInt(variable));
        }

        rs = ps.executeQuery();

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(IDCONDUCTOR, rs.getInt(IDCONDUCTOR));
            jsonObject.addProperty(FECHA_CARNET, rs.getDate(FECHA_CARNET).toString());
            jsonObject.addProperty(FECHA_CADUCIDAD, rs.getDate(FECHA_CADUCIDAD).toString());
            jsonObject.addProperty(EMPLEADOID, rs.getInt(EMPLEADOID));

            jsonArray.add(jsonObject);
        }

        if (jsonArray.size() == 0) {
            conexio = false;
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("correcte", conexio);
            resposta = gson.toJson(obtResposta);
        } else {
            resposta = gson.toJson(jsonArray);
        }

        cn.cerrarConexion();
        return resposta;
    }

    /**
     *
     * Modificamos en el atributo que nos pasan con el valor variable
     * puede modificarse por el idconductor o por el dni del empleado
     * @param id
     * @param variable
     * @param atributo
     * @return
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public String ModificarConductor(String id, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int empleadoid;

        boolean esDni = ComprobarID(id);

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (esDni) {

            String sqlEmp = "SELECT idempleado FROM public.empleados "
                    + " WHERE dni = ?";

            ps = con.prepareStatement(sqlEmp);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                empleadoid = rs.getInt(1);

                String sqlIdempleado = "UPDATE public.conductor "
                        + " SET " + atributo + " = ? "
                        + " WHERE empleadoid = ?";

                ps = con.prepareStatement(sqlIdempleado);
                Date fecha = format.parse(variable);
                ps.setDate(1, new java.sql.Date(fecha.getTime()));
                ps.setInt(2, empleadoid);

                int resultEm = ps.executeUpdate();

                if (resultEm != 0) {
                    conexio = true;
                    JsonObject obtResposta = new JsonObject();
                    obtResposta.addProperty("correcte", conexio);
                    resposta = gson.toJson(obtResposta);

                    ps.close(); // Cerrar el PreparedStatement
                    cn.cerrarConexion();
                    return resposta;
                }

            }

        } else {

            String sql = "UPDATE public.conductor "
                    + " SET " + atributo + " = ? "
                    + " WHERE idconductor = ?";

            ps = con.prepareStatement(sql);

            if (atributo.equalsIgnoreCase(FECHA_CADUCIDAD)) {
                Date fecha = format.parse(variable);
                ps.setDate(1, new java.sql.Date(fecha.getTime()));
                ps.setInt(2, Integer.parseInt(id));

            }

            int result = ps.executeUpdate();

            if (result != 0) {
                conexio = true;
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", conexio);
                resposta = gson.toJson(obtResposta);

                ps.close(); // Cerrar el PreparedStatement
                cn.cerrarConexion();
                return resposta;
            }

        }

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta = gson.toJson(conexio);

    }

    /**
     * Funcion para añadirsolo las fechas
     *
     * @param fecha_carnet
     * @param fecha_caducidad
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public String InsertConductorFechas(String fecha_carnet, String fecha_caducidad) throws SQLException, ParseException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Date fecha_1 = format.parse(fecha_carnet);
        Date fecha_2 = format.parse(fecha_caducidad);

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        //insertamos el idempleado de empleados  en empleadoid del conductor
        //donde la categoria del empleado es igual a conductor
        String sql = """
                      INSERT INTO conductor (idconductor, empleadoid, fecha_carnet, fecha_caducidad_carnet) 
                     SELECT nextval('sec_idconductor'), idempleado, ?, ?
                     FROM empleados 
                     WHERE categoria = 'conductor' AND idempleado NOT IN (
                     SELECT empleadoid FROM conductor)""";

        ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(fecha_1.getTime()));
        ps.setDate(2, new java.sql.Date(fecha_2.getTime()));
        // Ejecutar la consulta SELECT y obtener el ResultSet
        int res = ps.executeUpdate();

        //if (rs.next()) {
        if (res != 0) {
            conexio = true;
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("correcte", conexio);
            resposta = gson.toJson(obtResposta);

            ps.close(); // Cerrar el PreparedStatement
            cn.cerrarConexion();
            return resposta;
        }

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta = gson.toJson(conexio);

    }

    public boolean ComprobarID(String id) {
        //Comprobamos si tiene letra con lo que no esnvia el dni
        for (int i = 0; i < id.length(); i++) {
            if (Character.isLetter(id.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
