/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                      VALUES (nextval('sec_conductor'), ?, ?, ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idvehiculo);
        ps.setDate(1, new java.sql.Date(fecha_1.getTime()));
        ps.setDate(1, new java.sql.Date(fecha_2.getTime()));
        ps.setFloat(2, empleadoid);


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
     *
     * Actualizamos los atributos de Conductor
     *
     * @param idconductor
     *
     * @return si es correcto devolvemos un tru correcte
     * @throws java.sql.SQLException
     */
    public String ActualizarConductor(String idconductor) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     UPDATE public.conductor
                      SET idconductor=idconductor, fecha_carnet=fecha_carnet, fecha_caducidad_carnet=fecha_caducidad_carnet, empleadoid=empleadoid
                      WHERE idconductor = ? """;

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setInt(1, Integer.parseInt(idconductor));

        int result = ps.executeUpdate();
        conexio = result != 0; //si la conexion obtiene un resultado conexio = true sino false
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        resposta = gson.toJson(obtResposta);

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        System.out.println(result);
        return resposta;

    }

    /**
     * Eliminamos el conductor
     * @param idconductor
     * @return
     * @throws SQLException
     */
    public String EliminarConductor(String idconductor) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     DELETE FROM public.conductor
                      WHERE idconductor = ?""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setInt(1, Integer.parseInt(idconductor));

        int result = ps.executeUpdate();
        conexio = result != 0; //si la conexion obtiene un resultado conexio = true sino false
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        resposta = gson.toJson(obtResposta);

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        System.out.println(result);
        return resposta;

    }

    /**
     * obtenemos la clase a la que se quiere listar
     *
     * @param clase
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarConductor(String clase) throws SQLException {
        Gson gson = new Gson();
        Statement st;
        ResultSet rs;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        st = (Statement) con.createStatement();
        rs = st.executeQuery("SELECT * From " + clase + "\n ORDER BY idconductor ASC ");

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idconductor", rs.getInt("idconductor"));
            jsonObject.addProperty("fecha_carnet", rs.getDate("fecha_carnet").toString());
            jsonObject.addProperty("fecha_caducidad_carnet", rs.getDate("fecha_caducidad_carnet").toString());
            jsonObject.addProperty("empleadoid", rs.getInt("empleadoid"));

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
     *
     * @param idconductor
     * @param variable
     * @param atributo
     * @return
     * @throws SQLException
     */
    public String ModificarConductor(String idconductor, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE public.conductor "
                + " SET " + atributo + " = ? "
                + " WHERE idconductor = ?";

        ps = con.prepareStatement(sql);
       
            Date fecha = format.parse(variable);
            ps.setDate(1, new java.sql.Date(fecha.getTime()));
            ps.setInt(2, Integer.parseInt(idconductor));

       
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

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta = gson.toJson(conexio);
    }

    /**
     * Listar un solo Repostajesegun su id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public String ListarConductorId(String id) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                 SELECT * FROM public.conductor
                 WHERE idconductor = ?""";

        ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        // Ejecutar la consulta SELECT y obtener el ResultSet
        rs = ps.executeQuery();

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idconductor", rs.getInt("idconductor"));
            jsonObject.addProperty("fecha_carnet", rs.getDate("fecha_carnet").toString());
            jsonObject.addProperty("fecha_caducidad_carnet", rs.getDate("fecha_caducidad_carnet").toString());
            jsonObject.addProperty("empleadoid", rs.getInt("empleadoid"));
 

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

        rs.close(); // Cerrar el ResultSet
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta;
    }
    /**
     * Funcion para añadirsolo las fechas 
     * @param fecha_carnet
     * @param fecha_caducidad
     * @return
     * @throws SQLException
     * @throws ParseException 
     */
    public String InsertConductorIdempleado(String fecha_carnet, String fecha_caducidad) throws SQLException, ParseException {
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
                     SELECT empleadoid FROM conductor""";

        ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(fecha_1.getTime()));
        ps.setDate(1, new java.sql.Date(fecha_2.getTime()));
        // Ejecutar la consulta SELECT y obtener el ResultSet
        rs = ps.executeQuery();

        if (rs.next()) {
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

}
