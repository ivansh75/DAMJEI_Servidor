/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static damjei_GestionClases.GestionEmpleados.NOMBRE;
import static damjei_GestionClases.GestionMantenimiento.FECHA_MANTENIMIENTO;
import static damjei_GestionClases.GestionMantenimiento.IDMANTENIMIENTO;
import static damjei_GestionClases.GestionMantenimiento.KILOMETROS_MANTENIMIENTO;
import static damjei_GestionClases.GestionMantenimiento.MANTENIMIENTO;
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
     * @param variable
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarMantenimiento(String variable) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (variable.equalsIgnoreCase(MANTENIMIENTO)) {

            String sqlc = "SELECT * FROM " + variable +"\n ORDER BY idmantenimiento ASC ";
            ps = con.prepareStatement(sqlc);
           
        } else {

            String sqli = """
                 SELECT * FROM public.mantenimiento
                 WHERE idmantenimiento = ?""";

            ps = con.prepareStatement(sqli);
            ps.setInt(1, Integer.parseInt(variable));
        }
        
        rs = ps.executeQuery();
        
        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(IDMANTENIMIENTO, rs.getInt(IDMANTENIMIENTO));
            jsonObject.addProperty(NOMBRE, rs.getString(NOMBRE));
            // Verificar si el valor es nulo antes de agregarlo al objeto Json
            if (rs.getObject(KILOMETROS_MANTENIMIENTO) != null) {
                jsonObject.addProperty(KILOMETROS_MANTENIMIENTO, rs.getFloat(KILOMETROS_MANTENIMIENTO));
            }
            if (rs.getObject(FECHA_MANTENIMIENTO) != null) {
                jsonObject.addProperty(FECHA_MANTENIMIENTO, rs.getDate(FECHA_MANTENIMIENTO).toString());
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
    

}
