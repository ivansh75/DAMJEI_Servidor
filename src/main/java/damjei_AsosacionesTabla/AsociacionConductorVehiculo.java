/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_AsosacionesTabla;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_BD.ConexionBD;
import damjei_BD.SqlConductor;
import damjei_BD.SqlEmpleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ivimar
 */
public class AsociacionConductorVehiculo {

    public static final String EMPLEADO = "empleados";
    public static final String REPOSTAR = "repostar";
    public static final String VEHICULO = "vehiculo";

    private ArrayList<String> asCon = new ArrayList<>();
    String resposta;
    boolean actualizado = false;

    public boolean AsociacionConductorVehiculo(int idconductor) throws SQLException, ParseException {
        Gson gson = new Gson();

        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        //obtenemos el idvehiculo donde esta el conductorid
        String sqls = "SELECT  idvehiculo public.vehiculos "
                + " WHERE conductorid = ?";

        ps = con.prepareStatement(sqls);
        ps.setInt(1, idconductor);
        int results = ps.executeUpdate();

        if (results != 0) {
            //obtenemos el id del vehiculo y actualizamos a NULL el conductorid, ya que se va a borrar
            int idvehiculo = rs.getInt("idvehiculo");
            
            String sqlu = "UPDATE public.vehiculos "
                    + " SET conductorid  = NULL "
                    + "WHERE conductorid = ?";

            ps = con.prepareStatement(sqlu);
            ps.setInt(1, idvehiculo);
            
            int resultu = ps.executeUpdate();
        }
        
        actualizado = true;
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return actualizado;
    }

}
