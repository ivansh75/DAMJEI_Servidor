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
    //constantes de los titulos atributos
    public static final String IDREPOSTAR = "idrepostar";
    public static final String FECHA_REPOSTAR = "fecha_repostar";
    public static final String IMPORTE_REPOSTAR = "importe_repostar";
    public static final String KILOMETROS_REPOSTAR = "kilometros_repostar";
    public static final String COMBUSTIBLEID = "combustibleid";
    public static final String VEHICULOID = "vehiculoid";
    public static final String CONDUCTORID = "conductorid";
    public static final String LITROS = "litros";
    public static final String DATOS_REPOSTAR = "repostar";
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
        JsonObject repostarJson = jsonObject.getAsJsonObject(REPOSTAR);
        if (repostarJson != null && !repostarJson.isJsonNull()) {
            request.setAccion(jsonObject.get(DATOS_ACCION).getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(repostarJson.get(String.valueOf(IDREPOSTAR)).getAsString());
                    request.setDatos(repostarJson.get(FECHA_REPOSTAR).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf(IMPORTE_REPOSTAR)).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf(KILOMETROS_REPOSTAR)).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf(COMBUSTIBLEID)).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf(VEHICULOID)).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf(CONDUCTORID)).getAsString());
                    request.setDatos(repostarJson.get(String.valueOf(LITROS)).getAsString());

                    SqlRepostar sql = new SqlRepostar();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarRepostar(request.getDatos());
                    respostaRep = rs;
                    return respostaRep;

                }
                case ACTUALIZAR: {
                    request.setString1(repostarJson.get(String.valueOf(IDREPOSTAR)).getAsString());

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
                    request.setString1(repostarJson.get(String.valueOf(IDREPOSTAR)).getAsString());

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

                case MODIFICAR: {

                    request.setString1(repostarJson.get(String.valueOf(IDREPOSTAR)).getAsString());

                    if (repostarJson.get(FECHA_REPOSTAR) != null) {
                        request.setString2(repostarJson.get(FECHA_REPOSTAR).getAsString());
                        request.setString3(FECHA_REPOSTAR);
                    } else if (repostarJson.get(String.valueOf(IMPORTE_REPOSTAR)) != null) {
                        request.setString2(repostarJson.get(IMPORTE_REPOSTAR).getAsString());
                        request.setString3(IMPORTE_REPOSTAR);
                    } else if (repostarJson.get(String.valueOf(KILOMETROS_REPOSTAR)) != null) {
                        request.setString2(repostarJson.get(String.valueOf(KILOMETROS_REPOSTAR)).getAsString());
                        request.setString3(KILOMETROS_REPOSTAR);
                    } else if (repostarJson.get(String.valueOf(COMBUSTIBLEID)) != null && repostarJson.get(COMBUSTIBLEID).getAsInt() != 0) {
                        request.setString2(repostarJson.get(String.valueOf(COMBUSTIBLEID)).getAsString());
                        request.setString3(COMBUSTIBLEID);
                    } else if (repostarJson.get(String.valueOf(VEHICULOID)) != null && repostarJson.get(VEHICULOID).getAsInt() != 0) {
                        request.setString2(repostarJson.get(String.valueOf(VEHICULOID)).getAsString());
                        request.setString3(VEHICULOID);
                    } else if (repostarJson.get(String.valueOf(CONDUCTORID)) != null && repostarJson.get(CONDUCTORID).getAsInt() != 0) {
                        request.setString2(repostarJson.get(String.valueOf(CONDUCTORID)).getAsString());
                        request.setString3(CONDUCTORID);
                    } else if (repostarJson.get(String.valueOf(LITROS)) != null) {
                        request.setString2(repostarJson.get(String.valueOf(LITROS)).getAsString());
                        request.setString3(LITROS);
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
                    request.setString1(repostarJson.get(String.valueOf(IDREPOSTAR)).getAsString());

                    SqlRepostar sql = new SqlRepostar();
                    String rs = sql.ListarRepostar(request.getString1());
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
