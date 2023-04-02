/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.ClientRequest;
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
public class SqlUsuari {

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;
    public static final int INSERTAR = 2;
    public static final int ACTUALITZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LLISTAR = 5;

    public static final String ERROR_AUTORIZACION = "Este usuario debe registrarse-Login";

    String CategoriaAdmin = "rrhh";
    String CategoriaConductor = "conductor";
    boolean UsuariAdmin = false;
    boolean conexio = false;

    String resposta;
    int accio;
    String token;
    boolean islogin = false;

    private ArrayList<String> datosEmp = new ArrayList<>();

    public SqlUsuari() {
    }

    /**
     * Recibimos los parametros para gestionar Cliente y accion a realizar
     *
     * @param dni
     * @param variable este valor puede ser contrasenya,categoria o
     * administrador
     * @param accio
     * @param clase
     * @return
     * @throws java.sql.SQLException
     */
    public String SqlUsuari(String dni, String variable, int accio, String clase) throws SQLException {
        ClientRequest request = new ClientRequest();
        int num = accio;
        int numToken = TokenManager.validateToken(request.getToken());
        if (numToken != -1 && num != 0) {
            num = 6;
        }

        switch (num) {
            case 0:
                resposta = LoginUsuari(dni, variable);
                return resposta;
            case 1:
                resposta = LogoutUsuari(dni, variable);
                return resposta;
            case 3:
                resposta = ActualizarUsuari(dni, variable, clase);
                return resposta;
            case 4:
                resposta = EliminarUsuari(dni);
                return resposta;
            case 5:
                resposta = LListarUsuari(clase);
                return resposta;
            case 6:
                resposta = LogoutUsuari(dni, variable);
                return ERROR_AUTORIZACION;

        }
        return ERROR_AUTORIZACION;
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
        String categoria;
        Statement st;
        ResultSet rs;

        try {

            ConexionBD cn = new ConexionBD();
            Connection con = cn.establecerConexion();

            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT nombre,dni,categoria\n"
                    + "FROM empleados\n"
                    + "WHERE UPPER (dni) = UPPER ('" + dni + "') AND dni = '" + contraseña + "'");

            if (rs.next()) {
                categoria = rs.getString("categoria").substring(0);
                //comprobamos si por categoria es administrador o usuario conductor
                if (categoria.equalsIgnoreCase(CategoriaAdmin)) {
                    islogin = true;
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
                } else if (categoria.equalsIgnoreCase(CategoriaConductor)) {

                    islogin = true;
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
                islogin = false;
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
            Logger.getLogger(SqlUsuari.class.getName()).log(Level.SEVERE, null, ex);
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
    private String LogoutUsuari(String dni, String contraseña) {
        Gson gson = new Gson();

        islogin = false;
        conexio = false;
        //Eliminamos el token de usuario
        TokenManager.removeToken(dni);
        //Borrem el token del Usuari
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("correcte", conexio);
        obtResposta.addProperty("nombre", "");
        return resposta = gson.toJson(obtResposta);

    }

    /**
     * obtenemos la clase a la que se quiere listar
     *
     * @param clase
     * @return
     */
    private String empleats(String clase) {
        Gson gson = new Gson();
        Statement st;
        ResultSet rs;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        try {

            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT * From " + clase);

            if (rs.next()) {
                datosEmp = (ArrayList<String>) rs;
                cn.cerrarConexion();
                return resposta = gson.toJson(datosEmp);
            } else {
                resposta = null;
                System.out.println("No se encontro ningun empleado");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuari.class.getName()).log(Level.SEVERE, null, ex);
        }

        cn.cerrarConexion();
        return resposta;
    }

    public String InsertarUsuari(ArrayList<String> datos) throws SQLException {
        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        int numToken = TokenManager.validateToken(request.getToken());
        //comprovamos que tenga el token y sea unusuario registrado  
        if (numToken == -1) {
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
            if (result != 0) {
                conexio = true;
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", conexio);
                resposta = gson.toJson(obtResposta);
                cn.cerrarConexion();
                return resposta;
            }
        }
        return resposta = gson.toJson(conexio);
    }

    private String ActualizarUsuari(String dni, String variable, String atributo) throws SQLException {
        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sql = "UPDATE empleados "
                + " SET " + atributo + " = ? "
                + "WHERE dni = ?";

        ps = con.prepareStatement(sql);
        ps.setString(1, variable);
        ps.setString(2, dni);

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

    private String EliminarUsuari(String dni) {
        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        return null;
    }

    private String LListarUsuari(String dni) {
        return null;
    }

}
