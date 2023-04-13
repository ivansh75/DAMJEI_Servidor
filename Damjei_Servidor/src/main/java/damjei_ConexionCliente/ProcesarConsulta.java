/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_GestionClases.GestionCombustible;
import damjei_GestionClases.GestionConductor;
import damjei_GestionClases.GestionEmpleados;
import static damjei_GestionClases.GestionEmpleados.LOGIN;
import damjei_GestionClases.GestionMantenimiento;
import damjei_GestionClases.GestionRepostar;
import damjei_GestionClases.GestionRevisiones;
import damjei_GestionClases.GestionVehiculo;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ivimar
 */
public class ProcesarConsulta {

    public static final String EMPRESA = "Empresa.class";
    public static final String EMPLEADOS = "Empleats.class";
    public static final String CONDUCTOR = "Conductor.class";
    public static final String VEHICULOS = "Vehicle.class";
    public static final String REVISIONES = "Revisions.class";
    public static final String MANTENIMIENTO = "Mantenimet.class";
    public static final String REPOSTAR = "Repostar.class";
    public static final String GASOLINA = "Benzina.class";

    public static final String ERROR_PROCESAR_CONSULTA = "Error de sintexis al procesar consulta";
    public static final String ERROR_REGISTRO_USUARIO = "Error el cliente o Usuario NO registrado";
    public static final String ERROR_REGISTRO_CADUCADO = "Error el cliente o Usuario NO registrado";

    Connection conn;
    boolean conexio = false;
    boolean isLogin = false;
    String resposta = "";

    /**
     * Constructor simple
     */
    public ProcesarConsulta() {
    }

    public String ConsultaCliente(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener la información de la clase
        JsonElement claseJson = jsonObject.get("clase");
        if (claseJson != null && !claseJson.isJsonNull()) {
            String claseNombre = claseJson.getAsString();
            //Eliminamos comillas del String
            claseNombre = claseNombre.replaceAll("\'\'", "\'");
            request.setAccion(jsonObject.get("accio").getAsInt());
            if (jsonObject.has("token")) {
                request.setToken(jsonObject.get("token").getAsString());
            }
            
            //si no tenemos token no estamos registrados y debera ir si o si a gestionEmleados
            if (request.getToken() == null && request.getAccion() != LOGIN) {
                isLogin = false;
                String error = ("correcta:" + conexio + " error: " + ERROR_REGISTRO_USUARIO);
                System.out.println("--------ERROR_REGISTRO_USUARIO-----------" + ERROR_REGISTRO_USUARIO);
                return resposta = gson.toJson(error);
                //si tenemos token y la accion es diferente a Login debemos validar el token
            } else if (request.getToken() != null && request.getAccion() != LOGIN) {
                int numToken = TokenManager.validateToken(request.getToken());//validamos si es un token registrado
                //////////////Comprovamos y aseguramos que las acciones que se vayan hacer esten con token y validado 
                /////sino debera hacer un Login; token validado !=-1 y accion diferente a login //////////////////////////  
                if (numToken == -1 && request.getAccion() != LOGIN) {
                    isLogin = false;
                    String error = ("correcta:" + conexio + " error: " + ERROR_REGISTRO_CADUCADO);
                    System.out.println("-------ERROR_REGISTRO_CADUCADO------------" + ERROR_REGISTRO_CADUCADO);
                    return resposta = gson.toJson(error);
                } else {
                    isLogin = true;
                }
            } else if (request.getToken() == null && request.getAccion() == LOGIN) {
                isLogin = true;
            }

            if (isLogin) {

                if (claseNombre.equalsIgnoreCase(EMPLEADOS)) {
                    GestionEmpleados obem = new GestionEmpleados();
                    return resposta = obem.GestionEmpleados(json);
                } else if (claseNombre.equalsIgnoreCase(CONDUCTOR)) {
                    GestionConductor obco = new GestionConductor();
                     
                } else if (claseNombre.equalsIgnoreCase(VEHICULOS)) {
                    GestionVehiculo obve = new GestionVehiculo();
                    return resposta = obve.GestionVehiculo(json);
                } else if (claseNombre.equalsIgnoreCase(REVISIONES)) {
                    GestionRevisiones obrev = new GestionRevisiones();

                } else if (claseNombre.equalsIgnoreCase(MANTENIMIENTO)) {
                    GestionMantenimiento obman = new GestionMantenimiento();

                } else if (claseNombre.equalsIgnoreCase(REPOSTAR)) {
                    GestionRepostar obrp = new GestionRepostar();

                } else if (claseNombre.equalsIgnoreCase(GASOLINA)) {
                    GestionCombustible obbz = new GestionCombustible();

                }
            }
        }
        String error = ("correcta: " + conexio + " error: " + ERROR_PROCESAR_CONSULTA);
        return resposta = gson.toJson(error);
    }

    public void close() throws SQLException {
        conn.close();
    }

}
