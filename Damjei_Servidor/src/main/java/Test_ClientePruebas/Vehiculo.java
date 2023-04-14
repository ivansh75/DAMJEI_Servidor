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
public class Vehiculo {

    private static final String HOST = "localhost";
    private static final int PUERTO = 8180;
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Vehiculo() {

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



    public void InsertarVehiculo() throws IOException, ParseException {
        
        // Crear una instancia de la clase y establecer sus valores
        DatosVehicle datos = new DatosVehicle();
        
        Vehicle vehicle = new Vehicle();
        vehicle.setIdvehiculo(8);
        vehicle.setMarca("BMW");
        vehicle.setModelo("Seri10");
        vehicle.setMatricula("1234BMW");
        vehicle.setKilometros_alta(Float.valueOf(1000));
        vehicle.setFecha_alta("2023-02-09");
        vehicle.setEmpresaid(1);
        vehicle.setKilometros_actuales(Float.valueOf(1000));
        datos.setVehicle(vehicle);
        datos.setAccio(2);
        datos.setClase("Vehicle.class");

        //list.add(String.valueOf( empleat.getId_empresa()));
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
        DatosVehicle datos = new DatosVehicle();
        Vehicle vehicle = new Vehicle();
        vehicle.setMatricula("SEA1234");
        datos.setVehicle(vehicle);
        datos.setAccio(3);
        datos.setClase("Vehicle.class");

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
        DatosVehicle datos = new DatosVehicle();
        Vehicle vehicle = new Vehicle();
        vehicle.setMatricula("1234BMW");
        datos.setVehicle(vehicle);
        datos.setAccio(4);
        datos.setClase("Vehicle.class");

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
        DatosVehicle datos = new DatosVehicle();
        Vehicle vehicle = new Vehicle();
        datos.setVehicle(vehicle);
        datos.setAccio(5);
        datos.setClase("Vehicle.class");

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
        DatosVehicle datos = new DatosVehicle();
        Vehicle vehicle = new Vehicle();
        vehicle.setMatricula("SEA1234");
        vehicle.setConductorid(2);
        vehicle.setEmpresaid(1);
        datos.setVehicle(vehicle);
        datos.setAccio(6);
        datos.setClase("Vehicle.class");

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
