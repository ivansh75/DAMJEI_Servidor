/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlVehiculos;
import damjei_ConexionCliente.ClientRequest;
import static damjei_GestionClases.GestionEmpleados.ELIMINAR;
import static damjei_GestionClases.GestionEmpleados.INSERTAR;
import static damjei_GestionClases.GestionEmpleados.LISTAR;
import static damjei_GestionClases.GestionEmpleados.LISTARID;
import static damjei_GestionClases.GestionEmpleados.MODIFICAR;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ivimar
 */
public class GestionVehiculo {

    //constantes de los titulos atributos
    public static final String IDVEHICULO = "idvehiculo";
    public static final String MARCA = "marca";
    public static final String MODELO = "modelo";
    public static final String MATRICULA = "matricula";
    public static final String KILOMETROS_ALTA = "kilometros_alta";
    public static final String FECHA_ALTA = "fecha_alta";
    public static final String FECHA_BAJA = "fecha_baja";
    public static final String CONDUCTORID = "conductorid";
    public static final String EMPRESAID = "empresaid";
    public static final String KILOMETROS_ACTUALES = "kilometros_actuales";
    public static final String DATOS_VEHICLE = "vehicle";
    public static final String DATOS_CLASE = "clase";
    public static final String DATOS_ACCION = "accio";
    public static final String VEHICULOS = "vehiculos";

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
        JsonObject vehiculosJson = jsonObject.getAsJsonObject(DATOS_VEHICLE);
        if (vehiculosJson != null && !vehiculosJson.isJsonNull()) {
            request.setAccion(jsonObject.get(DATOS_ACCION).getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(vehiculosJson.get(String.valueOf(IDVEHICULO)).getAsString());
                    request.setDatos(vehiculosJson.get(MARCA).getAsString());
                    request.setDatos(vehiculosJson.get(MODELO).getAsString());
                    request.setDatos(vehiculosJson.get(MATRICULA).getAsString());
                    request.setDatos(vehiculosJson.get(String.valueOf(KILOMETROS_ALTA)).getAsString());
                    request.setDatos(vehiculosJson.get(FECHA_ALTA).getAsString());
                    request.setDatos(vehiculosJson.get(String.valueOf(EMPRESAID)).getAsString());
                    request.setDatos(vehiculosJson.get(String.valueOf(KILOMETROS_ACTUALES)).getAsString());

                    SqlVehiculos sql = new SqlVehiculos();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarVehiculo(request.getDatos());
                    respostaVh = rs;
                    return respostaVh;

                }
                case ELIMINAR: {
                    request.setString1(vehiculosJson.get(MATRICULA).getAsString());

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
                case MODIFICAR: {

                    request.setString1(vehiculosJson.get(MATRICULA).getAsString());

                    if (vehiculosJson.get(FECHA_BAJA) != null) {
                        request.setString2(vehiculosJson.get(FECHA_BAJA).getAsString());
                        request.setString3(FECHA_BAJA);
                    } else if (vehiculosJson.get(String.valueOf(CONDUCTORID)) != null) {
                        request.setString2(vehiculosJson.get(CONDUCTORID).getAsString());
                        request.setString3(CONDUCTORID);
                    } else if (vehiculosJson.get(String.valueOf(KILOMETROS_ACTUALES)) != null) {
                        request.setString2(vehiculosJson.get(String.valueOf(KILOMETROS_ACTUALES)).getAsString());
                        request.setString3(KILOMETROS_ACTUALES);
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
                case LISTARID: {

                    request.setString1(vehiculosJson.get(IDVEHICULO).getAsString());

                    SqlVehiculos sql = new SqlVehiculos();
                    String rs = sql.ListarVehiculo(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaVh = rs;
                        return respostaVh;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONVEHICULOS);
                        return respostaVh = gson.toJson(error);
                    }
                }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONVEHICULOS);
        return respostaVh = gson.toJson(error);
    }

}
