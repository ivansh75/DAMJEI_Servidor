/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_ClientePruebas;

import java.util.ArrayList;

/**
 *
 * @author Ivimar
 */
public class CombustibleObjeto {
    
    private ArrayList<String> datosCom = new ArrayList<>(); 
    private int idcombustible;
    private String nombre;   
    private Float precio;

    public CombustibleObjeto() {
    }

    public ArrayList<String> getDatosCom() {
        return datosCom;
    }

    public void setDatosCom(ArrayList<String> datosCom) {
        this.datosCom = datosCom;
    }

    public int getIdcombustible() {
        return idcombustible;
    }

    public void setIdcombustible(int idcombustible) {
        this.idcombustible = idcombustible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
      
    
}
