/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_BD.SqlUsuari;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase donde obtenemos el Gson objeto del empleado
 *
 * @author Ivimar
 */
public class GestionEmpleados {
    public static final String ERROR_GESTIONUSUARIO = "Error en la gestion de Usuarios";

    String respostaCl;
    boolean conexio = false;
    private ArrayList<String> list = new ArrayList<>();

    public GestionEmpleados() {
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
                switch (request.getAccion()) {
                    case 0:
                    case 1: {
                        request.setString1(empleatJson.get("dni").getAsString());
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
                    }
                    case 2: {
                        //ArrayList<String> datos = new ArrayList<>();
                        request.setDatos(empleatJson.get(String.valueOf("idempleado")).getAsString());
                        request.setDatos(empleatJson.get("nom").getAsString());
                        request.setDatos(empleatJson.get("apellidos").getAsString());
                        request.setDatos(empleatJson.get("dni").getAsString());
                        request.setDatos(empleatJson.get("categoria").getAsString());
                        request.setDatos(empleatJson.get(String.valueOf("id_empresa")).getAsString());
                        request.setDatos(empleatJson.get("contrasenya").getAsString());
                        request.setDatos(empleatJson.get(String.valueOf("administrador")).getAsString());
                        request.setDatos(jsonObject.get(String.valueOf("accio")).getAsString());

                        SqlUsuari sql = new SqlUsuari();
                        //para asignar valores el 0 el el array, el 1 idempleado......
                        String rs = sql.InsertarUsuari(request.getDatos());
                        respostaCl = rs;
                        return respostaCl;

                    }
                    case 3:
                        request.setString1(empleatJson.get("dni").getAsString());
                        if (empleatJson.get("contrasenya").getAsString() != null) {
                            request.setString2(empleatJson.get("contrasenya").getAsString());
                            request.setString3("contraseña");
                        } else if (empleatJson.get("categoria").getAsString() != null) {
                            request.setString2(empleatJson.get("categoria").getAsString());
                            request.setString3("categoria");
                        } else if (empleatJson.get("administrador").getAsString() != null) {
                            request.setString2(empleatJson.get(String.valueOf("administrador")).getAsString());
                            request.setString3("administrador");
                        }
                        request.setId_String(empleatJson.get("id_empresa").getAsInt());
                        request.setAccion(jsonObject.get("accio").getAsInt());

                        SqlUsuari sql = new SqlUsuari();
                        String rs = sql.SqlUsuari(request.getString1(), request.getString2(), request.getAccion(), request.getString3());
                        if (!rs.isEmpty()) {
                            respostaCl = rs;
                            return respostaCl;
                        } else {
                            String error = ("correcta:" + conexio + "error:" + ERROR_GESTIONUSUARIO);
                            return respostaCl = gson.toJson(ERROR_GESTIONUSUARIO);
                        }
                }
            }

        }
        return null;
    }

}
