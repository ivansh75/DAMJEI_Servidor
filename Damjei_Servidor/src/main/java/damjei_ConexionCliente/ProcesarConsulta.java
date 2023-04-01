/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_BD.SqlUsuari;
import damjei_Objetos_Clases.ObjetoBenzina;
import damjei_Objetos_Clases.ObjetoConductor;
import damjei_Objetos_Clases.ObjetoEmpleados;
import damjei_Objetos_Clases.ObjetoMantenimiento;
import damjei_Objetos_Clases.ObjetoRepostar;
import damjei_Objetos_Clases.ObjetoRevisiones;
import damjei_Objetos_Clases.ObjetoVehiculo;
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

    String resposta = "";
    Connection conn;
    
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
                ObjetoEmpleados obem = new ObjetoEmpleados(); 
                return resposta = obem.ObjetoEmpleados(json);
            }else if(claseNombre.equalsIgnoreCase(CONDUCTOR)){
                ObjetoConductor obco = new ObjetoConductor();
          
            }else if(claseNombre.equalsIgnoreCase(VEHICLE)){
                ObjetoVehiculo obve = new ObjetoVehiculo();
                
            }else if(claseNombre.equalsIgnoreCase(REVISIONES)){
                ObjetoRevisiones obrev = new ObjetoRevisiones();
                
            }else if(claseNombre.equalsIgnoreCase(MANTENIMENT)){
                ObjetoMantenimiento obman = new ObjetoMantenimiento();
                
            }else if(claseNombre.equalsIgnoreCase(REPOSTAR)){
                ObjetoRepostar obrp = new ObjetoRepostar();
                
            }else if(claseNombre.equalsIgnoreCase(BENZINA)){
                ObjetoBenzina obbz = new ObjetoBenzina();
                
            }
        }
        return resposta;
    }

    public void close() throws SQLException {
        conn.close();
    }

}
