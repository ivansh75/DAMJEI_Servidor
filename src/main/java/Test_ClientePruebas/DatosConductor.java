/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_ClientePruebas;

/**
 *
 * @author Ivimar
 */
public class DatosConductor {
    
    private ConductorObjeto conductor;
    private String clase;
    private String token;
    //private boolean correcte;
    //private boolean administrador;
    private int accio;

    public DatosConductor() {
    }
 
  // getters y setters
    public ConductorObjeto getConductor() {
        return conductor;
    }

    public void setConductor(ConductorObjeto conductor) {
        this.conductor = conductor;
    }


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
    
     public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
  
}



