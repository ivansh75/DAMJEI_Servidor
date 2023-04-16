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
public class SqlRepostar {

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
    public SqlRepostar() {
    }

    /**
     *
     * @param datos
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public String InsertarRepostar(ArrayList<String> datos) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idrepostar = Integer.parseInt("nextval ('sec_idrepostar')");
        Date fecha = format.parse(datos.get(1));
        float importe = Float.parseFloat(datos.get(2));
        float kilometros = Float.parseFloat(datos.get(3));
        int combustible = Integer.parseInt(datos.get(4));
        int vehiculo = Integer.parseInt(datos.get(5));
        int conductor = Integer.parseInt(datos.get(6));
        float litros = Float.parseFloat(datos.get(7));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     INSERT INTO public.repostar(
                      idrepostar, fecha_repostar, importe_repostar, kilometros_repostar, combustibleid, vehiculoid, conductorid, litros)
                      VALUES (nextval('sec_idrepostar'), ?, ?, ?, ?, ?, ?, ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idvehiculo);
        ps.setDate(1, new java.sql.Date(fecha.getTime()));
        ps.setFloat(2, importe);
        ps.setFloat(3, kilometros);
        ps.setInt(4, combustible);
        ps.setInt(5, vehiculo);
        ps.setInt(6, conductor);
        ps.setFloat(7, litros);

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
     * Actualizamos los atributos de Repostar
     *
     * @param idrepostar
     *
     * @return si es correcto devolvemos un tru correcte
     * @throws java.sql.SQLException
     */
    public String ActualizarRepostar(String idrepostar) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     UPDATE public.repostar
                      SET idrepostar=idrepostar, fecha_repostar=fecha_repostar, importe_repostar=importe_repostar, 
                     kilometros_repostar=kilometros_repostar, combustibleid=combustibleid, vehiculoid=vehiculoid, 
                     conductorid=conductorid, litros=litros
                      WHERE idrepostar= ?""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setInt(1, Integer.parseInt(idrepostar));

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
     * Eliminamos el vehiculo de la matricula que nos dan
     *
     * @param idrepostar
     * @return
     * @throws SQLException
     */
    public String EliminarRepostar(String idrepostar) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     DELETE FROM public.repostar
                      WHERE idrepostar = ?""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setInt(1, Integer.parseInt(idrepostar));

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
        rs = st.executeQuery("SELECT * From " + clase + "\n ORDER BY idrepostar ASC ");

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idrepostar", rs.getInt("idrepostar"));
            jsonObject.addProperty("fecha_repostar", rs.getDate("fecha_repostar").toString());
            jsonObject.addProperty("importe_repostar", rs.getFloat("importe_repostar"));
            jsonObject.addProperty("kilometros_repostar", rs.getFloat("kilometros_repostar"));
            jsonObject.addProperty("combustibleid", rs.getInt("combustibleid"));
            jsonObject.addProperty("vehiculoid", rs.getInt("vehiculoid"));
            jsonObject.addProperty("conductorid", rs.getInt("conductorid"));
            jsonObject.addProperty("litros", rs.getFloat("litros"));

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
     * @param idrepostar
     * @param variable
     * @param atributo
     * @return
     * @throws SQLException
     */
    public String ModificarRepostar(String idrepostar, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE public.repostar "
                + " SET " + atributo + " = ? "
                + " WHERE idrepostar = ?";

        ps = con.prepareStatement(sql);
        if (atributo.equalsIgnoreCase("fecha_repostar")) {
            Date fecha = format.parse(variable);
            ps.setDate(1, new java.sql.Date(fecha.getTime()));
            ps.setInt(2, Integer.parseInt(idrepostar));

        } else if (atributo.equalsIgnoreCase("importe_repostar")) {                      
            ps.setFloat(1, Float.parseFloat(variable));
            ps.setInt(2, Integer.parseInt(idrepostar));

        } else if (atributo.equalsIgnoreCase("kilometros_repostar")) {                       
            ps.setFloat(1, Float.parseFloat(variable));
            ps.setInt(2, Integer.parseInt(idrepostar));
            
        } else if (atributo.equalsIgnoreCase("combustibleid")) {                       
            ps.setInt(1, Integer.parseInt(variable));
            ps.setInt(2, Integer.parseInt(idrepostar));
            
        } else if (atributo.equalsIgnoreCase("vehiculoid")) {                      
            ps.setInt(1, Integer.parseInt(variable));
            ps.setInt(2, Integer.parseInt(idrepostar));

        } else if (atributo.equalsIgnoreCase("conductorid")) {                      
            ps.setInt(1, Integer.parseInt(variable));
            ps.setInt(2, Integer.parseInt(idrepostar));

        } else if (atributo.equalsIgnoreCase("litros")) {           
            ps.setFloat(1, Float.parseFloat(variable));
            ps.setInt(2, Integer.parseInt(idrepostar));
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
