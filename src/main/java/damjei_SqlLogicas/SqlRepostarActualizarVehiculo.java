/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_SqlLogicas;

import damjei_BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Actualizamos el kilometraje del vehiculo cada vez que se haga un repostage y
 * comprobamos si tiene revisiones
 *
 * @author Ivimar
 */
public class SqlRepostarActualizarVehiculo {

    boolean conexio = false;

    public SqlRepostarActualizarVehiculo() {
    }

    public boolean SqlRepostarActualizarVehiculos(float kilometros, int idvehiculo) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();

        String sqlActr = """
                    UPDATE public.revisiones
                     SET  kilometros_revision=?
                     WHERE idrevision =? """;

        ps = con.prepareStatement(sqlActr);
        ps.setFloat(1, kilometros);
        ps.setInt(2, idvehiculo);

        int result = ps.executeUpdate();
        if (result != 1) {
            //rs.close(); // Cerrar el ResultSet
            ps.close(); // Cerrar el PreparedStatement
            cn.cerrarConexion();
            return conexio;
        } else {
            conexio = true;
        }

        //rs.close(); // Cerrar el ResultSet
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return conexio;

    }

}
