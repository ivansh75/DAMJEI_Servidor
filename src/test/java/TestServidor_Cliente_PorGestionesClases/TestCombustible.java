/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorGestionesClases;

import TestSocketSSL_Client.SocketSSL_Cliente;
import Test_ClientePruebas.CombustibleObjeto;
import Test_ClientePruebas.DatosCombustible;
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
public class TestCombustible {

    private static String testToken;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String HOST = "localhost";
    private static int PUERTO = 8180;
    //instacia del servidor para gestionar el test
    Server_SSLSocket instance = new Server_SSLSocket();
    boolean testCorrecte = true;

    /**
     * Primer Test Arrancamos servidor primero
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
     *
     * Segundo Test Iniciamos primero usuario como administrador
     *
     * @throws IOException
     */
    @Test
    public void TestB() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("-------------------TEST B-----------------------");
            System.out.println("-----INICIAR SESION CLIENTE ADMINISTRADOR-------");
            /*
            // Crear una instancia de la clase y establecer sus valores
            DatosEmpleado datos = new DatosEmpleado();
            Empleat empleat = new Empleat();
            empleat.setDni("admin");
            empleat.setContraseña("admin1");
            empleat.setId_empresa(1);
            datos.setEmpleat(empleat);
            datos.setAccio(0);
            datos.setClase("Empleats.class");
             */
            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("admin1");
            String datos = "{\"empleat\":{\"datos\":[],\"idempleado\":0,\"dni\":\"admin\",\"empresaid\":0,\"contraseña\":" + contraseña + ",\"administrador\":false},\"clase\":\"Empleats.class\",\"accio\":0} ";
            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            //String json = gson.toJson(datos);
            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //out.println(json);
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
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para insertar un combustible nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("--------------TEST C------------------");
            System.out.println("-----INSERTAR NUEVO COMBUSTIBLE-------");

            // Crear una instancia de la clase y establecer sus valores
            DatosCombustible datos = new DatosCombustible();

            CombustibleObjeto comObj = new CombustibleObjeto();
            comObj.setIdcombustible(5);
            comObj.setNombre("DieselPlus");
            comObj.setPrecio(Float.valueOf("1.90"));
            datos.setCombustible(comObj);
            datos.setAccio(2);
            datos.setClase("Combustible.class");
            datos.setToken(testToken);

            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);

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
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cuarto Test para Actualizar un combustible
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("-----------TEST D-----------------");
            System.out.println("-----ACTUALIZAR COMBUSTIBLE-------");
            // Crear una instancia de la clase y establecer sus valores
            DatosCombustible datos = new DatosCombustible();

            CombustibleObjeto comObj = new CombustibleObjeto();
            comObj.setNombre("Diesel");
            datos.setCombustible(comObj);
            datos.setAccio(3);
            datos.setClase("Combustible.class");
            datos.setToken(testToken);

            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);

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
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Quinto Test para Mostrar Lista de Combustibles Podremos ir viendo como se
     * añaden, eliminan y modifican los combustibles
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("--------------TEST E------------------");
            System.out.println("-----MOSTRAR LISTA DE COMBUSTIBLES-------");

            // Crear una instancia de la clase y establecer sus valores
            DatosCombustible datos = new DatosCombustible();

            CombustibleObjeto comObj = new CombustibleObjeto();
            datos.setCombustible(comObj);
            datos.setAccio(5);
            datos.setClase("Combustible.class");
            datos.setToken(testToken);

            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            String jsonR = data.toString();

            socket.close();

            Assert.assertEquals(jsonR, data);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test para Eliminar un combustible
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("-----------TEST F----------------");
            System.out.println("-----ELIMINAR COMBUSTIBLES-------");

            // Crear una instancia de la clase y establecer sus valores
            DatosCombustible datos = new DatosCombustible();

            CombustibleObjeto comObj = new CombustibleObjeto();
            comObj.setIdcombustible(8);
            comObj.setNombre("DieselPlusExtra");
            datos.setCombustible(comObj);
            datos.setAccio(4);
            datos.setClase("Combustible.class");
            datos.setToken(testToken);

            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);

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
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Septimo Test para Modificar un Combustible
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("---------------TEST G--------------------");
            System.out.println("--------MODIFICAR COMBUSTIBLE------------");

            // Crear una instancia de la clase y establecer sus valores
            DatosCombustible datos = new DatosCombustible();

            CombustibleObjeto comObj = new CombustibleObjeto();
            comObj.setIdcombustible(8);
            comObj.setNombre("Diesel");
            comObj.setPrecio(Float.valueOf("1.70"));
            datos.setCombustible(comObj);
            datos.setAccio(6);
            datos.setClase("Combustible.class");
            datos.setToken(testToken);
            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);

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
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Octavo Test Mostrar un combustible segun id
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("------------------TEST H--------------------");
            System.out.println("-----MOSTRAR LISTA DE UN COMBUSTIBLES-------");

            // Crear una instancia de la clase y establecer sus valores
            DatosCombustible datos = new DatosCombustible();

            CombustibleObjeto comObj = new CombustibleObjeto();
            comObj.setIdcombustible(1);
            datos.setCombustible(comObj);
            datos.setAccio(7);
            datos.setClase("Combustible.class");
            datos.setToken(testToken);

            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            String jsonR = data.toString();

            socket.close();

            Assert.assertEquals(jsonR, data);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
