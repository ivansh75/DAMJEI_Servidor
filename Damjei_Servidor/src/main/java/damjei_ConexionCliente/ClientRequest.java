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
    private String String3;
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
     * Contiene el primer valor de la consulta del Objecte, es un JsonElement del
     * Objeto
     *
     * @return el primer valor de la consulta
     */
    public String getString1() {
        return String1;
    }

    /**
     * obtenemos el primer valor del JsonElement
     * del Objeto
     *
     * @param String1
     */
    public void setString1(String String1) {
        this.String1 = String1;
    }

    /**
     * Contiene el segundo valor de la consulta del JsonElement del
     * Objeto
     *
     * @return String2
     */
    public String getString2() {
        return String2;
    }

    /**
     * Obtenemos el segundo valor del JsonElement
     * del Objeto
     *
     * @param String2
     */
    public void setString2(String String2) {
        this.String2 = String2;
    }
    /**
     * Contiene un valor que utilizaremos para modificaciones
     * @return String3
     */
     public String getString3() {
        return String3;
    }
    /**
     * Obtenim un valor que utilizzarem per modificacions
     * @param String3 
     */
    public void setString3(String String3) {
        this.String3 = String3;
    }

    /**
     * Contiene el tercer valor de la consulta del puede ser id_empresa
     * es un JsonElement del Objeto
     *
     * @return id_String
     */
    public int getId_String() {
        return id_String;
    }

    /**
     * Obtenemos el tercer valor del Objecte, puede ser
     * id_empresa es un JsonElement del Objeto
     *
     * @param id_String
     */
    public void setId_String(int id_String) {
        this.id_String = id_String;
    }

    /**
     * Contiene el Cuarto valor del Objecto,que indicara la accio hacer
     * al SQL, es un JsonObject del Objete
     *
     * @return accion
     */
    public int getAccion() {
        return accion;
    }

    /**
     * Obtenemos el cuarto valor del Objecte,que indicara la accio
     * hacer al SQL, es un JsonObject del Objete
     *
     * @param accion
     */
    public void setAccion(int accion) {
        this.accion = accion;
    }

    /**
     * Contiene el quinto valor de la consulta del Objecto,Con el sabremos
     * a la clase que pertenece el envio de datos es un JsonObject del Objete
     *
     * @return clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * Obetenemos el quinto valor del Objecto,que nos indicara
     * a la clase que pertenece el envio de datos es un JsonObject del Objete 
     *
     * @param clase
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * Contiene el array que podremos utilizar del Objecte. para hacer un insert
     * y poder tener una lista de datos
     * JsonElement o formar parte de un JsonObjecte
     *
     * @return datos
     */
    public ArrayList<String> getDatos() {
        return datos;
    }

    /**
     * Obtenemos el array que podremos utilizar del Objecte. para hacer un insert
     * y poder tener una lista de datos
     * JsonElement o formar parte de un JsonObjecte
     *
     * @param datos
     */
    public void setDatos(String datos) {
        this.datos.add(datos + "");
    }

    /**
     * Contiene el valor del Objecte,que indicara el token del
     * usuario es un JsonObject del Objete
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Obtendremos el valor del Objecte,que indicara el token del
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
