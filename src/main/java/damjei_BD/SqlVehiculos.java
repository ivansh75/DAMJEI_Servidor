/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static damjei_GestionClases.GestionEmpleados.EMPRESAID;
import static damjei_GestionClases.GestionVehiculo.CONDUCTORID;
import static damjei_GestionClases.GestionVehiculo.FECHA_ALTA;
import static damjei_GestionClases.GestionVehiculo.FECHA_BAJA;
import static damjei_GestionClases.GestionVehiculo.IDVEHICULO;
import static damjei_GestionClases.GestionVehiculo.KILOMETROS_ACTUALES;
import static damjei_GestionClases.GestionVehiculo.KILOMETROS_ALTA;
import static damjei_GestionClases.GestionVehiculo.MARCA;
import static damjei_GestionClases.GestionVehiculo.MATRICULA;
import static damjei_GestionClases.GestionVehiculo.MODELO;
import static damjei_GestionClases.GestionVehiculo.VEHICULOS;
import damjei_SqlLogicas.SqlVehiculosRevisiones;
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

        //el idvehiculo no lo añadimos porque se genera automaticamente
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
        if (result != 0) {
            //añadimos el vehiculo a revisiones
            SqlVehiculosRevisiones sqlRevisiones = new SqlVehiculosRevisiones();
            conexio = sqlRevisiones.SqlVehiculosRevisionesion(fecha_alta, kilometros_actuales, matricula);

            if (conexio) {
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", conexio);
                resposta = gson.toJson(obtResposta);
            }

        } else {
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("correcte", conexio);
            resposta = gson.toJson(obtResposta);
        }

        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
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
     * obtenemos la clase a la que se quiere listar o un vehiculo por el idvehiculo
     *
     * @param variable
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarVehiculo(String variable) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (variable.equalsIgnoreCase(VEHICULOS)) {

            String sqlc = "SELECT * FROM " + variable + "\n ORDER BY idvehiculo ASC ";
            ps = con.prepareStatement(sqlc);

        } else {

            String sqli = """
                 SELECT * FROM public.vehiculos
                 WHERE idvehiculo = ?""";

            ps = con.prepareStatement(sqli);
            ps.setInt(1, Integer.parseInt(variable));
        }

        rs = ps.executeQuery();

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(IDVEHICULO, rs.getInt(IDVEHICULO));
            jsonObject.addProperty(MARCA, rs.getString(MARCA));
            jsonObject.addProperty(MODELO, rs.getString(MODELO));
            jsonObject.addProperty(MATRICULA, rs.getString(MATRICULA));
            jsonObject.addProperty(KILOMETROS_ALTA, rs.getFloat(KILOMETROS_ALTA));
            jsonObject.addProperty(FECHA_ALTA, rs.getDate(FECHA_ALTA).toString());
            // Verificar si el valor es nulo antes de agregarlo al objeto Json
            if (rs.getObject(FECHA_BAJA) != null) {
                jsonObject.addProperty(FECHA_BAJA, rs.getDate(FECHA_BAJA).toString());
            }
            if (rs.getObject(CONDUCTORID) != null) {
                jsonObject.addProperty(CONDUCTORID, rs.getInt(CONDUCTORID));
            }

            jsonObject.addProperty(EMPRESAID, rs.getInt(EMPRESAID));
            jsonObject.addProperty(KILOMETROS_ACTUALES, rs.getFloat(KILOMETROS_ACTUALES));

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
        if (atributo.equalsIgnoreCase(FECHA_BAJA)) {
            Date fecha_baja = format.parse(variable);
            ps.setDate(1, new java.sql.Date(fecha_baja.getTime()));
            ps.setString(2, matricula);

        } else if (atributo.equalsIgnoreCase(CONDUCTORID)) {
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

}
