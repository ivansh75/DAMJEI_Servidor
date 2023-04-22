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
        JsonObject conductorJson = jsonObject.getAsJsonObject("conductor");
        if (conductorJson != null && !conductorJson.isJsonNull()) {
            request.setAccion(jsonObject.get("accio").getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(conductorJson.get(String.valueOf("idconductor")).getAsString());
                    request.setDatos(conductorJson.get("fecha_carnet").getAsString());
                    request.setDatos(conductorJson.get("fecha_caducidad_carnet").getAsString());
                    request.setDatos(conductorJson.get(String.valueOf("empleadoid")).getAsString());

                    SqlConductor sql = new SqlConductor();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarConductor(request.getDatos());
                    respostaCon = rs;
                    return respostaCon;

                }
                case ACTUALIZAR: {
                    request.setString1(conductorJson.get(String.valueOf("idconductor")).getAsString());

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
                    request.setString1(conductorJson.get(String.valueOf("idconductor")).getAsString());

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

                    request.setString1(conductorJson.get(String.valueOf("idconductor")).getAsString());
                    if (conductorJson.get("fecha_caducidad_carnet") != null){
                    request.setString2(conductorJson.get("fecha_caducidad_carnet").getAsString());
                    request.setString3("fecha_caducidad_carnet");
                    }else if ((conductorJson.get("empleadoid") != null)){
                        request.setString2(conductorJson.get("empleadoid").getAsString());
                        request.setString3("empleadoid");
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
                    request.setString1(conductorJson.get(String.valueOf("idconductor")).getAsString());

                    SqlConductor sql = new SqlConductor();
                    String rs = sql.ListarConductorId(request.getString1());
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
