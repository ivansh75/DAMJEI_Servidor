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
public class SqlMantenimiento {
    
    String resposta;
    boolean conexio = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * Constructor Simple
     */
    public SqlMantenimiento() {
    }

    /**
     * Insertar valores recibidos en el ArrayList
     *
     * @param datos
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public String InsertarMantenimiento(ArrayList<String> datos) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idvehiculo = Integer.parseInt("nextval ('sec_idmantenimiento')");
        String nombre = datos.get(1);

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                        INSERT INTO public.mantenimiento(
                        idmantenimiento, nombre)
                        VALUES (nextval('sec_idmantenimiento'), ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idvehiculo);
        ps.setString(1, nombre);

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
     * Actualizamos los atributos del vehiculo
     *
     * @param nombre
     *
     * @return si es correcto devolvemos un tru correcte
     * @throws java.sql.SQLException
     */
    public String ActualizarMantenimiento(String nombre) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     UPDATE public.mantenimiento 
                     SET idmantenimiento=idmantenimiento, nombre=nombre, kilometros_mantenimiento=kilometros_mantenimiento, fecha_mantenimiento=fecha_mantenimiento
                     WHERE UPPER(nombre) = UPPER(?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, nombre);

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
     * Eliminamos el vehiculo de la matricula que nos dan
     *
     * @param nombre
     * @return
     * @throws SQLException
     */
    public String EliminarMantenimiento(String nombre) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     DELETE From public.mantenimiento 
                     WHERE UPPER(nombre) = UPPER(?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, nombre);

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
    public String ListarMantenimiento(String clase) throws SQLException {
        Gson gson = new Gson();
        Statement st;
        ResultSet rs;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        st = (Statement) con.createStatement();
        rs = st.executeQuery("SELECT * From " + clase + "\n ORDER BY idmantenimiento ASC ");

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idmantenimiento", rs.getInt("idmantenimiento"));
            jsonObject.addProperty("nombre", rs.getString("nombre"));
            // Verificar si el valor es nulo antes de agregarlo al objeto Json
            if (rs.getObject("kilometros_mantenimiento") != null) {
                jsonObject.addProperty("kilometros_mantenimiento", rs.getFloat("kilometros_mantenimiento"));
            }
            if (rs.getObject("fecha_mantenimiento") != null) {
                jsonObject.addProperty("fecha_mantenimiento", rs.getDate("fecha_mantenimiento").toString());
            }

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
     * @param nombre
     * @param kilometros
     * @param fecha
     * @return
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public String ModificarMantenimiento(String nombre, String kilometros, String fecha) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        Date fecha_mtto = format.parse(fecha);

        String sql = "UPDATE public.mantenimiento "
                + " SET  kilometros_mantenimiento= ?, fecha_mantenimiento = ? "
                + "WHERE UPPER(nombre) = UPPER(?)";

        ps = con.prepareStatement(sql);
        ps.setFloat(1, Float.parseFloat(kilometros));
        ps.setDate(2, new java.sql.Date(fecha_mtto.getTime()));
        ps.setString(3, nombre);

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
     * Listar un solo mantenimiento por su id
     * @param id
     * @return
     * @throws SQLException 
     */
     public String ListarMantenimientoId(String id) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                 SELECT * FROM public.mantenimiento
                 WHERE idmantenimiento = ?""";

        ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));

        // Ejecutar la consulta SELECT y obtener el ResultSet
        rs = ps.executeQuery();
        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idmantenimiento", rs.getInt("idmantenimiento"));
            jsonObject.addProperty("nombre", rs.getString("nombre"));
            // Verificar si el valor es nulo antes de agregarlo al objeto Json
            if (rs.getObject("kilometros_mantenimiento") != null) {
                jsonObject.addProperty("kilometros_mantenimiento", rs.getFloat("kilometros_mantenimiento"));
            }
            if (rs.getObject("fecha_mantenimiento") != null) {
                jsonObject.addProperty("fecha_mantenimiento", rs.getDate("fecha_mantenimiento").toString());
            }

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

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta;
    }

}
