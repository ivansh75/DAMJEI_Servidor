/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlVehiculos;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;


/**
 *
 * @author Ivimar
 */
public class GestionVehiculo {

    public static final String VEHICULOS = "vehiculos";
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;

    public static final String ERROR_GESTIONVEHICULOS = "Error en la gestion de Vehiculos";
  
    String respostaVh;
    boolean conexio = false;

    public GestionVehiculo() {
    }

    public String GestionVehiculo(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Vehicle del JSON 
        JsonObject vehiculosJson = jsonObject.getAsJsonObject("vehicle");
        if (vehiculosJson != null && !vehiculosJson.isJsonNull()) {
            request.setAccion(jsonObject.get("accio").getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(vehiculosJson.get(String.valueOf("idvehiculo")).getAsString());
                    request.setDatos(vehiculosJson.get("marca").getAsString());
                    request.setDatos(vehiculosJson.get("modelo").getAsString());
                    request.setDatos(vehiculosJson.get("matricula").getAsString());
                    request.setDatos(vehiculosJson.get(String.valueOf("kilometros_alta")).getAsString());
                    request.setDatos(vehiculosJson.get("fecha_alta").getAsString());
                    request.setDatos(vehiculosJson.get(String.valueOf("empresaid")).getAsString());
                    request.setDatos(vehiculosJson.get(String.valueOf("kilometros_actuales")).getAsString());

                    SqlVehiculos sql = new SqlVehiculos();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarVehiculo(request.getDatos());
                    respostaVh = rs;
                    return respostaVh;

                }
                case ACTUALIZAR: {
                    request.setString1(vehiculosJson.get("matricula").getAsString());

                    SqlVehiculos sql = new SqlVehiculos();
                    String rs = sql.ActualizarVehiculo(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaVh = rs;
                        return respostaVh;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONVEHICULOS);
                        return respostaVh = gson.toJson(error);
                    }
                }
                case ELIMINAR: {
                    request.setString1(vehiculosJson.get("matricula").getAsString());

                    SqlVehiculos sql = new SqlVehiculos();
                    String rs = sql.EliminarVehiculo(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaVh = rs;
                        return respostaVh;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONVEHICULOS);
                        return respostaVh = gson.toJson(error);
                    }
                }
                case LISTAR: {

                    SqlVehiculos sql = new SqlVehiculos();
                    String rs = sql.ListarVehiculo(VEHICULOS);
                    if (!rs.isEmpty()) {
                        respostaVh = rs;
                        return respostaVh;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONVEHICULOS);
                        return respostaVh = gson.toJson(error);
                    }
                }

                case MODIFICAR:

                    request.setString1(vehiculosJson.get("matricula").getAsString());

                    if (vehiculosJson.get("fecha_baja") != null) {
                        request.setString2(vehiculosJson.get("fecha_baja").getAsString());
                        request.setString3("fecha_baja");
                    } else if (vehiculosJson.get(String.valueOf("conductorid")) != null) {
                        request.setString2(vehiculosJson.get("conductorid").getAsString());
                        request.setString3("conductorid");
                    } else if (vehiculosJson.get(String.valueOf("Kilometros_actuales")) != null) {
                        request.setString2(vehiculosJson.get(String.valueOf("Kilometros_actuales")).getAsString());
                        request.setString3("Kilometros_actuales");
                    }

                    SqlVehiculos sql = new SqlVehiculos();
                    String rs = sql.ModificarVehiculo(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaVh = rs;
                        return respostaVh;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONVEHICULOS);
                        return respostaVh = gson.toJson(error);
                    }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONVEHICULOS);
        return respostaVh = gson.toJson(error);
    }

}
