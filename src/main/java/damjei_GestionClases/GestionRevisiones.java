/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlRevisiones;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ivimar
 */
public class GestionRevisiones {

    public static final String REVISIONES = "revisiones";
    //constantes de los titulos atributos
    public static final String IDREVISION = "idrevision";
    public static final String FECHA_REVISION = "fecha_revision";
    public static final String KILOMETROS_REVISION = "kilometros_revision";
    public static final String VEHICULOID = "vehiculoid";
    public static final String MANTENIMIENTOID = "mantenimientoid";
    public static final String ESTADO_REVISION = "estado_revision";
    public static final String DATOS_REVISION = "revision";
    public static final String DATOS_CLASE = "clase";
    public static final String DATOS_ACCION = "accio";
    public static final String DATOS_TOKEN = "token";
    //constantes para la acciones
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;
    
    
    
    public static final String ERROR_GESTIONREVISIONES = "Error en la gestion de Revisiones";

    String respostaRep;
    boolean conexio = false;

    public GestionRevisiones() {
    }

    public String GestionRevisiones(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Vehicle del JSON 
        JsonObject revisionJson = jsonObject.getAsJsonObject(DATOS_REVISION);
        if (revisionJson != null && !revisionJson.isJsonNull()) {
            request.setAccion(jsonObject.get(DATOS_ACCION).getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(revisionJson.get(String.valueOf(IDREVISION)).getAsString());
                    request.setDatos(revisionJson.get(FECHA_REVISION).getAsString());
                    request.setDatos(revisionJson.get(String.valueOf(KILOMETROS_REVISION)).getAsString());
                    request.setDatos(revisionJson.get(String.valueOf(VEHICULOID)).getAsString());
                    request.setDatos(revisionJson.get(String.valueOf(MANTENIMIENTOID)).getAsString());
                    request.setDatos(revisionJson.get(String.valueOf(ESTADO_REVISION)).getAsString());

                    SqlRevisiones sql = new SqlRevisiones();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarRevisiones(request.getDatos());
                    respostaRep = rs;
                    return respostaRep;

                }
                case ACTUALIZAR: {
                    request.setString1(revisionJson.get(IDREVISION).getAsString());

                    SqlRevisiones sql = new SqlRevisiones();
                    String rs = sql.ActualizarRevisiones(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONREVISIONES);
                        return respostaRep = gson.toJson(error);
                    }
                }
                case ELIMINAR: {
                    request.setString1(revisionJson.get(IDREVISION).getAsString());

                    SqlRevisiones sql = new SqlRevisiones();
                    String rs = sql.EliminarRevisiones(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONREVISIONES);
                        return respostaRep = gson.toJson(error);
                    }
                }
                case LISTAR: {

                    SqlRevisiones sql = new SqlRevisiones();
                    String rs = sql.ListarRevisiones(REVISIONES);
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREVISIONES);
                        return respostaRep = gson.toJson(error);
                    }
                }

                case MODIFICAR: {

                    request.setString1(revisionJson.get(String.valueOf(IDREVISION)).getAsString());

                    if (revisionJson.get(FECHA_REVISION) != null) {
                        request.setString2(revisionJson.get(FECHA_REVISION).getAsString());
                        request.setString3(FECHA_REVISION);
                    } else if (revisionJson.get(String.valueOf(KILOMETROS_REVISION)) != null) {
                        request.setString2(revisionJson.get(String.valueOf(KILOMETROS_REVISION)).getAsString());
                        request.setString3(KILOMETROS_REVISION);
                    } else if (revisionJson.get(String.valueOf(VEHICULOID)) != null && revisionJson.get(VEHICULOID).getAsInt() != 0) {
                        request.setString2(revisionJson.get(String.valueOf(VEHICULOID)).getAsString());
                        request.setString3(VEHICULOID);
                    } else if (revisionJson.get(String.valueOf(MANTENIMIENTOID)) != null && revisionJson.get(MANTENIMIENTOID).getAsInt() != 0) {
                        request.setString2(revisionJson.get(String.valueOf(MANTENIMIENTOID)).getAsString());
                        request.setString3(MANTENIMIENTOID);
                    } else if (revisionJson.get(String.valueOf(ESTADO_REVISION)) != null) {
                        request.setString2(revisionJson.get(String.valueOf(ESTADO_REVISION)).getAsString());
                        request.setString3(ESTADO_REVISION);
                    }

                    SqlRevisiones sql = new SqlRevisiones();
                    String rs = sql.ModificarRevisiones(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREVISIONES);
                        return respostaRep = gson.toJson(error);
                    }
                }
                case LISTARID: {
                    request.setString1(revisionJson.get(String.valueOf(IDREVISION)).getAsString());

                    SqlRevisiones sql = new SqlRevisiones();
                    String rs = sql.ListarRevisiones(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREVISIONES);
                        return respostaRep = gson.toJson(error);
                    }
                }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONREVISIONES);
        return respostaRep = gson.toJson(error);
    }

}
