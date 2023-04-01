/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_Objetos_Clases;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_BD.SqlUsuari;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ivimar
 */
public class ObjetoEmpleados {

    String respostaCl;
    private ArrayList<String> list = new ArrayList<>();

    public ObjetoEmpleados() {
    }

    public String ObjetoEmpleados(String json) throws SQLException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        // Obtener la información de la clase
        JsonElement claseJson = jsonObject.get("clase");
        if (claseJson != null && !claseJson.isJsonNull()) {
            String claseNombre = claseJson.getAsString();
            //Eliminamos comillas del String
            claseNombre = claseNombre.replaceAll("\'\'", "\'");
            // Obtener el objeto Empleat del JSON 
            JsonObject empleatJson = jsonObject.getAsJsonObject("empleat");
            if (empleatJson != null && !empleatJson.isJsonNull()) {
                request.setAccion(jsonObject.get("accio").getAsInt());
                //comprobamos que no se una introducion de usuario que vaya con array
                if (request.getAccion() != 2) {
                    request.setString1(empleatJson.get("nom").getAsString());
                    request.setString2(empleatJson.get("contrasenya").getAsString());
                    request.setId_String(empleatJson.get("id_empresa").getAsInt());
                    request.setAccion(jsonObject.get("accio").getAsInt());
                    

                    SqlUsuari sql = new SqlUsuari();
                    String rs = sql.SqlUsuari(request.getString1(), request.getString2(), request.getAccion(), claseNombre);
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        return null;
                    }
                } else {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(empleatJson.get(String.valueOf("idempleado")).getAsString());
                    request.setDatos(empleatJson.get("nom").getAsString());
                    request.setDatos(empleatJson.get("apellidos").getAsString());
                    request.setDatos(empleatJson.get("dni").getAsString());
                    request.setDatos(empleatJson.get("categoria").getAsString());
                    request.setDatos(empleatJson.get(String.valueOf("id_empresa")).getAsString());
                    request.setDatos(empleatJson.get("contrasenya").getAsString());
                    request.setDatos(empleatJson.get("administrador").getAsString());
                    request.setDatos(jsonObject.get(String.valueOf("accio")).getAsString());
                    
                    System.out.println("-------------estoy en insertar------");
                    System.out.println(request.getDatos());
                    
                    SqlUsuari sql = new SqlUsuari();
                    String rs = sql.InsertarUsuari(request.getDatos());
                    respostaCl = rs;
                    return respostaCl;
                }
            }

        }
        return null;
    }

}
