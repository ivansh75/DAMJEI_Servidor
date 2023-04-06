/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivimar
 */
public class SqlDatos {

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;
    public static final int INSERTAR = 2;
    public static final int ACTUALITZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LLISTAR = 5;

    String respostaD;
    int accio;

    /**
     *
     * Constructor Simple
     */
    public SqlDatos() {
    }
    
    
    
    
    
    
    
    
    
    
    

    public String verTodosVehiculos(String clase) {
        Gson gson = new Gson();

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        Statement st;
        ResultSet rs;
        try {

            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT * FROM vehiculos");
            if (rs.next()) {
                respostaD = gson.toJson(rs);
            } else {
                respostaD = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        cn.cerrarConexion();
        return respostaD;
    }

}
