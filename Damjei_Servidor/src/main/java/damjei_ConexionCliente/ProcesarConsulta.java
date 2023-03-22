/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.ConexionBD;
import damjei_ConexionCliente.ClientRequest;
import damjei_TablasBD.Empleats;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ivimar
 */
public class ProcesarConsulta {

    public static final String EMPRESA = "\"Empresa.class";
    public static final String EMPLEADOS = "\"Empleat.class\"";
    public static final String CONDUCTOR = "Conductor.class";
    public static final String VEHICULO = "Vehiculo.class";
    public static final String REVISIONES = "Revisions.class";
    public static final String MANTENIMENT = "Mantenimet.class";
    public static final String REPOSTAR = "Repòstar.class";
    public static final String BENZINA = "Benzina.class";

    private Connection conn;

    public ProcesarConsulta() {
    }

    public String ConsultaCliente(String json) {
        Gson gson = new Gson();
        String resposta = null;
        // Obtener Objecte
        JsonObject jsonObject = new JsonObject();
        ClientRequest request = gson.fromJson(json, ClientRequest.class);
        String tabla = gson.toJson(request.getClase());
        System.out.println("qsdsañgjfdfmsbdgdnb," + tabla);

        // Realizar la consulta a la base de datos
        String sql = "";
        if (tabla.equalsIgnoreCase(EMPRESA)) {
            Empleats empleats = new Empleats();
            resposta= empleats.Empleats(json);            
        } else if (tabla.equalsIgnoreCase(EMPLEADOS)) {
            sql = "SELECT * FROM empleados";
        } else if (tabla.equalsIgnoreCase(CONDUCTOR)) {
            sql = "SELECT * FROM conductor";
        } else if (tabla.equalsIgnoreCase(VEHICULO)) {
            sql = "SELECT * FROM vehiculo";
        } else if (tabla.equalsIgnoreCase(REVISIONES)) {
            sql = "SELECT * FROM revisiones";
        } else if (tabla.equalsIgnoreCase(MANTENIMENT)) {
            sql = "SELECT * FROM mantenimiento";
        } else if (tabla.equalsIgnoreCase(REPOSTAR)) {
            sql = "SELECT * FROM repostar";
        } else if (tabla.equalsIgnoreCase(BENZINA)) {
            sql = "SELECT * FROM gasolina";
        } else {
            System.out.println("no se ha encontrado nada");
        }
        return resposta;
        
    }

    public void close() throws SQLException {
        conn.close();
    }

}
