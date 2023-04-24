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

/**
 * Class containing a client program to test the mock server that works with
 * sockets
 *
 * @author Ivimar
 */
public class Empleado {

    private static final String HOST = "localhost";
    private static final int PUERTO = 8180;


    public Empleado() {

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
        Usuario();
        //InsertarUsuario();
        //Actualizar();
        //Eliminar();
        Listar();
        //Actualizar();

    }

    public static void Usuario() throws IOException {

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        empleat.setDni("admin");
        empleat.setContrasenya("admin1");
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

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void InsertarUsuario() throws IOException {

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        //ArrayList<String> list = new ArrayList<>();
        Empleat empleat = new Empleat();
        empleat.setIdempleado(8);
        empleat.setNom("Joan");
        empleat.setApellidos("Ramon Serra");
        empleat.setDni("12345678j");
        empleat.setCategoria("rrhh");
        empleat.setId_empresa(1);
        empleat.setContrasenya("12345678j");
        empleat.setAdministrador(true);
        datos.setEmpleat(empleat);
        datos.setAccio(2);
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

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Actualizar() throws IOException {
        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        empleat.setDni("admin");
        datos.setEmpleat(empleat);
        datos.setAccio(3);
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

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Eliminar() throws IOException {
        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        empleat.setDni("12345678j");
        datos.setEmpleat(empleat);
        datos.setAccio(4);
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

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Listar() throws IOException {
        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        datos.setEmpleat(empleat);
        datos.setAccio(5);
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

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

    public static void Modificar() throws IOException {

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        empleat.setDni("admin");
        empleat.setCategoria("modificada");
        datos.setEmpleat(empleat);
        datos.setAccio(6);
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

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

    }

}
