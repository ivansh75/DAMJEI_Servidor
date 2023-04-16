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
public class SqlRevisiones {

    public static final int INSERTAR = 2;
    public static final int ACTUALITZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LLISTAR = 5;
    public static final int MODIFICAR = 6;

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

        //int idrepostar = Integer.parseInt("nextval ('sec_idrevision')");
        Date fecha = format.parse(datos.get(1));
        float kilometros = Float.parseFloat(datos.get(2));
        int vehiculo = Integer.parseInt(datos.get(3));
        int mttoid = Integer.parseInt(datos.get(4));
        boolean estado = Boolean.parseBoolean(datos.get(5));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql ="""
                    INSERT INTO public.revisiones(
                     idrevision, Fecha_revision, kilometros_revision, vehiculoid, mantenimientoid, estado_revision)
                     VALUES (nextval('sec_idrepostar'), ?, ?, ?, ?, ?)""";
                

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
     *
     * Actualizamos los atributos de Revision
     *
     * @param idrevision
     *
     * @return si es correcto devolvemos un tru correcte
     * @throws java.sql.SQLException
     */
    public String ActualizarRevisiones(String idrevision) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql ="""
                    UPDATE public.revisiones
                     SET idrevision=idrevision, Fecha_revision=Fecha_revision, kilometros_revision=kilometros_revision, vehiculoid=vehiculoid, mantenimientoid=mantenimientoid, estado_revision=estado_revision
                     WHERE idrevision =? """;
                
                                           

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
     * Eliminamos la revision de la idrevision que nos dan
     *
     * @param idrevision
     * @return
     * @throws SQLException
     */
    public String EliminarRepostar(String idrevision) throws SQLException {
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
     * obtenemos la clase a la que se quiere listar
     *
     * @param clase
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarRepostar(String clase) throws SQLException {
        Gson gson = new Gson();
        Statement st;
        ResultSet rs;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        st = (Statement) con.createStatement();
        rs = st.executeQuery("SELECT * From " + clase + "\n ORDER BY idrevision ASC ");

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idrevision", rs.getInt("idrevision"));
            jsonObject.addProperty("fecha_revision", rs.getDate("fecha_revision").toString());
            jsonObject.addProperty("kilometros_revision", rs.getFloat("kilometros_revision"));
            jsonObject.addProperty("vehiculoid", rs.getInt("vehiculoid"));
            jsonObject.addProperty("mantenimientoid", rs.getInt("mantenimientoid"));
            jsonObject.addProperty("litros", rs.getBoolean("estado_revision"));

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
    public String ModificarRepostar(String idrevision, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE public.revisiones "
                + " SET " + atributo + " = ? "
                + " WHERE idrevision = ?";

        ps = con.prepareStatement(sql);
        if (atributo.equalsIgnoreCase("fecha_revision")) {
            Date fecha = format.parse(variable);
            ps.setDate(1, new java.sql.Date(fecha.getTime()));
            ps.setInt(2, Integer.parseInt(idrevision));

        } else if (atributo.equalsIgnoreCase("kilometros_revision")) {                       
            ps.setFloat(1, Float.parseFloat(variable));
            ps.setInt(2, Integer.parseInt(idrevision));           
            
        } else if (atributo.equalsIgnoreCase("vehiculoid")) {                      
            ps.setInt(1, Integer.parseInt(variable));
            ps.setInt(2, Integer.parseInt(idrevision));

        } else if (atributo.equalsIgnoreCase("mantenimientoid")) {                      
            ps.setInt(1, Integer.parseInt(variable));
            ps.setInt(2, Integer.parseInt(idrevision));

        } else if (atributo.equalsIgnoreCase("litros")) {           
            ps.setBoolean(1, Boolean.parseBoolean(variable));
            ps.setInt(2, Integer.parseInt(idrevision));
        }
        
        int result = ps.executeUpdate();

        if (result != 0) {
            conexio = true;
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("correcte", conexio);
            resposta = gson.toJson(obtResposta);
            cn.cerrarConexion();
            return resposta;
        }

        return resposta = gson.toJson(conexio);
    }

}
