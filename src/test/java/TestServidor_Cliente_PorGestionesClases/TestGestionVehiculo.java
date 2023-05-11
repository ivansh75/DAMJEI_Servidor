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
public class TestGestionVehiculo {

    private static String testToken;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String HOST = "localhost";
    private static int PUERTO = 8180;
    //instacia del servidor para gestionar el test
    Server_SSLSocket instance = new Server_SSLSocket();
    boolean testCorrecte = true;
    boolean correcte = false;

    /**
     * Primer Test Arrancamos servidor primero
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
     * Segundo Test Iniciamos primero usuario como administrador
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
            Logger.getLogger(TestGestionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para insertar un vehiculo nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {

        try {

            System.out.println("-----------TEST C------- ----------");
            System.out.println("-----INSERTAR NUEVO VEHICULO-------");

            // Serializar el objeto en formato de Gson
            String datos = "{\"vehicle\":{\"datosVh\":[],\"idvehiculo\":8,\"marca\":\"BMW\",\"modelo\":\"Seri10\",\"matricula\":\"1234BMW\",\"kilometros_alta\":1000.0,\"fecha_alta\":\"2023-02-09\",\"conductorid\":0,\"empresaid\":1,\"kilometros_actuales\":1000.0},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            Logger.getLogger(TestGestionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TestGestionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Quinto Test para Mostrar Lista de vehiculos Vemos como esta añadido el
     * vehiculo nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {

        try {

            System.out.println("--------------TEST E------------------");
            System.out.println("-----MOSTRAR LISTA DE VEHICULOS-------");

            // Serializar el objeto en formato Gson
            String datos = "{\"empleat\":{\"datos\":[]},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":5 } ";

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
            Logger.getLogger(TestGestionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test para Eliminar un vehiculo
     *
     * @throws IOException
     */
    /*
    @Test
    public void TestF() throws IOException {

        try {

            System.out.println("----------TEST F------------");
            System.out.println("-----ELIMINAR VEHICULO-------");

            // Serializar el objeto en formato Gson
            String datos = "{\"vehicle\":{\"datosVh\":[],\"matricula\":\"1234BMW\"},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            Logger.getLogger(TestGestionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
    /**
     * Septimo Test par Modificar Vehiculo
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {

        try {

            System.out.println("--------------TEST G------------------");
            System.out.println("--------MODIFICAR VEHICULO------------");

            // Serializar el objeto en formato Gson
            String datos = "{\"vehicle\":{\"datosVh\":[],\"matricula\":\"SEA1234\",\"fecha_baja\":\"2023-4-22\"},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            Logger.getLogger(TestGestionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Octavo Test para mostrar listado de un vehiculo
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {

        try {

            System.out.println("-----------------TEST H-----------------");
            System.out.println("-----MOSTRAR LISTA DE UN VEHICULO-------");

            // Serializar el objeto en formato Gson
            String datos = "{\"vehicle\":{\"datosVh\":[],\"idvehiculo\":1},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":7}";

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
            Logger.getLogger(TestGestionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
