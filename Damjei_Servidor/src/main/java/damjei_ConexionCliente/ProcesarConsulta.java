/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_BD.SqlUsuari;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static String CLASE_EMPRESA = "Empresa";
    public static String CLASE_EMPLEADOS = "Empleats";
    public static String CLASE_CONDUCTOR = "Conductor";
    public static String CLASE_VEHIClE = "Vehicle";
    public static String CLASE_REVISIONES = "Revision";
    public static String CLASE_MANTENIMENT = "Manteniment";
    public static String CLASE_REPOSTAR = "Repostar";
    public static String CLASE_BENZINA = "Benzina";

    private Connection conn;
    String resposta;
    boolean iniciSesio = false;
    private ArrayList<String> data = new ArrayList<>();

    public ProcesarConsulta() {
    }

    public String ConsultaCliente(String json) {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest(resposta);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener la información de la clase
        JsonElement claseJson = jsonObject.get("clase");
        if (claseJson != null && !claseJson.isJsonNull()) {
            String claseNombre = claseJson.getAsString();
            System.out.println("------------------ClaseNombre---" + claseNombre);
            //Eliminamos comillas del String
            claseNombre = claseNombre.replaceAll("\'\'", "\'");
            if (claseNombre.equalsIgnoreCase(EMPLEATS)) {
                // Obtener el objeto Empleat del JSON 
                JsonObject empleatJson = jsonObject.getAsJsonObject("empleat");
                if (empleatJson != null && !empleatJson.isJsonNull()) {
                    request.setString1(empleatJson.get("nom").getAsString());
                    request.setString2(empleatJson.get("contrasenya").getAsString());
                    request.setId_String(empleatJson.get("id_empresa").getAsInt());
                    request.setAccion( jsonObject.get("accio").getAsInt());

                    SqlUsuari sql = new SqlUsuari();
                    String rs =sql.SqlUsuari(request.getString1(), request.getString2(), request.getAccion());
                    resposta =   request.ClientRequest(rs);
                }
            }
        }
        return resposta;
    }

    public void close() throws SQLException {
        conn.close();
    }

}
