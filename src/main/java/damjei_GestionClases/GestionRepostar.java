/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlRepostar;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ivimar
 */
public class GestionRepostar {

    public static final String REPOSTAR = "repostar";
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;

    public static final String ERROR_GESTIONREPOSTAR = "Error en la gestion de Repostar";

    String respostaRep;
    boolean conexio = false;

    public GestionRepostar() {
    }

    public String GestionRepostar(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Vehicle del JSON 
        JsonObject repostarJson = jsonObject.getAsJsonObject("repostar");
        if (repostarJson != null && !repostarJson.isJsonNull()) {
            request.setAccion(jsonObject.get("accio").getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(repostarJson.get(String.valueOf("idrepostar")).getAsString());
                    request.setDatos(repostarJson.get("fecha_repostar").getAsString());
                    request.setDatos(repostarJson.get(String.valueOf("importe_repostar")).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf("kilometros_repostar")).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf("combustibleid")).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf("vehiculoid")).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf("conductorid")).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf("litros")).getAsString());

                    SqlRepostar sql = new SqlRepostar();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarRepostar(request.getDatos());
                    respostaRep = rs;
                    return respostaRep;

                }
                case ACTUALIZAR: {
                    request.setString1(repostarJson.get(String.valueOf("idrepostar")).getAsString());

                    SqlRepostar sql = new SqlRepostar();
                    String rs = sql.ActualizarRepostar(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONREPOSTAR);
                        return respostaRep = gson.toJson(error);
                    }
                }
                case ELIMINAR: {
                    request.setString1(repostarJson.get(String.valueOf("idrepostar")).getAsString());

                    SqlRepostar sql = new SqlRepostar();
                    String rs = sql.EliminarRepostar(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONREPOSTAR);
                        return respostaRep = gson.toJson(error);
                    }
                }
                case LISTAR: {

                    SqlRepostar sql = new SqlRepostar();
                    String rs = sql.ListarRepostar(REPOSTAR);
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREPOSTAR);
                        return respostaRep = gson.toJson(error);
                    }
                }

                case MODIFICAR:{

                    request.setString1(repostarJson.get(String.valueOf("idrepostar")).getAsString());

                    if (repostarJson.get("fecha_repostar") != null) {
                        request.setString2(repostarJson.get("fecha_repostar").getAsString());
                        request.setString3("fecha_repostar");
                    } else if (repostarJson.get(String.valueOf("importe_repostar")) != null) {
                        request.setString2(repostarJson.get("importe_repostar").getAsString());
                        request.setString3("importe_repostar");
                    } else if (repostarJson.get(String.valueOf("kilometros_repostar")) != null) {
                        request.setString2(repostarJson.get(String.valueOf("kilometros_repostar")).getAsString());
                        request.setString3("kilometros_repostar");
                    } else if (repostarJson.get(String.valueOf("combustibleid")) != null && repostarJson.get("combustibleid").getAsInt() != 0) {
                        request.setString2(repostarJson.get(String.valueOf("combustibleid")).getAsString());
                        request.setString3("combustibleid");
                    } else if (repostarJson.get(String.valueOf("vehiculoid")) != null && repostarJson.get("vehiculoid").getAsInt() != 0) {
                        request.setString2(repostarJson.get(String.valueOf("vehiculoid")).getAsString());
                        request.setString3("vehiculoid");
                    } else if (repostarJson.get(String.valueOf("conductorid")) != null && repostarJson.get("conductorid").getAsInt() != 0) {
                        request.setString2(repostarJson.get(String.valueOf("conductorid")).getAsString());
                        request.setString3("conductorid");
                    } else if (repostarJson.get(String.valueOf("litros")) != null) {
                        request.setString2(repostarJson.get(String.valueOf("litros")).getAsString());
                        request.setString3("litros");
                    }

                    SqlRepostar sql = new SqlRepostar();
                    String rs = sql.ModificarRepostar(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREPOSTAR);
                        return respostaRep = gson.toJson(error);
                    }
                }
                case LISTARID: {
                     request.setString1(repostarJson.get(String.valueOf("idrepostar")).getAsString());
                     
                    SqlRepostar sql = new SqlRepostar();
                    String rs = sql.ListarRepostarId(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaRep = rs;
                        return respostaRep;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONREPOSTAR);
                        return respostaRep = gson.toJson(error);
                    }
                }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONREPOSTAR);
        return respostaRep = gson.toJson(error);
    }

}
