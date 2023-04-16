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
        JsonObject revisionJson = jsonObject.getAsJsonObject("revisiones");
        if (revisionJson != null && !revisionJson.isJsonNull()) {
            request.setAccion(jsonObject.get("accio").getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(revisionJson.get(String.valueOf("idrevision")).getAsString());
                    request.setDatos(revisionJson.get("fecha_revision").getAsString());
                    request.setDatos(revisionJson.get(String.valueOf("kilometros_revision")).getAsString());
                    request.setDatos(revisionJson.get(String.valueOf("vehiculoid")).getAsString());
                    request.setDatos(revisionJson.get(String.valueOf("mantenimientoid")).getAsString());
                    request.setDatos(revisionJson.get(String.valueOf("estado_revision")).getAsString());

                    SqlRevisiones sql = new SqlRevisiones();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarRevisiones(request.getDatos());
                    respostaRep = rs;
                    return respostaRep;

                }
                case ACTUALIZAR: {
                    request.setString1(revisionJson.get("idrevision").getAsString());

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
                    request.setString1(revisionJson.get("idrevision").getAsString());

                    SqlRevisiones sql = new SqlRevisiones();
                    String rs = sql.EliminarRepostar(request.getString1());
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
                    String rs = sql.ListarRepostar(REVISIONES);
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREVISIONES);
                        return respostaRep = gson.toJson(error);
                    }
                }

                case MODIFICAR:

                    request.setString1(revisionJson.get("idrevision").getAsString());

                    if (revisionJson.get("fecha_revision") != null) {
                        request.setString2(revisionJson.get("fecha_revision").getAsString());
                        request.setString3("fecha_revision");
                    } else if (revisionJson.get(String.valueOf("kilometros_revision")) != null) {
                        request.setString2(revisionJson.get(String.valueOf("kilometros_revision")).getAsString());
                        request.setString3("kilometros_revision");
                    } else if (revisionJson.get(String.valueOf("vehiculoid")) != null && revisionJson.get("vehiculoid").getAsInt() != 0) {
                        request.setString2(revisionJson.get(String.valueOf("vehiculoid")).getAsString());
                        request.setString3("vehiculoid");
                    } else if (revisionJson.get(String.valueOf("mantenimientoid")) != null && revisionJson.get("conductorid").getAsInt() != 0) {
                        request.setString2(revisionJson.get(String.valueOf("mantenimientoid")).getAsString());
                        request.setString3("mantenimientoid");
                    } else if (revisionJson.get(String.valueOf("estado_revision")) != null) {
                        request.setString2(revisionJson.get(String.valueOf("estado_revision")).getAsString());
                        request.setString3("estado_revision");
                    }

                    SqlRevisiones sql = new SqlRevisiones();
                    String rs = sql.ModificarRepostar(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREVISIONES);
                        return respostaRep = gson.toJson(error);
                    }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONREVISIONES);
        return respostaRep = gson.toJson(error);
    }

}

