/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static damjei_GestionClases.GestionRepostar.VEHICULOID;
import static damjei_GestionClases.GestionRevisiones.ESTADO_REVISION;
import static damjei_GestionClases.GestionRevisiones.FECHA_REVISION;
import static damjei_GestionClases.GestionRevisiones.IDREVISION;
import static damjei_GestionClases.GestionRevisiones.KILOMETROS_REVISION;
import static damjei_GestionClases.GestionRevisiones.MANTENIMIENTOID;
import static damjei_GestionClases.GestionRevisiones.REVISIONES;
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
public class SqlRevisiones {
    

    String resposta;
    boolean conexio = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * Constructor Simple
     */
    public SqlRevisiones() {
    }

    /**
     *
     * @param datos
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public String InsertarRevisiones(ArrayList<String> datos) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idrevision = Integer.parseInt("nextval ('sec_idrevision')");
        Date fecha = format.parse(datos.get(1));
        float kilometros = Float.parseFloat(datos.get(2));
        int vehiculo = Integer.parseInt(datos.get(3));
        int mttoid = Integer.parseInt(datos.get(4));
        boolean estado = Boolean.parseBoolean(datos.get(5));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                    INSERT INTO public.revisiones(
                     idrevision, "fecha_revision", kilometros_revision, vehiculoid, mantenimientoid, estado_revision)
                     VALUES (nextval('sec_idrevisiones'), ?, ?, ?, ?, ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idvehiculo);
        ps.setDate(1, new java.sql.Date(fecha.getTime()));
        ps.setFloat(2, kilometros);
        ps.setInt(3, vehiculo);
        ps.setInt(4, mttoid);
        ps.setBoolean(5, estado);

        int result = ps.executeUpdate();
        conexio = result != 0; //si la conexion obtiene un resultado conexio = true sino false
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        resposta = gson.toJson(obtResposta);
        cn.cerrarConexion();
        return resposta;

    }

    /**
     * Eliminamos la revision de la idrevision que nos dan
     *
     * @param idrevision
     * @return
     * @throws SQLException
     */
    public String EliminarRevisiones(String idrevision) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     DELETE FROM public.revisiones
                      WHERE idrevision = ?""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setInt(1, Integer.parseInt(idrevision));

        int result = ps.executeUpdate();
        conexio = result != 0; //si la conexion obtiene un resultado conexio = true sino false
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        resposta = gson.toJson(obtResposta);
        cn.cerrarConexion();
        System.out.println(result);
        return resposta;

    }

    /**
     * obtenemos la clase a la que se quiere listar o una revision por su idrevisiones
     *
     * @param variable
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarRevisiones(String variable) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (variable.equalsIgnoreCase(REVISIONES)) {

            String sqlc = "SELECT * FROM " + variable +"\n ORDER BY idrevision ASC ";
            ps = con.prepareStatement(sqlc);
           
        } else {

            String sqli = """
                 SELECT * FROM public.revisiones
                 WHERE idrevision = ?""";

            ps = con.prepareStatement(sqli);
            ps.setInt(1, Integer.parseInt(variable));
        }
        
        rs = ps.executeQuery();
        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(IDREVISION, rs.getInt(IDREVISION));
            jsonObject.addProperty(FECHA_REVISION, rs.getDate(FECHA_REVISION).toString());
            jsonObject.addProperty(KILOMETROS_REVISION, rs.getFloat(KILOMETROS_REVISION));
            jsonObject.addProperty(VEHICULOID, rs.getInt(VEHICULOID));
            jsonObject.addProperty(MANTENIMIENTOID, rs.getInt(MANTENIMIENTOID));
            jsonObject.addProperty(ESTADO_REVISION, rs.getBoolean(ESTADO_REVISION));

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
     * @param idrevision
     * @param variable
     * @param atributo
     * @return
     * @throws SQLException
     */
    
    public String ModificarRevisiones(String idrevision, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE public.revisiones "
                + " SET " + atributo + " = ? "
                + " WHERE idrevision = ?";

        ps = con.prepareStatement(sql);
        if (atributo.equalsIgnoreCase(FECHA_REVISION)) {
            Date fecha = format.parse(variable);
            ps.setDate(1, new java.sql.Date(fecha.getTime()));
            ps.setInt(2, Integer.parseInt(idrevision));

        } else if (atributo.equalsIgnoreCase(KILOMETROS_REVISION)) {
            ps.setFloat(1, Float.parseFloat(variable));
            ps.setInt(2, Integer.parseInt(idrevision));

        } else if (atributo.equalsIgnoreCase(VEHICULOID)) {
            ps.setInt(1, Integer.parseInt(variable));
            ps.setInt(2, Integer.parseInt(idrevision));

        } else if (atributo.equalsIgnoreCase(MANTENIMIENTOID)) {
            ps.setInt(1, Integer.parseInt(variable));
            ps.setInt(2, Integer.parseInt(idrevision));

        } else if (atributo.equalsIgnoreCase(ESTADO_REVISION)) {
            ps.setBoolean(1, Boolean.parseBoolean(variable));
            ps.setInt(2, Integer.parseInt(idrevision));
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
     * Listamos las revisiones por el vehiculoid que tiene el esta_revision false
     * Asi se podra ver el si un vehiculo en concreto tiene revisiones pendientes
     * @param variable
     * @return
     * @throws SQLException 
     */
     public String ListarRevisionesVehiculo(String variable) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();


            String sqlVr = """
                          SELECT * FROM public.revisiones
                                           WHERE vehiculoid = ? AND estado_revision = false""";

            ps = con.prepareStatement(sqlVr);
            ps.setInt(1, Integer.parseInt(variable));
        
        rs = ps.executeQuery();
        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(IDREVISION, rs.getInt(IDREVISION));
            jsonObject.addProperty(FECHA_REVISION, rs.getDate(FECHA_REVISION).toString());
            jsonObject.addProperty(KILOMETROS_REVISION, rs.getFloat(KILOMETROS_REVISION));
            jsonObject.addProperty(VEHICULOID, rs.getInt(VEHICULOID));
            jsonObject.addProperty(MANTENIMIENTOID, rs.getInt(MANTENIMIENTOID));
            jsonObject.addProperty(ESTADO_REVISION, rs.getBoolean(ESTADO_REVISION));

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
    
}
