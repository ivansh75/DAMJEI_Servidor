/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.TokenManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivimar
 */
public class SqlEmpleado {

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;

    //public static final String EMPLEADOS = "empleados";
    public static final String ERROR_AUTORIZACION = "Este usuario debe registrarse-Login";

    boolean UsuariAdmin = false;
    boolean conexio = false;

    String resposta;
    int accio;
    String token;

    public SqlEmpleado() {
    }

    /**
     *
     * Comprobamos para logear al cliente y definir tipo usuario
     *
     * @param dni
     * @param contraseña
     * @return Si es autorizado añadimos el token con su dni sino, usuario false
     * y no añadimos token
     */
    public String LoginUsuari(String dni, String contraseña) {
        Gson gson = new Gson();
        boolean administrador;
        Statement st;
        ResultSet rs;

        try {

            ConexionBD cn = new ConexionBD();
            Connection con = cn.establecerConexion();

            st = (Statement) con.createStatement();
            rs = st.executeQuery("""
                                 SELECT nombre,dni,contrase\u00f1a,administrador
                                 FROM empleados
                                 WHERE UPPER (dni) = UPPER ('""" + dni + "') AND UPPER (contraseña) = UPPER ('" + contraseña + "')");

            if (rs.next()) {
                administrador = rs.getBoolean("administrador");
                //comprobamos si por categoria es administrador o usuario conductor
                if (administrador) {
                    conexio = true;
                    UsuariAdmin = true;
                    //generamos y validamos el token de usuario
                    token = TokenManager.generateToken(dni);
                    TokenManager.addToken(dni, token);
                    //pasem parametres Objecta gson administrador
                    JsonObject obtResposta = new JsonObject();
                    obtResposta.addProperty("correcte", conexio);
                    obtResposta.addProperty("administrador", UsuariAdmin);
                    obtResposta.addProperty("token", token);
                    resposta = gson.toJson(obtResposta);
                    cn.cerrarConexion();
                    return resposta;
                } else {
                    conexio = true;
                    //generamos y validamos el token de usuario
                    token = token = TokenManager.generateToken(dni);
                    TokenManager.addToken(dni, token);
                    //pasem parametres Objecta gson Usuari
                    JsonObject obtResposta = new JsonObject();
                    obtResposta.addProperty("correcte", conexio);
                    obtResposta.addProperty("administrador", UsuariAdmin);
                    obtResposta.addProperty("token", token);
                    resposta = gson.toJson(obtResposta);
                    cn.cerrarConexion();
                    return resposta;
                }
            } else {

                //pasem parametres Objecte gson per dir que la conexxió no es correcta
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", conexio);
                obtResposta.addProperty("administrador", UsuariAdmin);
                resposta = gson.toJson(obtResposta);
                System.out.println("No se encontro ningun empleado");
                cn.cerrarConexion();
                return resposta;
            }

        } catch (SQLException ex) {
            Logger.getLogger(SqlEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resposta;
    }

    /**
     *
     * Obtenemos el nombre y dni que hace Logout
     *
     * @param dni
     * @param contraseña
     * @return false para desconectar y eliminar el token asignado
     */
    public String LogoutUsuari(String dni, String contraseña) {
        Gson gson = new Gson();

        conexio = false;
        //Eliminamos el token de usuario
        TokenManager.removeToken(dni);
        //Borrem el token del Usuari
        conexio = true;
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        obtResposta.addProperty("nombre", "");
        return resposta = gson.toJson(obtResposta);

    }

    /**
     * Insertamos Usuario nuevo
     *
     * @param datos
     * @return
     * @throws SQLException
     */
    public String InsertarUsuari(ArrayList<String> datos) throws SQLException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idempleado = Integer.parseInt("nextval ('sec_idempleado')");
        String nom = datos.get(1);
        String apellidos = datos.get(2);
        String dni = datos.get(3);
        String categoria = datos.get(4);
        int id_empresa = Integer.parseInt(datos.get(5));
        String contrasenya = datos.get(6);
        boolean administrador = Boolean.parseBoolean(datos.get(7));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                         INSERT INTO public.empleados(
                         idempleado, nombre, apellidos, dni, categoria, empresaid, "contrase\u00f1a", administrador)
                         VALUES (nextval('sec_idempleado'), ?, ?, ?, ?, ?, ?, ?)""";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, nom);
        ps.setString(2, apellidos);
        ps.setString(3, dni);
        ps.setString(4, categoria);
        ps.setInt(5, id_empresa);
        ps.setString(6, contrasenya);
        ps.setBoolean(7, administrador);

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
     * Actualizamos los atributos del usuario
     *
     * @param dni
     *
     * @return si es correcto devolvemos un tru correcte
     * @throws java.sql.SQLException
     */
    public String ActualizarUsuario(String dni) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE empleados SET nombre = nombre, apellidos = apellidos, dni = dni,"
                + " categoria = categoria, empresaid = empresaid, "
                + "contraseña = contraseña, administrador = administrador\n"
                + "WHERE dni = ? ";

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, dni);

        int result = ps.executeUpdate();
        conexio = result != 0; //si la conexion obtiene un resultado conexio = true sino false
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        resposta = gson.toJson(obtResposta);
        cn.cerrarConexion();
        System.out.println(result);
        return resposta;

    }

    public String EliminarUsuario(String dni) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                     DELETE From public.empleados 
                     WHERE dni = ? """;

        ps = con.prepareStatement(sql);
        //ps.setInt(1,idempleado);
        ps.setString(1, dni);

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
    public String ListarEmpleados(String clase) throws SQLException {
        Gson gson = new Gson();
        Statement st;
        ResultSet rs;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        st = (Statement) con.createStatement();
        rs = st.executeQuery("SELECT * From " + clase + "\n ORDER BY idempleado ASC ");

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idempleado", rs.getInt("idempleado"));
            jsonObject.addProperty("nombre", rs.getString("nombre"));
            jsonObject.addProperty("apellidos", rs.getString("apellidos"));
            jsonObject.addProperty("dni", rs.getString("dni"));
            jsonObject.addProperty("categoria", rs.getString("categoria"));
            jsonObject.addProperty("empresaid", rs.getInt("empresaid"));
            jsonObject.addProperty("contraseña", rs.getString("contraseña"));
            jsonObject.addProperty("administrador", rs.getBoolean("administrador"));
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
     * @param dni
     * @param variable
     * @param atributo
     * @return
     * @throws SQLException
     */
    public String ModificarUsusario(String dni, String variable, String atributo) throws SQLException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE empleados "
                + " SET " + atributo + " = ? "
                + "WHERE dni = ?";

        ps = con.prepareStatement(sql);

        if (atributo.equalsIgnoreCase("administrador")) {
            ps.setBoolean(1, Boolean.parseBoolean(variable));
            ps.setString(2, dni);

        } else {
            ps.setString(1, variable);
            ps.setString(2, dni);
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

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public String ListarEmpleadosID(String id) throws SQLException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                 SELECT * FROM public.empleados
                 WHERE idempleado = ?""";

        ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));

        // Ejecutar la consulta SELECT y obtener el ResultSet
        rs = ps.executeQuery();

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("idempleado", rs.getInt("idempleado"));
            jsonObject.addProperty("nombre", rs.getString("nombre"));
            jsonObject.addProperty("apellidos", rs.getString("apellidos"));
            jsonObject.addProperty("dni", rs.getString("dni"));
            jsonObject.addProperty("categoria", rs.getString("categoria"));
            jsonObject.addProperty("empresaid", rs.getInt("empresaid"));
            jsonObject.addProperty("contraseña", rs.getString("contraseña"));
            jsonObject.addProperty("administrador", rs.getBoolean("administrador"));
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
