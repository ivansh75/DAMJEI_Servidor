/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_GestionClases.GestionBenzina;
import damjei_GestionClases.GestionConductor;
import damjei_GestionClases.GestionEmpleados;
import damjei_GestionClases.GestionMantenimiento;
import damjei_GestionClases.GestionRepostar;
import damjei_GestionClases.GestionRevisiones;
import damjei_GestionClases.GestionVehiculo;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author Ivimar
 */
public class ProcesarConsulta {

    public static final String EMPRESA = "Empresa.class";
    public static final String EMPLEATS = "Empleats.class";
    public static final String CONDUCTOR = "Conductor.class";
    public static final String VEHICLE = "Vehicle.class";
    public static final String REVISIONES = "Revisions.class";
    public static final String MANTENIMENT = "Mantenimet.class";
    public static final String REPOSTAR = "Repòstar.class";
    public static final String BENZINA = "Benzina.class";
    
    public static final String ERROR_PROCEASR_CONSULTA = "Error de sintexis al procesar consulta";

    Connection conn;
    boolean conexio = false;
    String resposta = "";
    
    
    /**
     * Constructor simple
     */
    public ProcesarConsulta() {
    }
    
    public String ConsultaCliente(String json) throws SQLException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener la información de la clase
        JsonElement claseJson = jsonObject.get("clase");
        if (claseJson != null && !claseJson.isJsonNull()) {
            String claseNombre = claseJson.getAsString();

            //Eliminamos comillas del String
            claseNombre = claseNombre.replaceAll("\'\'", "\'");
            if (claseNombre.equalsIgnoreCase(EMPLEATS)) {
                GestionEmpleados obem = new GestionEmpleados(); 
                return resposta = obem.ObjetoEmpleados(json);
            }else if(claseNombre.equalsIgnoreCase(CONDUCTOR)){
                GestionConductor obco = new GestionConductor();
          
            }else if(claseNombre.equalsIgnoreCase(VEHICLE)){
                GestionVehiculo obve = new GestionVehiculo();
                
            }else if(claseNombre.equalsIgnoreCase(REVISIONES)){
                GestionRevisiones obrev = new GestionRevisiones();
                
            }else if(claseNombre.equalsIgnoreCase(MANTENIMENT)){
                GestionMantenimiento obman = new GestionMantenimiento();
                
            }else if(claseNombre.equalsIgnoreCase(REPOSTAR)){
                GestionRepostar obrp = new GestionRepostar();
                
            }else if(claseNombre.equalsIgnoreCase(BENZINA)){
                GestionBenzina obbz = new GestionBenzina();
                
            }
        }
        String error = ("correcta:" + conexio + "error:" + ERROR_PROCEASR_CONSULTA);
        return resposta = gson.toJson(error);
    }

    public void close() throws SQLException {
        conn.close();
    }

}
