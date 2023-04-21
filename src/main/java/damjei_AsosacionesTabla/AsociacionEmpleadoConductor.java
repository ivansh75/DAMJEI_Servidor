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
public class AsociacionEmpleadoConductor {

    public static final String EMPLEADO = "empleados";
    public static final String REPOSTAR = "repostar";
    public static final String VEHICULO = "vehiculo";

    private ArrayList<String> asCon = new ArrayList<>();
    String resposta;
    boolean actualizado = false;

    public boolean AsociarEmpleadoConductor(String dni) throws SQLException, ParseException {
        Gson gson = new Gson();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        //buscamos el id del empleado segun el dni del empleado
        String sqle = "SELECT  idempleado public.empleados "
                + " WHERE dni = ?";

        ps = con.prepareStatement(sqle);
        ps.setString(1, dni);
        int resulte = ps.executeUpdate();

        if (resulte != 0) {
            //obtenemos el idconductor y lo enviamos para buscar en la tabla vehiculo
            int empleadoid = rs.getInt("idempleado");
            
            //buscamos el id del conductor segun el id del empleado
            String sqls = "SELECT  idconductor public.conductor "
                    + " WHERE empleadoid = ?";

            ps = con.prepareStatement(sqls);
            ps.setInt(1, empleadoid);
            
            int results = ps.executeUpdate();
            
            
            if (results != 0) {
                //obtenemos el idconductor y lo enviamos para buscar en la tabla vehiculo
                int conductorid = rs.getInt("idconductor");
                    AsociacionConductorVehiculo ascv = new AsociacionConductorVehiculo();
                    actualizado = ascv.AsociacionConductorVehiculo(conductorid);
                    //una vez actualizada la tabla vehiculo,podemos borrar el conductor
                    String sqld = """
                     DELETE FROM public.conductor
                      WHERE idconductor = ?""";

                    ps = con.prepareStatement(sqld);
                    //ps.setInt(1,idempleado);
                    ps.setInt(1, conductorid);

                    int resultd = ps.executeUpdate();
                }

                actualizado = true;
                ps.close(); // Cerrar el PreparedStatement
                cn.cerrarConexion();
            }

            ps.close(); // Cerrar el PreparedStatement
            cn.cerrarConexion();
            return actualizado;

    }
    

    public boolean AsociarConductorEmpleado(String dni) throws SQLException, ParseException {
        Gson gson = new Gson();

        SqlEmpleado sqlEm = new SqlEmpleado();
        String empleado = sqlEm.ListarEmpleados(EMPLEADO);
        //hacemos el string en objetoque btenemos por array en este caso
        JsonArray jsonArray = gson.fromJson(empleado, JsonArray.class);
        for (JsonElement elemento : jsonArray) {
            JsonObject objeto = elemento.getAsJsonObject();
            if (objeto.get("dni").getAsString().equals(dni)) {
                String idempleado = objeto.get("idempleado").getAsString();

                //Falta insertar en tablaconductor idemepleado
                SqlConductor sqlCon = new SqlConductor();
                ;
                break;
            }
        }

        return actualizado;

    }

}
