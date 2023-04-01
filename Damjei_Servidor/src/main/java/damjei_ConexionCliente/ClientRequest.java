/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Ivimar
 */
public class ClientRequest {

    private ArrayList<String> datos = new ArrayList<>();
    private Map<String, Object> data;

    private String String1;
    private String String2;
    private int id_String;
    private int accion;
    private String clase;
    private String token;

    /**
     * Constructor buit
     */
    public ClientRequest() {
    }

    /**
     * Constructor per obtenir objecte
     *
     * @param String1
     * @param String2
     * @param id_String
     * @param accio
     * @param clase
     */
    public ClientRequest(String String1, String String2, int id_String, int accio, String clase) {

        this.String1 = String1;
        this.String2 = String2;
        this.id_String = id_String;
        this.accion = accio;
        this.clase = clase;
    }

    /**
     * Conte el primer valor de la consulta del Objecte es un JsonElement del
     * Objete
     *
     * @return el primer valor de la consulta
     */
    public String getString1() {
        return String1;
    }

    /**
     * Obtenim el primer valor que volem obtenir del Objecte es un JsonElement
     * del Objete
     *
     * @param String1
     */
    public void setString1(String String1) {
        this.String1 = String1;
    }

    /**
     * Conte el segon valor de la consulta del Objecte es un JsonElement del
     * Objete
     *
     * @return String2
     */
    public String getString2() {
        return String2;
    }

    /**
     * Obtenim el segon valor que volem obtenir del Objecte es un JsonElement
     * del Objete
     *
     * @param String2
     */
    public void setString2(String String2) {
        this.String2 = String2;
    }

    /**
     * Conte el tercer valor de la consulta del Objecte, potser el id_empresa es
     * un JsonElement del Objete
     *
     * @return id_String
     */
    public int getId_String() {
        return id_String;
    }

    /**
     * Obtenim el tercer valor que volem obtenir del Objecte, potser el
     * id_empresa es un JsonElement del Objete
     *
     * @param id_String
     */
    public void setId_String(int id_String) {
        this.id_String = id_String;
    }

    /**
     * Conte el quart valor de la consulta del Objecte,que indicara l'accio a
     * fer al SQL es un JsonObject del Objete
     *
     * @return accion
     */
    public int getAccion() {
        return accion;
    }

    /**
     * Obtenim el quart valor que volem obtenir del Objecte,que indicara l'accio
     * a fer al SQL es un JsonObject del Objete
     *
     * @param accion
     */
    public void setAccion(int accion) {
        this.accion = accion;
    }

    /**
     * Conte el cinque valor de la consulta del Objecte,Ens donara la clase que
     * pertanye es un JsonObject del Objete
     *
     * @return clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * Obtenim el cinque valor que volem obtenir del Objecte,que indicara
     * l'accio a fer al SQL es un JsonObject del Objete
     *
     * @param clase
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * Conte el array que podrem tenir en la consulta del Objecte. podra ser un
     * JsonElement o formar part d'un JsonObjecte
     *
     * @return datos
     */
    public ArrayList<String> getDatos() {
        return datos;
    }

    /**
     * Obtenim el array que podrem tenir en la consulta del Objecte. podra ser
     * un JsonElement o formar part d'un JsonObjecte
     *
     * @param datos
     */
    public void setDatos(String datos) {
        this.datos.add(datos + "");
    }

    /**
     * Conte el valor que volem obtenir del Objecte,que indicara el token del
     * usuario es un JsonObject del Objete
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Obtenim el valor que volem obtenir del Objecte,que indicara el token del
     * usuario es un JsonObject del Objete
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * 
     * 
     * @return 
     */
    public Map<String, Object> getData() {
        return data;
    }
    
    /**
     * 
     * 
     * @param data 
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
