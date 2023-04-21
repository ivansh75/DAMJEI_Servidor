/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlCombustible;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ivimar
 */
public class GestionCombustible {

    public static final String COMBUSTIBLE = "combustible";
    public static final int INSERTAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;

    public static final String ERROR_GESTIONCOMBUSTIBLE = "Error en la gestion de Combustibles";

    String respostaCom;
    boolean conexio = false;

    public GestionCombustible() {
    }

    public String GestionCombustible(String json) throws SQLException, ParseException {
        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Combustible del JSON 
        JsonObject combustibleJson = jsonObject.getAsJsonObject("combustible");
        if (combustibleJson != null && !combustibleJson.isJsonNull()) {
            request.setAccion(jsonObject.get("accio").getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(combustibleJson.get(String.valueOf("idcombustible")).getAsString());
                    request.setDatos(combustibleJson.get("nombre").getAsString());
                    request.setDatos(combustibleJson.get(String.valueOf("precio")).getAsString());

                    SqlCombustible sql = new SqlCombustible();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarCombustible(request.getDatos());
                    respostaCom = rs;
                    return respostaCom;

                }
                case ACTUALIZAR: {
                    request.setString1(combustibleJson.get("nombre").getAsString());

                    SqlCombustible sql = new SqlCombustible();
                    String rs = sql.ActualizarCombustible(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCom = rs;
                        return respostaCom;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONCOMBUSTIBLE);
                        return respostaCom = gson.toJson(error);
                    }
                }
                case ELIMINAR: {
                    request.setString1(combustibleJson.get("nombre").getAsString());

                    SqlCombustible sql = new SqlCombustible();
                    String rs = sql.EliminarCombustible(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCom = rs;
                        return respostaCom;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONCOMBUSTIBLE);
                        return respostaCom = gson.toJson(error);
                    }
                }
                case LISTAR: {

                    SqlCombustible sql = new SqlCombustible();
                    String rs = sql.ListarCombustible(COMBUSTIBLE);
                    if (!rs.isEmpty()) {
                        respostaCom = rs;
                        return respostaCom;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONCOMBUSTIBLE);
                        return respostaCom = gson.toJson(error);
                    }
                }

                case MODIFICAR:{

                    request.setString1(combustibleJson.get("nombre").getAsString());

                    request.setString2(combustibleJson.get(String.valueOf("precio")).getAsString());
                    request.setString3("precio");

                    SqlCombustible sql = new SqlCombustible();
                    String rs = sql.ModificarCombustible(request.getString1(), request.getString2(), request.getString3());
                    if (!rs.isEmpty()) {
                        respostaCom = rs;
                        return respostaCom;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONCOMBUSTIBLE);
                        return respostaCom = gson.toJson(error);
                    }
            }
                case LISTARID: {
                    request.setString1(combustibleJson.get(String.valueOf("idcombustible")).getAsString());
                    
                    SqlCombustible sql = new SqlCombustible();
                    String rs = sql.ListarCombustibleId(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCom = rs;
                        return respostaCom;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONCOMBUSTIBLE);
                        return respostaCom = gson.toJson(error);
                    }
                }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONCOMBUSTIBLE);
        return respostaCom = gson.toJson(error);

    }

}
