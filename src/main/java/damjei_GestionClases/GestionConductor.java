/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlConductor;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ivimar
 */
public class GestionConductor {
   
    public static final String CONDUCTOR = "conductor";
    //constantes de los titulos atributos
    public static final String IDCONDUCTOR = "idconductor";
    public static final String FECHA_CARNET = "fecha_carnet";
    public static final String FECHA_CADUCIDAD = "fecha_caducidad_carnet";
    public static final String EMPLEADOID = "empleadoid";
    public static final String DATOS_CONDUCTOR = "conductor";
    public static final String DATOS_CLASE = "clase";
    public static final String DATOS_ACCION = "accio";
    public static final String DATOS_TOKEN = "token";
    //constantes para las acciones
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;

    public static final String ERROR_GESTIONCONDUCTOR = "Error en la gestion de Conductor";

    String respostaCon;
    boolean conexio = false;

    public GestionConductor() {
    }

    public String GestionConductor(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Vehicle del JSON 
        JsonObject conductorJson = jsonObject.getAsJsonObject(CONDUCTOR);
        if (conductorJson != null && !conductorJson.isJsonNull()) {
            request.setAccion(jsonObject.get(DATOS_ACCION).getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(conductorJson.get(String.valueOf(IDCONDUCTOR)).getAsString());
                    request.setDatos(conductorJson.get(FECHA_CARNET).getAsString());
                    request.setDatos(conductorJson.get(FECHA_CADUCIDAD).getAsString());
                    request.setDatos(conductorJson.get(String.valueOf(EMPLEADOID)).getAsString());

                    SqlConductor sql = new SqlConductor();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarConductor(request.getDatos());
                    respostaCon = rs;
                    return respostaCon;

                }
                case ACTUALIZAR: {
                    request.setString1(conductorJson.get(String.valueOf(IDCONDUCTOR)).getAsString());

                    SqlConductor sql = new SqlConductor();
                    String rs = sql.ActualizarConductor(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCon = rs;
                        return respostaCon;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONCONDUCTOR);
                        return respostaCon = gson.toJson(error);
                    }
                }
                case ELIMINAR: {
                    request.setString1(conductorJson.get(String.valueOf(IDCONDUCTOR)).getAsString());

                    SqlConductor sql = new SqlConductor();
                    String rs = sql.EliminarConductor(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCon = rs;
                        return respostaCon;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONCONDUCTOR);
                        return respostaCon = gson.toJson(error);
                    }
                }
                case LISTAR: {

                    SqlConductor sql = new SqlConductor();
                    String rs = sql.ListarConductor(CONDUCTOR);
                    if (!rs.isEmpty()) {
                        respostaCon = rs;
                        return respostaCon;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONCONDUCTOR);
                        return respostaCon = gson.toJson(error);
                    }
                }

                case MODIFICAR: {

                    request.setString1(conductorJson.get(String.valueOf(IDCONDUCTOR)).getAsString());
                    if (conductorJson.get(FECHA_CADUCIDAD) != null){
                    request.setString2(conductorJson.get(FECHA_CADUCIDAD).getAsString());
                    request.setString3(FECHA_CADUCIDAD);
                    }else if ((conductorJson.get(EMPLEADOID) != null)){
                        request.setString2(conductorJson.get(EMPLEADOID).getAsString());
                        request.setString3(EMPLEADOID);
                    }

                    SqlConductor sql = new SqlConductor();
                    String rs = sql.ModificarConductor(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaCon = rs;
                        return respostaCon;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONCONDUCTOR);
                        return respostaCon = gson.toJson(error);
                    }
                }
                case LISTARID: {
                    request.setString1(conductorJson.get(String.valueOf(IDCONDUCTOR)).getAsString());

                    SqlConductor sql = new SqlConductor();
                    String rs = sql.ListarConductor(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCon = rs;
                        return respostaCon;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONCONDUCTOR);
                        return respostaCon = gson.toJson(error);
                    }
                }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONCONDUCTOR);
        return respostaCon = gson.toJson(error);
    }

}
