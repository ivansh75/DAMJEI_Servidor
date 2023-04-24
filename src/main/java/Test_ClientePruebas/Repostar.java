/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test_ClientePruebas;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Class containing a client program to test the mock server that works with
 * sockets
 *
 * @author Ivimar
 */
public class Repostar {

    private static final String HOST = "localhost";
    private static final int PUERTO = 8180;
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Repostar() {

    }
    //opens a connection to the server

    private static Socket connect() {
        Socket s;
        if (PUERTO == -1 || HOST == null) {
            return null;
        } else {
            try {
                s = new Socket(HOST, PUERTO);
                s.setSoTimeout(4000);
                return s;
            } catch (IOException ex) {
                return null;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //Actualizar();
        //Eliminar();
        Listar();
        //Actualizar();

    }



    public void InsertarRepostar() throws IOException, ParseException {
        
        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();
        
        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        repObjet.setFecha_repostar("2023-02-03");
        repObjet.setImporte_repostar(Float.parseFloat("100"));
        repObjet.setKilometros_repostar(Float.parseFloat("1500"));
        repObjet.setCombustibleid(1);
        repObjet.setVehiculoid(1);
        repObjet.setConductorid(1);
        repObjet.setLitros(Float.parseFloat("30"));
        datos.setRepostar(repObjet);
        datos.setAccio(2);
        datos.setClase("Repostar.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        System.out.println("---------ENVIA CLIENTE---" + json);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        System.out.println("-------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Actualizar() throws IOException {
        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();
        
        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        datos.setRepostar(repObjet);
        datos.setAccio(3);
        datos.setClase("Repostar.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        System.out.println("---------ENVIA CLIENTE---" + json);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Eliminar() throws IOException {
          // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();
        
        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        datos.setRepostar(repObjet);
        datos.setAccio(4);
        datos.setClase("Repostar.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        System.out.println("---------ENVIA CLIENTE---" + json);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Listar() throws IOException {
         // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();
        
        RepostarObjeto repObjet = new RepostarObjeto();
        datos.setRepostar(repObjet);
        datos.setAccio(5);
        datos.setClase("Repostar.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        System.out.println("---------ENVIA CLIENTE---" + json);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Modificar() throws IOException {

        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();
        
        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        repObjet.setConductorid(1);
        datos.setRepostar(repObjet);
        datos.setAccio(6);       
        datos.setClase("Repostar.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        System.out.println("---------ENVIA CLIENTE---" + json);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

}
