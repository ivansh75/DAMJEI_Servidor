/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
    Statement st;
    ResultSet rs;
    String categoria;
    String resposta;
    int accio;
    int token;

    private ArrayList<String> datos = new ArrayList<>();

    public SqlUsuari() {
    }

    public String SqlUsuari(String nom, String dni, int accio) {
        //int num =Integer.parseInt(accio.getAsString());
        int num = accio;

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
                resposta = LListarUsuari(dni);
                return resposta;

        }
        return null;
    }

    public String LoginUsuari(String nombre, String dni) {

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        boolean UsuariAdmin = false;
        boolean User = false;
        Statement st;
        ResultSet rs;
        boolean usuari = false;

        try {
            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT nombre,dni,categoria\n"
                    + "FROM empleados\n"
                    + "WHERE UPPER (nombre) = UPPER ('" + nombre + "') AND dni = '" + dni + "'");

            if (rs.next()) {
                categoria = rs.getString("categoria").substring(0);
            } else {
                resposta = null;
                System.out.println("No se encontro ningun empleado");
            }
            //comprobamos si por categoria es administrador o usuario conductor
            if (categoria.equalsIgnoreCase(CategoriaAdmin)) {
                usuari = true;
                UsuariAdmin = true;
                int token = 456;
                //pasem parametres Objecta gson administrador
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", usuari);
                obtResposta.addProperty("administrador", UsuariAdmin);
                obtResposta.addProperty("token", token);
                resposta = gson.toJson(obtResposta);;
                return resposta;
            } else if (categoria.equalsIgnoreCase(CategoriaConductor)) {
                usuari = true;
                User = true;
                int token = 456;
                //pasem parametres Objecta gson Usuari
                JsonObject obtResposta = new JsonObject();
                obtResposta.addProperty("correcte", usuari);
                obtResposta.addProperty("administrador", User);
                obtResposta.addProperty("token", token);
                resposta = gson.toJson(obtResposta);;
                return resposta;
            } else {
                JOptionPane.showMessageDialog(null, "El Usuario o password es Incorrecto" + JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        cn.cerrarConexion();
        return resposta = null;

    }

    public String LogoutUsuari(String nombre, String dni) {
        token = 0;
        //Borrem el token del Usuari
        JsonObject obtResposta = new JsonObject();
        obtResposta.addProperty("nombre", nombre);
        obtResposta.addProperty("dni", dni);
        obtResposta.addProperty("token", token);
        return resposta = gson.toJson(obtResposta);

    }

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
