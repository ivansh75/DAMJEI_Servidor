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
public class Empleat {
    
    private ArrayList<String> datos = new ArrayList<>(); 
    private int idempleado;
    private String nom;
    private String apellidos;
    private String dni;
    private String categoria;
    private String fecha_carnet;
    private String fecha_caducidad_carnet;
    private int empresaid;
    private String contraseña;
    private boolean administrador;

    public Empleat() {
    }

   //getters and setters

    public ArrayList<String> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<String> datos) {
        this.datos = datos;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public int getId_empresa() {
        return empresaid;
    }

    public void setId_empresa(int id_empresa) {
        this.empresaid = id_empresa;
    }

    public String getContrasenya() {
        return contraseña;
    }

    public void setContrasenya(String contrasenya) {
        this.contraseña = contrasenya;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
   
    
}
