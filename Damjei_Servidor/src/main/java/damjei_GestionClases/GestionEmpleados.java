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
import damjei_ConexionCliente.TokenManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase donde obtenemos el Gson objeto del empleado
 *
 * @author Ivimar
 */
public class GestionEmpleados {

    public static final String EMPLEADOS = "empleados";
    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;

    public static final String ERROR_GESTIONUSUARIO = "Error en la gestion de Usuarios";
    public static final String ERROR_AUTORIZACION = "Este usuario debe registrarse-Login";

    String respostaCl;
    boolean conexio = false;
    private ArrayList<String> list = new ArrayList<>();

    /**
     *
     * Constructor simple
     */
    public GestionEmpleados() {
    }

    public String GestionEmpleados(String json) throws SQLException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        // Obtener la información de la clase
        JsonElement claseJson = jsonObject.get("clase");
        if (claseJson != null && !claseJson.isJsonNull()) {
            String claseNombre = claseJson.getAsString();
            //Eliminamos comillas del String
            //claseNombre = claseNombre.replaceAll("\'\'", "\'");
            // Obtener el objeto Empleat del JSON 
            JsonObject empleatJson = jsonObject.getAsJsonObject("empleat");
            if (empleatJson != null && !empleatJson.isJsonNull()) {
                request.setAccion(jsonObject.get("accio").getAsInt());
                
                switch (request.getAccion()) {
                    case LOGIN: {
                        request.setString1(empleatJson.get("dni").getAsString());
                        request.setString2(empleatJson.get("contrasenya").getAsString());

                        SqlUsuari sql = new SqlUsuari();
                        String rs = sql.LoginUsuari(request.getString1(), request.getString2());
                        if (!rs.isEmpty()) {
                            respostaCl = rs;
                            return respostaCl;
                        } else {
                            String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                            return respostaCl = gson.toJson(error);
                        }
                    }

                    case LOGOUT: {
                        request.setString1(empleatJson.get("dni").getAsString());
                        request.setString2(empleatJson.get("contrasenya").getAsString());

                        SqlUsuari sql = new SqlUsuari();
                        String rs = sql.LogoutUsuari(request.getString1(), request.getString2());
                        if (!rs.isEmpty()) {
                            respostaCl = rs;
                            return respostaCl;
                        } else {
                            String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                            return respostaCl = gson.toJson(error);
                        }
                    }

                    case INSERTAR: {
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
                    case ACTUALIZAR: {
                        request.setString1(empleatJson.get("dni").getAsString());

                        SqlUsuari sql = new SqlUsuari();
                        String rs = sql.ActualizarUsuario(request.getString1());
                        if (!rs.isEmpty()) {
                            respostaCl = rs;
                            return respostaCl;
                        } else {
                            String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                            return respostaCl = gson.toJson(error);
                        }
                    }
                    case ELIMINAR: {
                        request.setString1(empleatJson.get("dni").getAsString());

                        SqlUsuari sql = new SqlUsuari();
                        String rs = sql.ElimiinarUsuario(request.getString1());
                        if (!rs.isEmpty()) {
                            respostaCl = rs;
                            return respostaCl;
                        } else {
                            String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                            return respostaCl = gson.toJson(error);
                        }
                    }
                    case LISTAR: {

                        SqlUsuari sql = new SqlUsuari();
                        String rs = sql.ListarEmpleados(EMPLEADOS);
                        if (!rs.isEmpty()) {
                            respostaCl = rs;
                            return respostaCl;
                        } else {
                            String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONUSUARIO);
                            return respostaCl = gson.toJson(error);
                        }
                    }

                    case MODIFICAR:

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
                        String rs = sql.ModificarUsusario(request.getString1(), request.getString2(), request.getString3());
                        if (!rs.isEmpty()) {
                            respostaCl = rs;
                            return respostaCl;
                        } else {
                            String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONUSUARIO);
                            return respostaCl = gson.toJson(error);
                        }
                }

            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
        return respostaCl = gson.toJson(error);
    }
}
