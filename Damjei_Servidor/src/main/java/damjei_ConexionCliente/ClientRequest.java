/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

/*import com.fasterxml.jackson.databind.JsonNode;*/
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ivimar
 */
public class ClientRequest {

    private final Map<String, Object> hasMap = new HashMap<>();

    private ArrayList<String> datos = new ArrayList<>();

    private String String1;
    private String String2;
    private int id_String;
    private int accion;
    private String clase;

    public ClientRequest(String resposta) {
    }

    public ClientRequest(String String1, String String2, int id_String, int accio, String clase) {

        this.String1 = String1;
        this.String2 = String2;
        this.id_String = id_String;
        this.accion = accio;
        this.clase = clase;
    }

    public String ClientRequest(String resposta) {
        Gson gson = new Gson();
        return gson.toJson(resposta);
       
    }

    public String getString1() {
        return String1;
    }

    public void setString1(String String1) {
        this.String1 = String1;
    }

    public String getString2() {
        return String2;
    }

    public void setString2(String String2) {
        this.String2 = String2;
    }

    public int getId_String() {
        return id_String;
    }

    public void setId_String(int id_String) {
        this.id_String = id_String;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public ArrayList<String> getDatos() {
        return datos;
    }

    public void setDatos(JsonObject datos) {
        this.datos.add(datos + "");
    }

}
