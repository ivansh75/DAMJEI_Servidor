/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorGestionesClases;

import TestServidor_Cliente_PorAcciones.TestModificar;
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
public class TestGestionEmpleado {

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

            System.out.println("--------------------TEST B-----------------------");
            System.out.println("-----INICIAR SESION EMPLEADO ADMINISTRADOR-------");

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
            Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para insertar un empleado nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {

        try {

            System.out.println("----------------------TEST C-------------------------");
            System.out.println("-----INSERTAR EMPLEADO NUEVO COMO CONDUCTOR-------");

            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("ivansan");
            // Serializar el objeto en formato de Gson
            String datos = "{\"empleat\":{\"datos\":[],\"idempleado\":8,\"nombre\":\"Ivan\",\"apellidos\":\"SÃ¡nchez SÃ¡nchez\",\"dni\":\"12345678b\",\"categoria\":\"conductor\",\"fecha_carnet\":\"1995-1-20\",\"fecha_caducidad_carnet\":\"2005-1-30\",\"empresaid\":1,\"contraseña\":" + contraseña + ",\"administrador\":false},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cuarto Test para insertar un empleado nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {

        try {

            System.out.println("----------------------TEST D-------------------------");
            System.out.println("-----INSERTAR EMPLEADO NUEVO COMO ADMINISTRADOR-------");

            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("12345678j");
            // Serializar el objeto en formato de Gson
            String datos = "{\"empleat\":{\"datos\":[],\"idempleado\":8,\"nombre\":\"Joan\",\"apellidos\":\"Ramon Serra\",\"dni\":\"12345678j\",\"categoria\":\"rrhh\",\"empresaid\":1,\"contraseña\":" + contraseña + ",\"administrador\":true},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Sexto Test para Mostrar Lista de empleados. Vemos como esta añadido el
     * empleado nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {

        try {

            System.out.println("--------------TEST F------------------");
            System.out.println("-----MOSTRAR LISTA DE EMPLEADOS-------");

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
            Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Septimo Test para Eliminar un empleado
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {

        try {

            System.out.println("----------TEST G------------");
            System.out.println("-----ELIMINAR EMPLEADO-------");

            // Serializar el objeto en formato Gson
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"12345678j\"},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":4 } ";

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
            TestF();
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Octavo Test para modificar un empleado
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {

        try {

            System.out.println("----------TEST H-------------");
            System.out.println("-----MODIFICAR EMPLEADO-------");

            // Serializar el objeto en formato Gson
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"12345678s\",\"categoria\":\"modoficada\"},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":6 } ";

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
            TestF();
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Noveno Test para Mostrar Lista del empleado. Vemos como esta añadido el
     * empleado nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestI() throws IOException {

        try {

            System.out.println("----------------TEST I------------------");
            System.out.println("-----MOSTRAR LISTA DE UN EMPLEADO-------");

            // Serializar el objeto en formato Gson
            String datos = "{\"empleat\":{\"datos\":[],\"idempleado\":1},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":7} ";

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
            Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
      /**
     *
     * Tercer Test para modificar un empleado que es conductor modificamos la fecha
     *
     * @throws IOException
     */
    @Test
    public void TestJ() throws IOException {

        try {

            System.out.println("----------TEST J-------------");
            System.out.println("-----MODIFICAR CONDUCTOR DESDE EMPLEADO-------");

            // Serializar el objeto en formato Gson
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"12345678f\",\"fecha_caducidad_carnet\":\"2035-01-12\"},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":6 } ";

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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
