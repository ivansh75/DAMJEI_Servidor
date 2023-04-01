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
public class TestServer {

    //instacia del servidor para gestionar el test
    Server instance = new Server();

    /**
     * Primer Test para inicializar el Servidor
     *
     * @throws IOException
     */
    @Test
    public void IniciarServerOK_Test1() throws IOException {
        System.out.println("-----TEST 1-------");
        System.out.println("-----INICIAR SERVIDOR-------");

        instance.initialize("localhost", 8180);
        instance.run();

        boolean repuesta = instance.isRunning();
        Assert.assertEquals(repuesta, instance.isRunning());

    }

    /**
     * SegundoTest para Iniciar Sesion como Cliente Administardor
     *
     * @throws IOException
     */
    @Test
    public void InicarSesioClienteAdministradorOK_Test2() throws IOException {
        System.out.println();
        System.out.println("-----TEST 2-------");
        System.out.println("-----INICIAR SESION CLIENTE ADMINISTRADOR-------");

        instance.initialize("localhost", 8180);
        instance.run();
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
        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = input.readLine();

        socket.close();

        String jsonR = data.toString();

        Assert.assertEquals(jsonR, data);

    }

    /**
     * Tercer Test Iniciamos Sesion como Cliente Usuario
     *
     * @throws IOException
     */
    @Test
    public void InicarSesioClienteUserOK_Test3() throws IOException {
        System.out.println();
        System.out.println("-----TEST 3-------");
        System.out.println("-----INICIAR SESION CLIENTE USUARIO-------");
        instance.initialize("localhost", 8180);
        instance.run();
        // Crear una instancia de la clase y establecer sus valores
        Datos datos = new Datos();
        Empleat empleat = new Empleat();
        empleat.setNom("user");
        empleat.setContrasenya("user");
        empleat.setId_empresa(0);
        datos.setEmpleat(empleat);
        datos.setAccio(0);
        datos.setClase("Empleats.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = input.readLine();

        socket.close();

        String jsonR = data.toString();

        Assert.assertEquals(jsonR, data);

    }

    /**
     * Cuarto Test Para cerrar sesion Cliente
     *
     * @throws IOException
     */
    @Test
    public void CerrarSesionCliente_Test4() throws IOException {
        System.out.println();
        System.out.println("-----TEST 4-------");
        System.out.println("-----CERRAR SESION CLIENTE USUARIO-------");

        instance.initialize("localhost", 8180);
        instance.run();
        // Crear una instancia de la clase y establecer sus valores
        Datos datos = new Datos();
        Empleat empleat = new Empleat();
        empleat.setNom("admin");
        empleat.setContrasenya("admin");
        empleat.setId_empresa(0);
        datos.setEmpleat(empleat);
        datos.setAccio(1);
        datos.setClase("Empleats.class");
        

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = input.readLine();

        socket.close();

        String jsonR = data.toString();

        Assert.assertEquals(jsonR, data);

    }

    /**
     * Quinto Test ParaIniciar sesion como Cliente no Registrado o Autorizado
     *
     * @throws IOException
     */
    @Test
    public void InicarSesioClienteNOK_Test5() throws IOException {
        System.out.println();
        System.out.println("-----TEST 5-------");
        System.out.println("-----INICIAR SESION CLIENTE NO AUTORIZADO NOK-------");

        instance.initialize("localhost", 8180);
        instance.run();
        // Crear una instancia de la clase y establecer sus valores
        Datos datos = new Datos();
        Empleat empleat = new Empleat();
        empleat.setNom("Usuario");
        empleat.setContrasenya("user");
        empleat.setId_empresa(0);
        datos.setEmpleat(empleat);
        datos.setAccio(0);
        datos.setClase("Empleats.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = input.readLine();

        socket.close();

        String jsonR = data.toString();

        Assert.assertEquals(jsonR, data);

    }

}
