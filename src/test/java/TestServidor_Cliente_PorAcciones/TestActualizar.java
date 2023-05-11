/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorAcciones;

import TestServidor_Cliente_PorGestionesClases.TestGestionConductor;
import TestSocketSSL_Client.SocketSSL_Cliente;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.Encriptador;
import damjei_Servidor_Run.Server_SSLSocket;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Ivimar
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestActualizar {

    
    private static String HOST = "localhost";
    private static int PUERTO = 8180;
    private static String testToken;
    //instacia del servidor para gestionar el test
    Server_SSLSocket instance = new Server_SSLSocket();
    boolean testCorrecte = true;
    boolean correcte = false;
    

    /**
     * Primer Test Iniciamos el servidor
     *
     * @throws IOException
     */
    @Test
    public void TestA() throws IOException {
        
        System.out.println("---------TEST A-------------");
        System.out.println("-----INICIAR SERVIDOR-------");

        instance.initialize(HOST, PUERTO);
        instance.run();

        boolean repuesta = instance.isRunning();
        Assert.assertEquals(repuesta, instance.isRunning());

    }

    /**
     * Segundo Test Iniciamos primero usuario empleado como Administrador
     *
     * @throws IOException
     */
    @Test
    public void TestB() throws IOException {

        try {           
            System.out.println("--------------------TEST B-----------------------");
            System.out.println("-----INICIAR SESION EMPLEADO ADMINISTRADOR-------");

            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("admin1");
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"admin\",\"contraseña\":" + contraseña + "},\"clase\":\"Empleats.class\",\"accio\":0} ";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            // Obtener el Token para las pruebas
            JsonElement testJson = jsonObject.get("token");
            JsonElement correctaJson = jsonObject.get("correcte");
            if (testJson != null && !testJson.isJsonNull()) {
                testToken = testJson.getAsString();
                correcte = correctaJson.getAsBoolean();

            }

            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestGestionConductor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para Actualizar un empleado
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {
        
        try {
            System.out.println("-----------TEST C-------------");
            System.out.println("-----ACTUALIZAR EMPLEADO-------");
            
            // Serializar el objeto en formato Gson
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"admin\"},\"clase\":\"Empleats.class\",\"token\":"+ testToken + ",\"accio\":3 } ";
            
            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            JsonElement correctaJson = jsonObject.get("correcte");
            if (correctaJson != null && !correctaJson.isJsonNull()) {
                correcte = correctaJson.getAsBoolean();

            }
            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cuarto Test para Actualizar un vehiculo
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {

        try {
            System.out.println("-----------TEST D-------------");
            System.out.println("-----ACTUALIZAR VEHICULO-------");
            
            // Serializar el objeto en formato Gson
            String datos = "{\"vehicle\":{\"datosVh\":[],\"matricula\":\"SEA1234\"},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":3}"; 

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            JsonElement correctaJson = jsonObject.get("correcte");
            if (correctaJson != null && !correctaJson.isJsonNull()) {
                correcte = correctaJson.getAsBoolean();

            }
            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Quinto Test para Actualizar un Repostaje
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {

        try {
            System.out.println("------------TEST E-------------");
            System.out.println("-----ACTUALIZAR REPOSTAR-------");
  
            // Serializar el objeto en formato de Gson
            String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":1},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":3}"; 
 
            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            JsonElement correctaJson = jsonObject.get("correcte");
            if (correctaJson != null && !correctaJson.isJsonNull()) {
                correcte = correctaJson.getAsBoolean();

            }
            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sexto Test para Actualizar una Revision
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {

        try {
            System.out.println("------------TEST F-------------");
            System.out.println("-----ACTUALIZAR REVISION-------");
  
            // Serializar el objeto en formato de Gson
            String datos = "{\"revision\":{\"datosRev\":[],\"idrevision\":1},\"clase\":\"Revisiones.class\",\"token\":" + testToken + ",\"accio\":3}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            JsonElement correctaJson = jsonObject.get("correcte");
            if (correctaJson != null && !correctaJson.isJsonNull()) {
                correcte = correctaJson.getAsBoolean();

            }
            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Septimo Test para Actualizar un Mantenimiento
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {

        try {
            System.out.println("-----------TEST G-------------");
            System.out.println("-----ACTUALIZAR MANTENIMIENTO-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Liquido RefrigeraciÃ³n\"},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":3}";
    
            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            JsonElement correctaJson = jsonObject.get("correcte");
            if (correctaJson != null && !correctaJson.isJsonNull()) {
                correcte = correctaJson.getAsBoolean();

            }
            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Octavo Test para Actualizar un combustible
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {

        try {
            System.out.println("-----------TEST H-----------------");
            System.out.println("-----ACTUALIZAR COMBUSTIBLE-------");

            // Serializar el objeto en formato de Gson
            String datos = "{\"combustible\":{\"datosCom\":[],\"nombre\":\"Diesel\"},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":3}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            JsonElement correctaJson = jsonObject.get("correcte");
            if (correctaJson != null && !correctaJson.isJsonNull()) {
                correcte = correctaJson.getAsBoolean();

            }
            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Noveno Test para Actualizar un Conductor
     *
     * @throws IOException
     */
    @Test
    public void TestI() throws IOException {

        try {
            System.out.println("-----------TEST I-------------");
            System.out.println("-----ACTUALIZAR CONDUCTOR-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"conductor\":{\"datosCon\":[],\"idconductor\":1},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":3}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            JsonElement correctaJson = jsonObject.get("correcte");
            if (correctaJson != null && !correctaJson.isJsonNull()) {
                correcte = correctaJson.getAsBoolean();

            }
            socket.close();
            Assert.assertEquals(testCorrecte, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  
}
