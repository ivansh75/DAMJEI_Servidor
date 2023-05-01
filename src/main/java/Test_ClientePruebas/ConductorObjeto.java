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
public class ConductorObjeto {
    
    private ArrayList<String> datosCon = new ArrayList<>(); 
    private int idconductor;
    private String fecha_carnet;   
    private String fecha_caducidad_carnet;
    private int empleadoid;

    public ConductorObjeto() {
    }

    public ArrayList<String> getDatosCon() {
        return datosCon;
    }

    public void setDatosCon(ArrayList<String> datosCon) {
        this.datosCon = datosCon;
    }

    public int getIdconductor() {
        return idconductor;
    }

    public void setIdconductor(int idconductor) {
        this.idconductor = idconductor;
    }

    public String getFecha_carnet() {
        return fecha_carnet;
    }

    public void setFecha_carnet(String fecha_carnet) {
        this.fecha_carnet = fecha_carnet;
    }

    public String getFecha_caducidad_carnet() {
        return fecha_caducidad_carnet;
    }

    public void setFecha_caducidad_carnet(String fecha_caducidad_carnet) {
        this.fecha_caducidad_carnet = fecha_caducidad_carnet;
    }

    public int getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(int empleadoid) {
        this.empleadoid = empleadoid;
    }
  
    
}
