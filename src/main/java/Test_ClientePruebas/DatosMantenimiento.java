/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_ClientePruebas;

/**
 *
 * @author Ivimar
 */
public class DatosMantenimiento {
    
    private MantenimientoObjeto mantenimiento;
    private String clase;
    private String token;

    private int accio;

    public DatosMantenimiento() {
    }
 
  // getters y setters

    public MantenimientoObjeto getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(MantenimientoObjeto mantenimiento) {
        this.mantenimiento = mantenimiento;
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



