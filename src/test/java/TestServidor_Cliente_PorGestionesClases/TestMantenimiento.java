/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorGestionesClases;

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
public class TestMantenimiento {

    private static String testToken;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String HOST = "localhost";
    private static int PUERTO = 8180;
    //instacia del servidor para gestionar el test
    Server_SSLSocket instance = new Server_SSLSocket();
    boolean testCorrecte = true;
    boolean correcte = false;

    /**
     * Priemer Test Arrancamos servidor primero
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
     *
     * Segundo Iniciamos primero usuario como administrador
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
            Logger.getLogger(TestMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para insertar un Mantenimiento nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {

        try {
            boolean correcte = false;

            System.out.println("----------------TEST C-----------------");
            System.out.println("-----INSERTAR NUEVO MANTENIMIENTO-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Líquido Refrigeración\",\"kilometros_mantenimiento\":60000},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            Logger.getLogger(TestMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Quinto Test para Mostrar Lista de Mantenimiento Vemos como esta añadido,
     * modificcado o eliminado el Mantenimiento
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {

        try {
            System.out.println("--------------TEST E------------------");
            System.out.println("-----MOSTRAR LISTA DE MANTENIMIENTO-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"mantenimiento\":{\"datosMt\":[]},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":5}";

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
            Logger.getLogger(TestMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test para Eliminar un Mantenimiento
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {

        try {
            System.out.println("----------TEST F------------");
            System.out.println("-----ELIMINAR MANTENIMIENTO-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Líquido Refrigeración\"},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            Logger.getLogger(TestMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Septimo Test par Modificar Vehiculo
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {

        try {
            System.out.println("--------------TEST G------------------");
            System.out.println("--------MODIFICAR MANTENIMIENTO------------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Líquido Refrigeración\",\"kilometros_mantenimiento\":60000,\"fecha_mantenimiento\":\"2024-4-22\"},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            Logger.getLogger(TestMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Octavo Test para mostrar un Mantenimiento por su id
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {

        try {
            System.out.println("-----------------TEST H----------------------");
            System.out.println("-----MOSTRAR LISTA DE UN MANTENIMIENTO-------");

            // Serializar el objeto en formato JSON usando Gson
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"idmantenimiento\":1},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":7}";

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
            Logger.getLogger(TestMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
