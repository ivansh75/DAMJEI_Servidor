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
    
    public String ConsultaCliente(String json) {

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
                // Obtener el objeto Empleat del JSON 
                JsonObject empleatJson = jsonObject.getAsJsonObject("empleat");
                if (empleatJson != null && !empleatJson.isJsonNull()) {
                    request.setString1(empleatJson.get("nom").getAsString());
                    request.setString2(empleatJson.get("contrasenya").getAsString());
                    request.setId_String(empleatJson.get("id_empresa").getAsInt());
                    request.setAccion(jsonObject.get("accio").getAsInt());

                    SqlUsuari sql = new SqlUsuari();
                    String rs = sql.SqlUsuari(request.getString1(), request.getString2(), request.getAccion(), request.getClase());
                    if (!rs.isEmpty()) {
                        resposta = rs;

                    } else {
                        return null;
                    }
                }
            }
        }
        return resposta;
    }

    public void close() throws SQLException {
        conn.close();
    }

}
