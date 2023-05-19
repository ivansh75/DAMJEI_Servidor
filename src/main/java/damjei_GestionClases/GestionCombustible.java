/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlCombustible;
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
public class GestionCombustible {

    public static final String COMBUSTIBLE = "combustible";
    //constantes de los titulos atributos
    public static final String IDCOMBUSTIBLE = "idcombustible";
    public static final String NOMBRE = "nombre";
    public static final String PRECIO = "precio";
    public static final String DATOS_COMBUSTIBLE = "combustible";
    public static final String DATOS_CLASE = "clase";
    public static final String DATOS_ACCION = "accio";
    public static final String DATOS_TOKEN = "token";

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
        JsonObject combustibleJson = jsonObject.getAsJsonObject(COMBUSTIBLE);
        if (combustibleJson != null && !combustibleJson.isJsonNull()) {
            request.setAccion(jsonObject.get(DATOS_ACCION).getAsInt());

            switch (request.getAccion()) {
                case INSERTAR: {
                    //ArrayList<String> datos = new ArrayList<>();
                    request.setDatos(combustibleJson.get(String.valueOf(IDCOMBUSTIBLE)).getAsString());
                    request.setDatos(combustibleJson.get(NOMBRE).getAsString());
                    request.setDatos(combustibleJson.get(String.valueOf(PRECIO)).getAsString());

                    SqlCombustible sql = new SqlCombustible();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarCombustible(request.getDatos());
                    respostaCom = rs;
                    return respostaCom;

                }
                case ELIMINAR: {
                    request.setString1(combustibleJson.get(NOMBRE).getAsString());

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

                case MODIFICAR: {

                    request.setString1(combustibleJson.get(NOMBRE).getAsString());

                    request.setString2(combustibleJson.get(String.valueOf(PRECIO)).getAsString());
                    request.setString3(PRECIO);

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
                    request.setString1(combustibleJson.get(String.valueOf(IDCOMBUSTIBLE)).getAsString());

                    SqlCombustible sql = new SqlCombustible();
                    String rs = sql.ListarCombustible(request.getString1());
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
