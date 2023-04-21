/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_ClientePruebas;

/**
 *
 * @author Ivimar
 */
public class DatosRevisiones {
    
    private RevisionesObjeto revision;
    private String clase;
    private String token;

    private int accio;

    public DatosRevisiones() {
    }
 
  // getters y setters

    public RevisionesObjeto getRevision() {
        return revision;
    }

    public void setRevision(RevisionesObjeto revision) {
        this.revision = revision;
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



