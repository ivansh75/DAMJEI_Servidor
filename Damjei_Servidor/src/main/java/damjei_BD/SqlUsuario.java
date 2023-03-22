/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ivimar
 */
public class SqlUsuario {

    public SqlUsuario() {
    }

    public String comprobarUsuario(String nombre, String dni) {
        
        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String CategoriaAdmin = "rrhh";
        String CategoriaConductor = "conductor";
        Statement st;
        ResultSet rs;
        String categoria = "";
        String n = "";
        int idempleadoAdmin = 1;
        int idempleadoUsern = 2;

        try {
            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT idempleado,nombre,dni,categoria\n"
                    + "FROM empleados\n"
                    + "WHERE UPPER (nombre) = UPPER ('" + nombre + "') AND dni = '" + dni + "'");

            if (rs.next()) {
                n = rs.getString("idempleado").substring(0);
                
                categoria = rs.getString("categoria").substring(0);
            } else {
                n = null;
                System.out.println("No se encontro ningun empleado");
            }
            //comprobamos si por categoria es administrador o usuario conductor
            if (categoria.equalsIgnoreCase(CategoriaAdmin)) {
                System.out.println("Administrador_____________----------" + n);
                return  n.trim();
            } else if (categoria.equalsIgnoreCase(CategoriaConductor)) {
                return n.trim();
            } else {
                JOptionPane.showMessageDialog(null, "El Usuario o password es Incorrecto" + JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n.trim();

    }

    public ResultSet verTodosVehiculos() {

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

        return rs;
    }

}
