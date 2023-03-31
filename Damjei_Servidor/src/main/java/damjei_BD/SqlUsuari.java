/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.TokenManager;
import java.sql.Connection;
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

    Gson gson = new Gson();
    String CategoriaAdmin = "rrhh";
    String CategoriaConductor = "conductor";
    boolean UsuariAdmin = false;
    boolean conexio = false;
    Statement st;
    ResultSet rs;
    String categoria;
    String resposta;
    int accio;
    String token;
    boolean islogin = false;

    private ArrayList<String> datos = new ArrayList<>();

    public SqlUsuari() {
    }
    /**
     * Recibimos los parametros para gestionar Cliente y accion a realizar
     * 
     * @param nom
     * @param dni
     * @param accio
     * @return 
     */
    public String SqlUsuari(String nom, String dni, int accio, String clase) {
        int num = accio;
        if (!islogin && num != 0) {
            num = 6;
        }

        switch (num) {
            case 0:
                resposta = LoginUsuari(nom, dni);
                return resposta;
            case 1:
                resposta = LogoutUsuari(nom, dni);
                return resposta;
            case 2:
                resposta = InsertarUsuari(datos);
                return resposta;
            case 3:
                resposta = ActualizarUsuari(dni);
                return resposta;
            case 4:
                resposta = EliminarUsuari(dni);
                return resposta;
            case 5:
                resposta = LListarUsuari(clase);
                return resposta;
            case 6:
                resposta = LogoutUsuari(nom, dni);
                return resposta;

        }
        return null;
    }
    /**
     * 
     * Comprobamos para logear al cliente y definir tipo usuario
     * @param nombre
     * @param dni
     * @return Si es autorizado añadimos el token con su dni sino,
     * usuario false y no añadimos token
     */
    public String LoginUsuari(String nombre, String dni) {

        try {

            ConexionBD cn = new ConexionBD();
            Connection con = cn.establecerConexion();

            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT nombre,dni,categoria\n"
                    + "FROM empleados\n"
                    + "WHERE UPPER (nombre) = UPPER ('" + nombre + "') AND dni = '" + dni + "'");

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
     * @param nombre
     * @param dni
     * @return false para desconectar y eliminar el token asignado
     */
    public String LogoutUsuari(String nombre, String dni) {
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
     * @param clase
     * @return 
     */
    public String empleats(String clase) {
        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        try {

            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT * From " + clase);

            if (rs.next()) {
                datos = (ArrayList<String>) rs;
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
    
    public ResultSet verTodosVehiculos(String clase) {

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        Statement st;
        ResultSet rs = null;

        try {
            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT * FROM vehiculos");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        cn.cerrarConexion();
        return rs;
    }

    private String InsertarUsuari(ArrayList<String> datos) {
        return null;
    }

    private String ActualizarUsuari(String dni) {
        return null;
    }

    private String EliminarUsuari(String dni) {
        return null;
    }

    private String LListarUsuari(String dni) {
        return null;
    }

}
