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
import static damjei_GestionClases.GestionEmpleados.DNI;
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
                case ELIMINAR: {

                    if (conductorJson.get(IDCONDUCTOR) != null) {
                        request.setString1(conductorJson.get(String.valueOf(IDCONDUCTOR)).getAsString());
                    } else {
                        request.setString1(conductorJson.get(String.valueOf(DNI)).getAsString());
                    }
                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.EliminarUsuario(request.getString1());
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
                    if (conductorJson.get(FECHA_CADUCIDAD) != null) {
                        request.setString2(conductorJson.get(FECHA_CADUCIDAD).getAsString());
                        request.setString3(FECHA_CADUCIDAD);
                    } else if ((conductorJson.get(EMPLEADOID) != null)) {
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
