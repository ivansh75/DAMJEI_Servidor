/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_ClientePruebas;

/**
 *
 * @author Ivimar
 */
public class DatosVehicle {
    
    private Vehicle vehicle;
    private String clase;
    private String token;

    private int accio;

    public DatosVehicle() {
    }
 
  // getters y setters

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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



