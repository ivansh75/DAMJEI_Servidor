/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damjei_BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ivimar
 */
public class ConexionBD {

    //VARIABLES PARA CONEXION BD POSTGRESQL Y NUESTRA BD
    private static final String USUARIO = "postgres";
    private static final String PASSWORD = "isanchez";
    private static final String DB = "GestionMantenimientoFlotaVehiculo";
    private static final String IP = "localhost";
    private static final String PUERTO = "5432";
    //variables para la conexion a la BD y direccion con el jdbc de java
    Connection conectar = null;
    String cadena = "jdbc:postgresql://" + IP + ":" + PUERTO + "/" + DB;

    public ConexionBD() {

    }
    /**
     * 
     * @return conexio a labase dedaes
     */
    public Connection establecerConexion() {

        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena, USUARIO, PASSWORD);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la Base de Datos, error:" + e, "conexión", JOptionPane.ERROR_MESSAGE);
        }

        return conectar;
    }
    
    /**
     * void per cerrar la conesió a la base de dades si esta oberts
     * 
     */
    public void cerrarConexion() {

        try {
            if (conectar.isReadOnly()) {
                conectar.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Desconectar el Servidor, error:" + ex, "Desconexión", JOptionPane.ERROR_MESSAGE);
            
        }

    }

}
