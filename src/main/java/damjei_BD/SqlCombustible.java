/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static damjei_GestionClases.GestionCombustible.COMBUSTIBLE;
import static damjei_GestionClases.GestionCombustible.IDCOMBUSTIBLE;
import static damjei_GestionClases.GestionCombustible.PRECIO;
import static damjei_GestionClases.GestionEmpleados.NOMBRE;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ivimar
 */
public class SqlCombustible {

    String resposta;
    boolean conexio = false;

    /**
     *
     * Constructor Simple
     */
    public SqlCombustible() {
    }

    /**
     * Insertamos Combustible
     *
     * @param datos
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public String InsertarCombustible(ArrayList<String> datos) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idcombustible = Integer.parseInt("nextval ('sec_idcombustible')");
        String nombre = datos.get(1);
        float precio = Float.parseFloat(datos.get(2));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                         INSERT INTO public.combustible(
                         idcombustible, nombre, precio)
                         VALUES (nextval('sec_idcombustible'), ?, ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, nombre);
        ps.setFloat(2, precio);

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
     * Actualizamos los atributos del Combustible
     *
     * @param nombre
     *
     * @return si es correcto devolvemos un tru correcte
     * @throws java.sql.SQLException
     */
    public String ActualizarCombustible(String nombre) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     UPDATE public.combustible 
                     SET idcombustible=idcombustible, nombre=nombre, precio=precio
                     WHERE UPPER(nombre) = UPPER(?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idcombustible);
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
     * Eliminamos el Combustible de la matricula que nos dan
     *
     * @param nombre
     * @return
     * @throws SQLException
     */
    public String EliminarCombustible(String nombre) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     DELETE From public.combustible
                     WHERE UPPER(nombre) = UPPER(?) """;

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
     * @param variable
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarCombustible(String variable) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (variable.equalsIgnoreCase(COMBUSTIBLE)) {

            String sqlc = "SELECT * FROM " + variable + "\n ORDER BY idcombustible ASC ";
            ps = con.prepareStatement(sqlc);

        } else {

            String sqli = """
                 SELECT * FROM public.combustible
                 WHERE idcombustible = ?""";

            ps = con.prepareStatement(sqli);
            ps.setInt(1, Integer.parseInt(variable));
        }

        rs = ps.executeQuery();
        
        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(IDCOMBUSTIBLE, rs.getInt(IDCOMBUSTIBLE));
            jsonObject.addProperty(NOMBRE, rs.getString(NOMBRE));
            jsonObject.addProperty(PRECIO, rs.getFloat(PRECIO));

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
     * @param variable
     * @param atributo
     * @return
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public String ModificarCombustible(String nombre, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE public.combustible "
                + " SET " + atributo + " = ? "
                + "WHERE UPPER(nombre) = UPPER(?)";

        ps = con.prepareStatement(sql);

        ps.setFloat(1, Float.parseFloat(variable));
        ps.setString(2, nombre);

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

}
