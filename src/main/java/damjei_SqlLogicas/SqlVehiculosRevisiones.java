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
import java.text.ParseException;
import java.util.Date;

/**
 * Relación de vehiculo nuevo con las revisiones a realizar de los
 * mantenimientos que tendran los vehiculos
 *
 * @author Ivimar
 */
public class SqlVehiculosRevisiones {

    boolean conexio = false;

    public SqlVehiculosRevisiones() {
    }

    /**
     * Por cada vehiculo insertado nuevo, le asignamos las revisiones de los
     * mantenimientos de vehiculos
     *
     * @param fecha
     * @param kilometros
     * @param matricula
     * @return conexio a true si se ha realizado correctamente
     * @throws SQLException
     * @throws ParseException
     */
    public boolean SqlVehiculosRevisionesion(Date fecha, float kilometros, String matricula) throws SQLException, ParseException {
        int idvehiculo = 0;
        boolean estado_revision = true;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        //obtenemos el id del vehiculo insertado
        String sql = """
                 SELECT idvehiculo FROM public.vehiculos
                 WHERE matricula = ?""";

        ps = con.prepareStatement(sql);
        ps.setString(1, matricula);

        rs = ps.executeQuery();

        while (rs.next()) {

            idvehiculo = rs.getInt(1);

        }

        //Obtener los id de mantenimiento para asociar añadirlos a las revisiones
        String sqlMnttoid = "SELECT idmantenimiento FROM mantenimiento";
        ps = con.prepareStatement(sqlMnttoid);
        ResultSet rsMtto = ps.executeQuery();

        while (rsMtto.next()) {
            int mttoid = rsMtto.getInt("idmantenimiento");

            String sqlR = """
                    INSERT INTO public.revisiones(
                     idrevision, "fecha_revision", kilometros_revision, vehiculoid, mantenimientoid, estado_revision)
                     VALUES (nextval('sec_idrevisiones'), ?, ?, ?, ?, ?)""";

            ps = con.prepareStatement(sqlR);
            //Añadimos el arrayList y enviamos a insertar revisiones
            ps.setDate(1, new java.sql.Date(fecha.getTime()));
            ps.setFloat(2, kilometros);
            ps.setInt(3, idvehiculo);
            ps.setInt(4, mttoid);
            ps.setBoolean(5, estado_revision);

            int result = ps.executeUpdate();
            if (result != 1) {
                rs.close(); // Cerrar el ResultSet
                ps.close(); // Cerrar el PreparedStatement
                cn.cerrarConexion();
                return conexio;
            } else {

                conexio = true;
            }

        }

        rs.close(); // Cerrar el ResultSet
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return conexio;

    }

}
