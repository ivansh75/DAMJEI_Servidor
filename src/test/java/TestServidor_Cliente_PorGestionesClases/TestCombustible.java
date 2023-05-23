/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorGestionesClases;

import TestServidor_Cliente_PorAcciones.TestEliminar;
import TestServidor_Cliente_PorAcciones.TestInsertar;
import TestServidor_Cliente_PorAcciones.TestListar;
import TestServidor_Cliente_PorAcciones.TestListarId;
import TestServidor_Cliente_PorAcciones.TestModificar;
import TestServidor_Cliente_Resultado_Acciones.ResultadoInsertDeleteUpdateTest;
import TestServidor_Cliente_Resultado_Acciones.ResultadoListarListarIdTest;
import TestSocketSSL_Client.SocketSSL_Cliente;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.Encriptador;
import static damjei_GestionClases.GestionCombustible.COMBUSTIBLE;
import static damjei_GestionClases.GestionEmpleados.ELIMINAR;
import static damjei_GestionClases.GestionEmpleados.INSERTAR;
import static damjei_GestionClases.GestionEmpleados.MODIFICAR;
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
import java.sql.SQLException;
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
    boolean correcte = false;
    //instanciamos los resultados posibles del test
    ResultadoInsertDeleteUpdateTest testResult = new ResultadoInsertDeleteUpdateTest();
    ResultadoListarListarIdTest testResultList = new ResultadoListarListarIdTest();

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
            Logger.getLogger(TestCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercero Test para insertar un combustible nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestC() throws IOException, SQLException {
        String nombre = "DieselPlus";
        try {

            System.out.println("--------------TEST C------------------");
            System.out.println("-----INSERTAR NUEVO COMBUSTIBLE-------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"combustible\":{\"datosCom\":[],\"idcombustible\":8,\"nombre\":\"DieselPlus\",\"precio\":1.9},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"combustible\":{\"datosCom\":[],\"idcombustible\":8,\"nombre\":" + nombre + ",\"precio\":1.9},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            boolean result = testResult.ComprobarResultadosCombustible(nombre, INSERTAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Cuarot Test para Mostrar Lista de Combustibles Podremos ir viendo como se
     * añaden, eliminan y modifican los combustibles
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestD() throws IOException, SQLException {

        try {

            System.out.println("--------------TEST D------------------");
            System.out.println("-----MOSTRAR LISTA DE COMBUSTIBLES-------");

            // Serializar el objeto en formato de Gson
            String datos = "{\"combustible\":{\"datosCom\":[]},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":5}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            int length = data.length();

            socket.close();

            int result = testResultList.ComprobarListarCombustible(COMBUSTIBLE);//obtenemos la medida del String              
            Assert.assertEquals(result, length);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestListar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Quinto Test para Eliminar un combustible
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestE() throws IOException, SQLException {
        String nombre = "DieselPlusPlus";
        try {

            System.out.println("-----------TEST E----------------");
            System.out.println("-----ELIMINAR COMBUSTIBLES-------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"combustible\":{\"datosCom\":[],\"nombre\":\"DieselPlus\"},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":4}";
            String datos = "{\"combustible\":{\"datosCom\":[],\"nombre\":" + nombre + "},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            boolean result = testResult.ComprobarResultadosCombustible(nombre, ELIMINAR);//obtenemos el resultado de la comprobacion si se ha borrado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test para Modificar un Combustible
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestF() throws IOException, SQLException {
        String precio = "1.75";

        try {

            System.out.println("---------------TEST F--------------------");
            System.out.println("--------MODIFICAR COMBUSTIBLE------------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"combustible\":{\"datosCom\":[],\"nombre\":\"Diesel\",\"precio\":1.80},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":6}";
            String datos = "{\"combustible\":{\"datosCom\":[],\"nombre\":\"Diesel\",\"precio\":" + precio + "},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            boolean result = testResult.ComprobarResultadosCombustible(precio, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Septimo Test Mostrar un combustible segun id
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestH() throws IOException, SQLException {
        String idcombustible = "1";
        try {

            System.out.println("------------------TEST H--------------------");
            System.out.println("-----MOSTRAR LISTA DE UN COMBUSTIBLES-------");

            // Serializar el objeto en formato de Gson
            String datos = "{\"combustible\":{\"datosCom\":[],\"idcombustible\":1},\"clase\":\"Combustible.class\",\"token\":" + testToken + ",\"accio\":7}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            int length = data.length();

            socket.close();

            int result = testResultList.ComprobarListarCombustible(idcombustible);//obtenemos la medida del String              
            Assert.assertEquals(result, length);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestListarId.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
