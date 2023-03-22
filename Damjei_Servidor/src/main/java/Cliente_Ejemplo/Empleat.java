/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente_Ejemplo;

/**
 *
 * @author Ivimar
 */

public class Empleat {

    private String nom = "admin";
    private String contrasenya = "admin";
    private int id_empresa = 0;
    private String empleat = "admin";
    
    public Empleat() {
    }

    public Empleat(String nom, String categoria,String id_empresa) {
        //this.id_empresa = id_empresa;
        this.nom = "admin";
        this.contrasenya = "admin";
        this.id_empresa = 0;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    
    
}