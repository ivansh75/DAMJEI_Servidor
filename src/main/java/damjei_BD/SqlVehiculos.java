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
public class SqlVehiculos {

    String resposta;
    boolean conexio = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * Constructor Simple
     */
    public SqlVehiculos() {
    }

    /**
     *
     * @param datos
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public String InsertarVehiculo(ArrayList<String> datos) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idvehiculo = Integer.parseInt("nextval ('sec_idvehiculo')");
        String marca = datos.get(1);
        String modelo = datos.get(2);
        String matricula = datos.get(3);
        float kilometros_alta = Float.parseFloat(datos.get(4));
        Date fecha_alta = format.parse(datos.get(5));
        int empresaid = Integer.parseInt(datos.get(6));
        float kilometros_actuales = Float.parseFloat(datos.get(7));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                         INSERT INTO public.vehiculos(
                         idvehiculo, marca, modelo, matricula, kilometros_alta, fecha_alta, empresaid, kilometros_actuales)
                         VALUES (nextval('sec_idvehiculo'), ?, ?, ?, ?, ?, ?, ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idvehiculo);
        ps.setString(1, marca);
        ps.setString(2, modelo);
        ps.setString(3, matricula);
        ps.setFloat(4, kilometros_alta);
        ps.setDate(5, new java.sql.Date(fecha_alta.getTime()));
        ps.setInt(6, empresaid);
        ps.setFloat(7, kilometros_actuales);

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
     * @param matricula
     *
     * @return si es correcto devolvemos un tru correcte
     * @throws java.sql.SQLException
     */
    public String ActualizarVehiculo(String matricula) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE public.vehiculos \n"
                + "SET idvehiculo=idvehiculo, marca=marca, modelo=modelo, matricula=matricula, "
                + "kilometros_alta=kilometros_alta, fecha_alta=fecha_alta, fecha_baja=fecha_baja, "
                + "conductorid=conductorid, empresaid=empresaid, kilometros_actuales=kilometros_actuales\n"
                + "WHERE matricula = ?";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, matricula);

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
     * @param matricula
     * @return
     * @throws SQLException
     */
    public String EliminarVehiculo(String matricula) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "DELETE From public.vehiculos \n"
                + "WHERE matricula = ? ";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, matricula);

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
    public String ListarVehiculo(String clase) throws SQLException {
        Gson gson = new Gson();
        Statement st;
        ResultSet rs;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        st = (Statement) con.createStatement();
        rs = st.executeQuery("SELECT * From " + clase + "\n ORDER BY idvehiculo ASC ");

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idvehiculo", rs.getInt("idvehiculo"));
            jsonObject.addProperty("marca", rs.getString("marca"));
            jsonObject.addProperty("modelo", rs.getString("modelo"));
            jsonObject.addProperty("matricula", rs.getString("matricula"));
            jsonObject.addProperty("kilometros_alta", rs.getFloat("kilometros_alta"));
            jsonObject.addProperty("fecha_alta", rs.getDate("fecha_alta").toString());
            // Verificar si el valor es nulo antes de agregarlo al objeto Json
            if (rs.getObject("fecha_baja") != null) {
                jsonObject.addProperty("fecha_baja", rs.getDate("fecha_baja").toString());
            }
            if (rs.getObject("conductorid") != null) {
                jsonObject.addProperty("conductorid", rs.getInt("conductorid"));
            }

            jsonObject.addProperty("empresaid", rs.getInt("empresaid"));
            jsonObject.addProperty("kilometros_actuales", rs.getFloat("kilometros_actuales"));

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
     * @param matricula
     * @param variable
     * @param atributo
     * @return
     * @throws SQLException
     */
    public String ModificarVehiculo(String matricula, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE public.vehiculos "
                + " SET " + atributo + " = ? "
                + "WHERE matricula = ?";

        ps = con.prepareStatement(sql);
        if (atributo.equalsIgnoreCase("fecha_baja")) {
            Date fecha_baja = format.parse(variable);
            ps.setDate(1, new java.sql.Date(fecha_baja.getTime()));
            ps.setString(2, matricula);

        } else if (atributo.equalsIgnoreCase("conductorid")) {
            //int conductorid = Integer.parseInt(variable);            
            ps.setInt(1, Integer.parseInt(variable));
            ps.setString(2, matricula);

        } else {
            //float kilometros_actuales = Float.parseFloat(variable);
            ps.setFloat(1, Float.parseFloat(variable));
            ps.setString(2, matricula);
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

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return resposta = gson.toJson(conexio);
    }

    /**
     * Listar un vehiculo por su id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public String ListarVehiculoId(String id) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                 SELECT * FROM public.vehiculos
                 WHERE idvehiculo = ?""";

        ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));

        // Ejecutar la consulta SELECT y obtener el ResultSet
        rs = ps.executeQuery();
        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idvehiculo", rs.getInt("idvehiculo"));
            jsonObject.addProperty("marca", rs.getString("marca"));
            jsonObject.addProperty("modelo", rs.getString("modelo"));
            jsonObject.addProperty("matricula", rs.getString("matricula"));
            jsonObject.addProperty("kilometros_alta", rs.getFloat("kilometros_alta"));
            jsonObject.addProperty("fecha_alta", rs.getDate("fecha_alta").toString());
            // Verificar si el valor es nulo antes de agregarlo al objeto Json
            if (rs.getObject("fecha_baja") != null) {
                jsonObject.addProperty("fecha_baja", rs.getDate("fecha_baja").toString());
            }
            if (rs.getObject("conductorid") != null) {
                jsonObject.addProperty("conductorid", rs.getInt("conductorid"));
            }

            jsonObject.addProperty("empresaid", rs.getInt("empresaid"));
            jsonObject.addProperty("kilometros_actuales", rs.getFloat("kilometros_actuales"));

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

}
