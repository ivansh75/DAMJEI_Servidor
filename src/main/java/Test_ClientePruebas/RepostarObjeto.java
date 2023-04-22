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
public class RepostarObjeto {
  
    private ArrayList<String> datosRep = new ArrayList<>(); 
    private int idrepostar;
    private String fecha_repostar;
    private Float importe_repostar;
    private Float kilometros_repostar;
    private int combustibleid;
    private int vehiculoid;
    private int conductorid;
    private Float litros;
   

     public RepostarObjeto() {
    }
     // getters y setters

    public ArrayList<String> getDatosRep() {
        return datosRep;
    }

    public void setDatosRep(ArrayList<String> datosRep) {
        this.datosRep = datosRep;
    }

    public int getIdrepostar() {
        return idrepostar;
    }

    public void setIdrepostar(int idrepostar) {
        this.idrepostar = idrepostar;
    }

    public String getFecha_repostar() {
        return fecha_repostar;
    }

    public void setFecha_repostar(String fecha_repostar) {
        this.fecha_repostar = fecha_repostar;
    }

    public Float getImporte_repostar() {
        return importe_repostar;
    }

    public void setImporte_repostar(Float importe_repostar) {
        this.importe_repostar = importe_repostar;
    }

    public Float getKilometros_repostar() {
        return kilometros_repostar;
    }

    public void setKilometros_repostar(Float kilometros_repostar) {
        this.kilometros_repostar = kilometros_repostar;
    }

    public int getCombustibleid() {
        return combustibleid;
    }

    public void setCombustibleid(int combustibleid) {
        this.combustibleid = combustibleid;
    }

    public int getVehiculoid() {
        return vehiculoid;
    }

    public void setVehiculoid(int vehiculoid) {
        this.vehiculoid = vehiculoid;
    }

    public int getConductorid() {
        return conductorid;
    }

    public void setConductorid(int conductorid) {
        this.conductorid = conductorid;
    }

    public Float getLitros() {
        return litros;
    }

    public void setLitros(Float litros) {
        this.litros = litros;
    }



}
