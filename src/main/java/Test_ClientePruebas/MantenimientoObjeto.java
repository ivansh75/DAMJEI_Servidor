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
public class MantenimientoObjeto {
  
    private ArrayList<String> datosMt = new ArrayList<>(); 
    private int idmantenimiento;
    private String nombre;
    private Float kilometros_mantenimiento;
    private String fecha_mantenimiento;
   

     public MantenimientoObjeto() {
    }
     
     // getters y setters
    public ArrayList<String> getDatosMt() {
        return datosMt;
    }

    public void setDatosMt(ArrayList<String> datosMt) {
        this.datosMt = datosMt;
    }

    public int getIdmantenimiento() {
        return idmantenimiento;
    }

    public void setIdmantenimiento(int idmantenimiento) {
        this.idmantenimiento = idmantenimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getKilometros_mantenimiento() {
        return kilometros_mantenimiento;
    }

    public void setKilometros_mantenimiento(Float kilometros_mantenimiento) {
        this.kilometros_mantenimiento = kilometros_mantenimiento;
    }

    public String getFecha_mantenimiento() {
        return fecha_mantenimiento;
    }

    public void setFecha_mantenimiento(String fecha_mantenimiento) {
        this.fecha_mantenimiento = fecha_mantenimiento;
    }

    
}
