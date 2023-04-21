/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_AsosacionesTabla;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_BD.SqlConductor;
import damjei_BD.SqlEmpleado;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ivimar
 */
public class AsociacionVehiculoRevisiones {

    public static final String EMPLEADO = "empleados";
    public static final String REPOSTAR = "repostar";
    public static final String VEHICULO = "vehiculo";

    private ArrayList<String> asCon = new ArrayList<>();
    String resposta;
    boolean conexio = false;

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

        return conexio;

    }

}
