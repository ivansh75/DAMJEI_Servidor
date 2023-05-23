/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.Encriptador;
import damjei_ConexionCliente.TokenManager;
import static damjei_GestionClases.GestionEmpleados.ADMINISTRADOR;
import static damjei_GestionClases.GestionEmpleados.APELLIDOS;
import static damjei_GestionClases.GestionEmpleados.CATEGORIA;
import static damjei_GestionClases.GestionEmpleados.IDEMPLEADO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static damjei_GestionClases.GestionEmpleados.CONTRASEÑA;
import static damjei_GestionClases.GestionEmpleados.DATOS_TOKEN;
import static damjei_GestionClases.GestionEmpleados.DNI;
import static damjei_GestionClases.GestionEmpleados.EMPLEADOS;
import static damjei_GestionClases.GestionEmpleados.EMPRESAID;
import static damjei_GestionClases.GestionEmpleados.NOMBRE;

/**
 *
 * @author Ivimar
 */
public class SqlEmpleado {

    //public static final String EMPLEADOS = "empleados";
    public static final String ERROR_AUTORIZACION = "Este usuario debe registrarse-Login";

    boolean UsuariAdmin = false;
    boolean conexio = false;

    String resposta;
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
     * @throws java.text.ParseException
     */
    public String LoginUsuari(String dni, String contraseña) throws ParseException {

        try {
            Gson gson = new Gson();
            boolean administrador;
            boolean encriptado = false;//cambiaremos a false cuando encriptemos servidor y cliente

            Statement st;
            ResultSet rs;

            ConexionBD cn = new ConexionBD();
            Connection con = cn.establecerConexion();
            //cuando encriptamos solo miraremos por dni y la contraseña al ser encriptada, la verificaremos después 
            st = (Statement) con.createStatement();
            rs = st.executeQuery("""
                                             SELECT idempleado,nombre,dni,"contraseña",administrador
                                             FROM empleados
                                             WHERE UPPER (dni) = UPPER ('""" + dni + "')");

            if (rs.next()) {
                String id = rs.getString(IDEMPLEADO);
                administrador = rs.getBoolean(ADMINISTRADOR);
                String contrasenaEncriptadaBD = rs.getString(CONTRASEÑA);
                // Encriptar la contraseña almacenada en la base de datos, cambiaremos por comparar directamente ya que estaran encriptadas. 
                //String contrasenaEncriptadaBD = Encriptar(contrasenaBD);
                if (contrasenaEncriptadaBD.equals(contraseña)) {
                    encriptado = true;

                    //comprobamos si por la Booleana es administrador o usuario. admiinstrador true=administrador y coinciden contraseñas
                    if (administrador && encriptado) {
                        conexio = true;
                        UsuariAdmin = true;
                        //generamos y validamos el token de usuario
                        token = TokenManager.generateToken(dni);
                        TokenManager.addToken(dni, token);
                        //pasem parametres Objecta gson administrador
                        JsonObject obtResposta = new JsonObject();
                        obtResposta.addProperty("correcte", conexio);
                        obtResposta.addProperty(ADMINISTRADOR, UsuariAdmin);
                        obtResposta.addProperty(DATOS_TOKEN, token);
                        resposta = gson.toJson(obtResposta);
                        cn.cerrarConexion();
                        return resposta;
                    } else if (!administrador && encriptado) {
                        conexio = true;
                        //generamos y validamos el token de usuario
                        token = token = TokenManager.generateToken(dni);
                        TokenManager.addToken(dni, token);
                        //pasem parametres Objecta gson Usuari
                        JsonObject obtResposta = new JsonObject();
                        obtResposta.addProperty("correcte", conexio);
                        obtResposta.addProperty(ADMINISTRADOR, UsuariAdmin);
                        obtResposta.addProperty(DATOS_TOKEN, token);
                        resposta = gson.toJson(obtResposta);
                        cn.cerrarConexion();
                        return resposta;

                    }
                }
            }
            //pasem parametres Objecte gson per dir que la conexxió no es correcta
            JsonObject obtResposta = new JsonObject();
            obtResposta.addProperty("correcte", conexio);
            obtResposta.addProperty(ADMINISTRADOR, UsuariAdmin);
            resposta = gson.toJson(obtResposta);
            System.out.println("No se encontro ningun empleado");
            cn.cerrarConexion();
            return resposta;
        } catch (SQLException ex) {
            Logger.getLogger(SqlEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resposta;
    }

    /**
     *
     * Obtenemos el token del usuario para cerrar sesión
     *
     * @param token
     * @return false para desconectar y eliminar el token asignado
     */
    public String LogoutUsuari(String token) {
        Gson gson = new Gson();

        conexio = false;
        //Eliminamos el token de usuario
        TokenManager.removeToken(token);
        //Borrem el token del Usuari
        conexio = true;
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        obtResposta.addProperty(NOMBRE, "");
        return resposta = gson.toJson(obtResposta);

    }

    /**
     * Insertamos Usuario nuevo
     *
     * @param datos
     * @return
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public String InsertarUsuari(ArrayList<String> datos) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        //int idempleado = Integer.parseInt("nextval ('sec_idempleado')");
        String nom = datos.get(1);
        String apellidos = datos.get(2);
        String dni = datos.get(3);
        String categoria = datos.get(4);
        int id_empresa = Integer.parseInt(datos.get(5));
        String contrasenya = Encriptar(datos.get(6));

        boolean administrador = Boolean.parseBoolean(datos.get(7));

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = """
                         INSERT INTO public.empleados(
                         idempleado, nombre, apellidos, dni, categoria, empresaid, "contraseña", administrador)
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
     * Funcion para Eliminar a un usuario por su dni
     * @param dni
     * @return
     * @throws SQLException 
     */
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
     * obtenemos la clase a la que se quiere listar o un empleado por idempleado
     *
     * @param variable
     * @return
     * @throws java.sql.SQLException
     */
    public String ListarEmpleados(String variable) throws SQLException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        if (variable.equalsIgnoreCase(EMPLEADOS)) {

            String sqlc = "SELECT * FROM " + variable + "\n ORDER BY idempleado ASC ";
            ps = con.prepareStatement(sqlc);

        } else {

            String sqli = """
                 SELECT * FROM public.empleados
                 WHERE idempleado = ?""";

            ps = con.prepareStatement(sqli);
            ps.setInt(1, Integer.parseInt(variable));
        }

        rs = ps.executeQuery();

        // Creamos un objeto JsonArray para almacenar los resultados
        JsonArray jsonArray = new JsonArray();

        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(IDEMPLEADO, rs.getInt(IDEMPLEADO));
            jsonObject.addProperty(NOMBRE, rs.getString(NOMBRE));
            jsonObject.addProperty(APELLIDOS, rs.getString(APELLIDOS));
            jsonObject.addProperty(DNI, rs.getString(DNI));
            jsonObject.addProperty(CATEGORIA, rs.getString(CATEGORIA));
            jsonObject.addProperty(EMPRESAID, rs.getInt(EMPRESAID));
            jsonObject.addProperty(CONTRASEÑA, Encriptar(rs.getString(CONTRASEÑA)));
            jsonObject.addProperty(ADMINISTRADOR, rs.getBoolean(ADMINISTRADOR));
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
    public String ModificarUsusario(String dni, String variable, String atributo) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE empleados "
                + " SET " + atributo + " = ? "
                + "WHERE dni = ?";

        ps = con.prepareStatement(sql);

        if (atributo.equalsIgnoreCase(ADMINISTRADOR)) {
            ps.setBoolean(1, Boolean.parseBoolean(variable));
            ps.setString(2, dni);

        } else if (atributo.equalsIgnoreCase(CONTRASEÑA)) {
            String contrasenya = Encriptar(variable);
            ps.setString(1, contrasenya);
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

    public String Encriptar(String contraseña) {
        Encriptador hash = new Encriptador();
        String contrasenaEncriptadaBD = hash.encriptarConSha256(contraseña);

        return contrasenaEncriptadaBD;

    }

}
