/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorGestionesClases;

import TestServidor_Cliente_PorAcciones.TestEliminar;
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
import java.text.SimpleDateFormat;
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
public class TestGestionConductor {

    private static String testToken;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String HOST = "localhost";
    private static int PUERTO = 8180;
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

        instance.initialize("localhost", 8180);
        instance.run();

        boolean repuesta = instance.isRunning();
        Assert.assertEquals(repuesta, instance.isRunning());

    }

    /**
     * Segundo Test Iniciamos primero usuario empleado como administrador
     *
     * @throws IOException
     */
    @Test
    public void TestB() throws IOException {

        try {

            System.out.println("-------------------TEST B-----------------------");
            System.out.println("-----INICIAR SESION CLIENTE ADMINISTRADOR-------");

            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("admin1");
            // Serializar el objeto en formato de Gson
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
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestGestionConductor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Quinto Test para Mostrar Lista de Conductores. Vemos como esta añadido el
     * Conductor nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {

        try {

            System.out.println("--------------TEST E------------------");
            System.out.println("-----MOSTRAR LISTA DE CONDUCTOR-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"conductor\":{\"datosCon\":[]},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":5}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            String jsonR = data.toString();

            socket.close();

            Assert.assertEquals(jsonR, data);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestGestionConductor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Septimo Test para modificar un Conductor
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {

        try {

            System.out.println("----------TEST G-------------");
            System.out.println("-----MODIFICAR CONDUCTOR-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"conductor\":{\"datosCon\":[],\"idconductor\":2,\"fecha_caducidad_carnet\":\"2035-01-12\"},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            TestE();
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestGestionConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Octavo Test para Mostrar Lista del Conductor. Vemos como esta añadido el
     * Conductor nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {

        try {

            System.out.println("----------------TEST H------------------");
            System.out.println("-----MOSTRAR LISTA DE UN CONDUCTOR-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"conductor\":{\"datosCon\":[],\"idconductor\":2},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":7}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            String jsonR = data.toString();

            socket.close();

            Assert.assertEquals(jsonR, data);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestGestionConductor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Noveno Test para Eliminar un empleado
     *
     * @throws IOException
     */
    @Test
    public void TestI() throws IOException {

        try {

            System.out.println("----------TEST I------------");
            System.out.println("-----ELIMINAR EMPLEADO CONDUCTOR-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"conductor\":{\"datosCon\":[],\"idconductor\":\"2\"},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":4}";

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
