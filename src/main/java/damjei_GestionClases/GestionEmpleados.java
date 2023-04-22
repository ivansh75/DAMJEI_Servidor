/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlConductor;
import damjei_BD.SqlEmpleado;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;


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
    public static final int LISTARID = 7;

    public static final String ERROR_GESTIONUSUARIO = "Error en la gestion de Usuarios";
    public static final String ERROR_AUTORIZACION = "Este usuario debe registrarse-Login";

    String respostaCl;
    boolean conexio = false;

    /**
     *
     * Constructor simple
     */
    public GestionEmpleados() {
    }

    public String GestionEmpleados(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Empleat del JSON 
        JsonObject empleatJson = jsonObject.getAsJsonObject("empleat");
        if (empleatJson != null && !empleatJson.isJsonNull()) {
            request.setAccion(jsonObject.get("accio").getAsInt());

            switch (request.getAccion()) {
                case LOGIN: {
                    request.setString1(empleatJson.get("dni").getAsString());
                    request.setString2(empleatJson.get("contrasenya").getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
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
                    request.setString1(jsonObject.get("token").getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.LogoutUsuari(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }

                case INSERTAR: {
                    
                    request.setDatos(empleatJson.get(String.valueOf("idempleado")).getAsString());
                    request.setDatos(empleatJson.get("nom").getAsString());
                    request.setDatos(empleatJson.get("apellidos").getAsString());
                    request.setDatos(empleatJson.get("dni").getAsString());
                    request.setDatos(empleatJson.get("categoria").getAsString());
                    request.setDatos(empleatJson.get(String.valueOf("id_empresa")).getAsString());
                    request.setDatos(empleatJson.get("contrasenya").getAsString());
                    request.setDatos(empleatJson.get(String.valueOf("administrador")).getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarUsuari(request.getDatos());
                    
                    if (empleatJson.get("categoria").getAsString().equalsIgnoreCase("conductor")) {
                        //ArrayList<String> datos = new ArrayList<>();
                        String fecha =(empleatJson.get("fecha_carnet").getAsString());
                        String fechaCaducidad =(empleatJson.get("fecha_caducidad_carnet").getAsString());
                        SqlConductor sqlc = new SqlConductor();
                        String rsc = sqlc.InsertConductorFechas(fecha, fechaCaducidad);
                    }
                    respostaCl = rs;
                    return respostaCl;

                }
                case ACTUALIZAR: {
                    request.setString1(empleatJson.get("dni").getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
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

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.EliminarUsuario(request.getString1());

                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }
                case LISTAR: {
                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.ListarEmpleados(EMPLEADOS);
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }

                case MODIFICAR: {

                    request.setString1(empleatJson.get("dni").getAsString());
                    if (empleatJson.get("contrasenya") != null) {
                        request.setString2(empleatJson.get("contrasenya").getAsString());
                        request.setString3("contraseña");
                    } else if (empleatJson.get("categoria") != null) {
                        request.setString2(empleatJson.get("categoria").getAsString());
                        request.setString3("categoria");
                        } else if (empleatJson.get("fecha_caducidad_carnet") != null) {
                        request.setString2(empleatJson.get("fecha_caducidad_carnet").getAsString());
                        request.setString3("fecha_caducidad_carnet");
                    } else if (empleatJson.get("administrador") != null) {
                        request.setString2(empleatJson.get(String.valueOf("administrador")).getAsString());
                        request.setString3("administrador");
                    }
                    request.setId_String(empleatJson.get("id_empresa").getAsInt());
                    request.setAccion(jsonObject.get("accio").getAsInt());

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.ModificarUsusario(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }

                case LISTARID: {

                    request.setString1(empleatJson.get(String.valueOf("idempleado")).getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.ListarEmpleadosID(request.getString1());
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
