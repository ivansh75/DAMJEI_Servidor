/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_ClientePruebas;

/**
 *
 * @author Ivimar
 */
public class Datos {

    public Datos() {
    }
    
    private Empleat empleat;
    private String clase;
    private String token;
    private boolean correcte;
    private boolean administrador;
    private int accio;

  
  // getters y setters
    public Empleat getEmpleat() {
        return empleat;
    }

    public void setEmpleat(Empleat empleat) {
        this.empleat = empleat;
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
     public boolean isCorrecta() {
        return correcte;
    }

    public void setCorrecta(boolean correcte) {
        this.correcte = correcte;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
  
}



