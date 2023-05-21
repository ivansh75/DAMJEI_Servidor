/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorAcciones;

import TestServidor_Cliente_Resultado_Acciones.ResultadoInsertDeleteUpdateTest;
import TestSocketSSL_Client.SocketSSL_Cliente;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.Encriptador;
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
public class TestModificar {

    private static String testToken;
    private static String HOST = "localhost";
    private static int PUERTO = 8180;
    //instacia del servidor para gestionar el test
    Server_SSLSocket instance = new Server_SSLSocket();
    boolean testCorrecte = true;
    boolean correcte = false;
    //instanciamos los resultados posibles del test
    ResultadoInsertDeleteUpdateTest testResult = new ResultadoInsertDeleteUpdateTest();

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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Tercer Test para modificar un empleado
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestC() throws IOException, SQLException {
        String categoria = "modIficada";
        try {

            System.out.println("----------TEST C-------------");
            System.out.println("-----MODIFICAR EMPLEADO-------");

            // Serializar el objeto en formato Gson
            //String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"12345678s\",\"categoria\":\"modoficada\"},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":6 } ";
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"admin\",\"categoria\":" + categoria + "},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":6 } ";

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
            boolean result = testResult.ComprobarResultadosEmpleado(categoria, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cuarto Test par Modificar Vehiculo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestD() throws IOException, SQLException {
        String fecha_baja = "2023-10-22";
        try {

            System.out.println("--------------TEST D------------------");
            System.out.println("--------MODIFICAR VEHICULO------------");

            // Serializar el objeto en formato Gson
            //String datos = "{\"vehicle\":{\"datosVh\":[],\"matricula\":\"SEA1234\",\"fecha_baja\":\"2023-4-22\"},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":6}";
            String datos = "{\"vehicle\":{\"datosVh\":[],\"matricula\":\"SEA1234\",\"fecha_baja\":" + fecha_baja + "},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            boolean result = testResult.ComprobarResultadosVehiculo(fecha_baja, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Quinto Test par Modificar un Repostaje
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestE() throws IOException, SQLException {
        String conductorid = "1";
        try {

            System.out.println("--------------TEST E------------------");
            System.out.println("--------MODIFICAR REPOSTAR------------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":1,\"conductorid\":1},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":6}";
            String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":1,\"conductorid\":" + conductorid + "},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            boolean result = testResult.ComprobarResultadosRepostar(conductorid, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test par Modificar una Revision
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestF() throws IOException, SQLException {
        String mantenimientoid = "1";
        try {

            System.out.println("--------------TEST F------------------");
            System.out.println("--------MODIFICAR REVISION------------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"revision\":{\"datosRev\":[],\"idrevision\":1,\"mantenimientoid\":1},\"clase\":\"Revisiones.class\",\"token\":" + testToken + ",\"accio\":6}";
            String datos = "{\"revision\":{\"datosRev\":[],\"idrevision\":1,\"mantenimientoid\":" + mantenimientoid + "},\"clase\":\"Revisiones.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            boolean result = testResult.ComprobarResultadosRevison(mantenimientoid, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Septimo Test par Modificar Vehiculo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestG() throws IOException, SQLException {
        String kilometros_mantenimiento = "61000";
        try {

            System.out.println("--------------TEST G------------------");
            System.out.println("--------MODIFICAR MANTENIMIENTO------------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Cambio ruedas\",\"kilometros_mantenimiento\":61000,\"fecha_mantenimiento\":\"2024-4-22\"},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":6}";
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Cambio ruedas\",\"kilometros_mantenimiento\":" + kilometros_mantenimiento + ",\"fecha_mantenimiento\":\"2024-4-22\"},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            boolean result = testResult.ComprobarResultadosManteniento(kilometros_mantenimiento, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Octavo Test para Modificar un Combustible
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestH() throws IOException, SQLException {
        String precio = "1.85";
        
        try {

            System.out.println("---------------TEST H--------------------");
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
     *
     * Noveno Test para modificar un Conductor
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestI() throws IOException, SQLException {
        String fecha_caducidad_carnet = "2035-01-12";
        try {

            System.out.println("----------TEST I-------------");
            System.out.println("-----MODIFICAR CONDUCTOR-------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"conductor\":{\"datosCon\":[],\"idconductor\":1,\"fecha_caducidad_carnet\":\"2035-01-12\"},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":6}";
            String datos = "{\"conductor\":{\"datosCon\":[],\"idconductor\":1,\"fecha_caducidad_carnet\":" + fecha_caducidad_carnet + "},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":6}";

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
            boolean result = testResult.ComprobarResultadosConductor(fecha_caducidad_carnet, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Tercer Test para modificar un empleado que es conductor modificamos la
     * fecha
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestJ() throws IOException, SQLException {
        String fecha_caducidad_carnet = "2045-01-12";
        try {

            System.out.println("----------TEST J-------------");
            System.out.println("-----MODIFICAR CONDUCTOR DESDE EMPLEADO-------");

            // Serializar el objeto en formato Gson
            //String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"12345678f\",\"fecha_caducidad_carnet\":\"2035-01-12\"},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":6 } ";
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"12345678f\",\"fecha_caducidad_carnet\":" + fecha_caducidad_carnet + "},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":6 } ";

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
            boolean result = testResult.ComprobarResultadosConductor(fecha_caducidad_carnet, MODIFICAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
