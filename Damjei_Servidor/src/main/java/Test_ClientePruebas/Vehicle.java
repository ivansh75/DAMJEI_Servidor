/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_ClientePruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ivimar
 */
public class Vehicle {
  
    private ArrayList<String> datosVh = new ArrayList<>(); 
    private int idvehiculo;
    private String marca;
    private String modelo;
    private String matricula;
    private Float kilometros_alta;
    private String fecha_alta;
    private String fecha_baja;
    private int conductorid;
    private int empresaid;
    private Float kilometros_actuales;
   

     public Vehicle() {
    }
     // getters y setters

    public ArrayList<String> getDatosVh() {
        return datosVh;
    }

    public void setDatosVh(ArrayList<String> datosVh) {
        this.datosVh = datosVh;
    }

    public int getIdvehiculo() {
        return idvehiculo;
    }

    public void setIdvehiculo(int idvehiculo) {
        this.idvehiculo = idvehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Float getKilometros_alta() {
        return kilometros_alta;
    }

    public void setKilometros_alta(Float kilometros_alta) {
        this.kilometros_alta = kilometros_alta;
    }

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(String fecha_baja) {
        this.fecha_baja = fecha_baja;
    }


    public int getConductorid() {
        return conductorid;
    }

    public void setConductorid(int conductorid) {
        this.conductorid = conductorid;
    }

    public int getEmpresaid() {
        return empresaid;
    }

    public void setEmpresaid(int empresaid) {
        this.empresaid = empresaid;
    }

    public Float getKilometros_actuales() {
        return kilometros_actuales;
    }

    public void setKilometros_actuales(Float kilometros_actuales) {
        this.kilometros_actuales = kilometros_actuales;
    }

   
}
