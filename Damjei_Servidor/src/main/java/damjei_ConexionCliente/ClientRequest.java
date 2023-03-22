/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author Ivimar
 */
public class ClientRequest {

    private String clase;
    private Object objecte;
    private String consultaString;
    private ArrayList<String> consulta = new ArrayList<>();
    private Gson gson;

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public String getConsultaString() {
        return consultaString;
    }

    public void setConsultaString(String consultaString) {
        this.consultaString = consultaString;
    }

    public ClientRequest(Object objecte, int accio) {
        this.objecte = objecte;
        this.accio = accio;
    }

    public void setConsulta(ArrayList<String> consulta) {
        this.consulta = consulta;
    }

    private int accio;

    public int getAccio() {
        return accio;
    }

    public void setAccio(int accio) {
        this.accio = accio;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Object getObjecte() {
        return objecte;
    }

    public void setObjecte(Object objecte) {
        this.objecte = objecte;
    }

    public Object getConsulta(int i, Class<ClientRequest> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
