/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlMantenimiento;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ivimar
 */
public class GestionMantenimiento {
    
    public static final String MANTENIMIENTO = "mantenimiento";
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;

    public static final String ERROR_GESTIONMANTENIMIENTO = "Error en la gestion de Mantenimiento";
  
    String respostaMt;
    boolean conexio = false;

    public GestionMantenimiento() {
    }
    
    
    public String GestionMantenimiento(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Vehicle del JSON 
        JsonObject mantenimientoJson = jsonObject.getAsJsonObject("mantenimiento");
        if (mantenimientoJson != null && !mantenimientoJson.isJsonNull()) {
            request.setAccion(jsonObject.get("accio").getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(mantenimientoJson.get(String.valueOf("idmantenimiento")).getAsString());
                    request.setDatos(mantenimientoJson.get("nombre").getAsString());

                    SqlMantenimiento sql = new SqlMantenimiento();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarMantenimiento(request.getDatos());
                    respostaMt = rs;
                    return respostaMt;

                }
                case ACTUALIZAR: {
                    request.setString1(mantenimientoJson.get("nombre").getAsString());

                    SqlMantenimiento sql = new SqlMantenimiento();
                    String rs = sql.ActualizarVehiculo(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaMt = rs;
                        return respostaMt;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONMANTENIMIENTO);
                        return respostaMt = gson.toJson(error);
                    }
                }
                case ELIMINAR: {
                    request.setString1(mantenimientoJson.get("nombre").getAsString());

                    SqlMantenimiento sql = new SqlMantenimiento();
                    String rs = sql.EliminarVehiculo(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaMt = rs;
                        return respostaMt;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONMANTENIMIENTO);
                        return respostaMt = gson.toJson(error);
                    }
                }
                case LISTAR: {

                    SqlMantenimiento sql = new SqlMantenimiento();
                    String rs = sql.ListarVehiculo(MANTENIMIENTO);
                    if (!rs.isEmpty()) {
                        respostaMt = rs;
                        return respostaMt;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONMANTENIMIENTO);
                        return respostaMt = gson.toJson(error);
                    }
                }

                case MODIFICAR:{

                    request.setString1(mantenimientoJson.get("nombre").getAsString());
                    request.setString2(mantenimientoJson.get(String.valueOf("kilometros_mantenimiento")).getAsString());
                    request.setString3(mantenimientoJson.get("fecha_mantenimiento").getAsString());

                    SqlMantenimiento sql = new SqlMantenimiento();
                    String rs = sql.ModificarVehiculo(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaMt = rs;
                        return respostaMt;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONMANTENIMIENTO);
                        return respostaMt = gson.toJson(error);
                    }
                }
                 case LISTARID: {                     
                    request.setString1(mantenimientoJson.get(String.valueOf("idmantenimiento")).getAsString());
                    
                    SqlMantenimiento sql = new SqlMantenimiento();
                    String rs = sql.ListarVehiculoId(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaMt = rs;
                        return respostaMt;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONMANTENIMIENTO);
                        return respostaMt = gson.toJson(error);
                    }
                }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONMANTENIMIENTO);
        return respostaMt = gson.toJson(error);
    }
    
    
    
    
    
}
