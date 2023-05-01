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
public class RevisionesObjeto {
  
    private ArrayList<String> datosRev = new ArrayList<>(); 
    private int idrevision;
    private String fecha_revision;
    private Float kilometros_revision;
    private int vehiculoid;
    private int mantenimientoid;
    private boolean estado_revision;
   

     public RevisionesObjeto() {
    }
     // getters y setters

    public ArrayList<String> getDatosRev() {
        return datosRev;
    }

    public void setDatosRev(ArrayList<String> datosRev) {
        this.datosRev = datosRev;
    }

    public int getIdrevision() {
        return idrevision;
    }

    public void setIdrevision(int idrevision) {
        this.idrevision = idrevision;
    }

    public String getFecha_revision() {
        return fecha_revision;
    }

    public void setFecha_revision(String fecha_revision) {
        this.fecha_revision = fecha_revision;
    }

    public Float getKilometros_revision() {
        return kilometros_revision;
    }

    public void setKilometros_revision(Float kilometros_revision) {
        this.kilometros_revision = kilometros_revision;
    }

    public int getVehiculoid() {
        return vehiculoid;
    }

    public void setVehiculoid(int vehiculoid) {
        this.vehiculoid = vehiculoid;
    }

    public int getMantenimientoid() {
        return mantenimientoid;
    }

    public void setMantenimientoid(int mantenimientoid) {
        this.mantenimientoid = mantenimientoid;
    }

    public boolean isEstado_revision() {
        return estado_revision;
    }

    public void setEstado_revision(boolean estado_revision) {
        this.estado_revision = estado_revision;
    }



}
