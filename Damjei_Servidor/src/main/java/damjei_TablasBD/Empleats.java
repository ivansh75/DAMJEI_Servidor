/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_TablasBD;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.ConexionBD;
import damjei_BD.SqlUsuario;
import damjei_ConexionCliente.ClientRequest;
import java.sql.Connection;

/**
 *
 * @author Ivimar
 */
public class Empleats {
    private String rsConsulta;

    public Empleats() {
    }

    public String Empleats(String json) {
        Gson gson = new Gson();

        ConexionBD cn = new ConexionBD();
        Connection con = cn.establecerConexion();
        JsonObject jsonObject = new JsonObject();
        ClientRequest request = gson.fromJson(json, ClientRequest.class);
        String nom = gson.toJson(request.getConsulta(0, ClientRequest.class));
        String contraseya = gson.toJson(request.getConsulta(1, ClientRequest.class));
        SqlUsuario sql = new SqlUsuario();
        rsConsulta = sql.comprobarUsuario(nom, contraseya);
        System.out.println("qsdsañgjfdfmsbdgdnb," + rsConsulta);
        return rsConsulta;

    }
}
