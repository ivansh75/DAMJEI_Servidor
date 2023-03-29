/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test_ClientePruebas;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class containing a client program to test the mock server that works with
 * sockets
 *
 * @author Ivimar
 */
public class Cliente {

    private static final String USUARIO_ADMIN = "rrhh";
    private static final String USUARIO = "conductor";
    private static final String HOST = "localhost";
    private static final int PUERTO = 8180;
    private static int sessionUser;

    public Cliente() {

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
        // Crear una instancia de la clase y establecer sus valores
        Datos datos = new Datos();
        Empleat empleat = new Empleat();
        empleat.setNom("admin");
        empleat.setContrasenya("admin");
        empleat.setId_empresa(0);
        datos.setEmpleat(empleat);
        datos.setAccio(0);
        datos.setClase("Empleats.class");

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
        
        System.out.println("---------RECIBE CLIENTE---"+ data);
        
        socket.close();

        /*
        Socket socket = connect();

        Gson gson = new Gson();

        Empleat empleat = new Empleat();
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(Empleat.class));
        obtEmpleat.addProperty("accio", 1);
        obtEmpleat.addProperty("clase", "Empleat.class");
        System.out.println("------------" + obtEmpleat);

        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        output.println(gson.toJson(obtEmpleat));

      

        /* 
        InputStream entrada = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = entrada.read(buffer);
        String resposta = new String(buffer, 0, bytesRead);
         */
    }

}
