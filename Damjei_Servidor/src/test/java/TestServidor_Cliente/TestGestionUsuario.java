/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestServidor_Cliente;

import Test_ClientePruebas.Datos;
import Test_ClientePruebas.Empleat;
import com.google.gson.Gson;
import damjei_Servidor_Run.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import junit.framework.Assert;

import org.junit.Test;

/**
 *
 * @author Ivimar
 */
public class TestGestionUsuario {

    //instacia del servidor para gestionar el test
    Server instance = new Server();
    boolean correcte = false;

    /**
     * Primer Test para inicializar el Servidor
     *
     * @throws IOException
     */
    @Test
    public void IniciarServerOK() throws IOException {
        System.out.println("-----TEST 1-------");
        System.out.println("-----INICIAR SERVIDOR-------");

        instance.initialize("localhost", 8180);
        instance.run();

        boolean repuesta = instance.isRunning();
        Assert.assertEquals(repuesta, instance.isRunning());

    }

    /**
     * Test para insertar a un usuario nuevo
     *
     * @throws IOException
     */
    @Test
    public void InsertarUsuario() throws IOException {
        System.out.println("-----TEST 2-------");
        System.out.println("-----INSERTAR USUARIO SERVIDOR-------");

        // Crear una instancia de la clase y establecer sus valores
        Datos datos = new Datos();
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

        String jsonR = data.toString();
        Assert.assertEquals(jsonR, data);

    }
    /**
     * Test para Actualizar a un usuario nuevo
     * 
     * @throws IOException 
     */
    @Test
    public void Actualizar() throws IOException {
        System.out.println("-----TEST 3-------");
        System.out.println("-----ACTUALIZAR CONTRASEÑA-------");
        // Crear una instancia de la clase y establecer sus valores
        Datos datos = new Datos();
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

        String jsonR = data.toString();
        Assert.assertEquals(jsonR, data);

    }

    /**
     * Test para Actulizar contraseña usuario
     *
     * @throws IOException
     */
    @Test
    public void Modificar() throws IOException {

        System.out.println("-----TEST 6-------");
        System.out.println("-----MODIFICAR CONTRASEÑA-------");

        // Crear una instancia de la clase y establecer sus valores
        Datos datos = new Datos();
        Empleat empleat = new Empleat();
        empleat.setDni("admin");
        empleat.setContrasenya("admin");
        empleat.setId_empresa(1);
        datos.setEmpleat(empleat);
        datos.setAccio(3);
        datos.setClase("Empleats.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        System.out.println("--------- CLIENTE---" + json);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        System.out.println("---------RECIBE CLIENTE---" + data);

        socket.close();

        String jsonR = data.toString();
        Assert.assertEquals(jsonR, data);

    }

}
