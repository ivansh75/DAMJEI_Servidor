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
import static damjei_GestionClases.GestionEmpleados.INSERTAR;
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
public class TestInsertar {

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
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para insertar un empleado nuevo como administrador
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestC() throws IOException, SQLException {
        String dni = "12345678j";
        try {

            System.out.println("----------------------TEST C-------------------------");
            System.out.println("-----INSERTAR EMPLEADO NUEVO COMO ADMINISTRADOR-------");

            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("12345678j");
            // Serializar el objeto en formato de Gson
            //String datos = "{\"empleat\":{\"datos\":[],\"idempleado\":8,\"nombre\":\"Joan\",\"apellidos\":\"Ramon Serra\",\"dni\":\"12345678j\",\"categoria\":\"rrhh\",\"empresaid\":1,\"contraseña\":" + contraseña + ",\"administrador\":true},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"empleat\":{\"datos\":[],\"idempleado\":8,\"nombre\":\"Joan\",\"apellidos\":\"Ramon Serra\",\"dni\":" + dni + ",\"categoria\":\"rrhh\",\"empresaid\":1,\"contraseña\":" + contraseña + ",\"administrador\":true},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            boolean result = testResult.ComprobarResultadosEmpleado(dni, INSERTAR);//obtenemos el resultado de la comprobacion si se ha isertado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cuarto Test para insertar un empleado nuevo conductor
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestD() throws IOException, SQLException {
        String dni = "12345678b";
        try {

            System.out.println("----------------------TEST D-------------------------");
            System.out.println("-----INSERTAR EMPLEADO NUEVO COMO CONDUCTOR-------");

            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("ivansan");
            // Serializar el objeto en formato de Gson
            String datos = "{\"empleat\":{\"datos\":[],\"idempleado\":8,\"nombre\":\"Ivan\",\"apellidos\":\"Sánchez Sánchez\",\"dni\":" + dni + ",\"categoria\":\"conductor\",\"fecha_carnet\":\"1995-1-20\",\"fecha_caducidad_carnet\":\"2005-1-30\",\"empresaid\":1,\"contraseña\":" + contraseña + ",\"administrador\":false},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            boolean result = testResult.ComprobarResultadosEmpleado(dni, INSERTAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cuarto Test para insertar un vehiculo nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestE() throws IOException, SQLException {
        String matricula = "1234BMW";
        try {

            System.out.println("-----------TEST E-----------------");
            System.out.println("-----INSERTAR NUEVO VEHICULO-------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"vehicle\":{\"datosVh\":[],\"idvehiculo\":8,\"marca\":\"BMW\",\"modelo\":\"Seri10\",\"matricula\":\"1234BMW\",\"kilometros_alta\":1000.0,\"fecha_alta\":\"2023-02-09\",\"conductorid\":0,\"empresaid\":1,\"kilometros_actuales\":1000.0},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"vehicle\":{\"datosVh\":[],\"idvehiculo\":8,\"marca\":\"BMW\",\"modelo\":\"Seri10\",\"matricula\":" + matricula + ",\"kilometros_alta\":1000.0,\"fecha_alta\":\"2023-02-09\",\"conductorid\":0,\"empresaid\":1,\"kilometros_actuales\":1000.0},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            boolean result = testResult.ComprobarResultadosVehiculo(matricula, INSERTAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test para insertar un Repostaje nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestF() throws IOException, SQLException {
        String idrepostar = "1";
        try {

            System.out.println("--------------TEST F-----------------");
            System.out.println("-----INSERTAR NUEVO REPOSTAR---------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":1,\"fecha_repostar\":\"2023-02-03\",\"importe_repostar\":100.0,\"kilometros_repostar\":1700.0,\"combustibleid\":2,\"vehiculoid\":1,\"conductorid\":1,\"litros\":30.0},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":" + idrepostar + ",\"fecha_repostar\":\"2023-02-03\",\"importe_repostar\":100.0,\"kilometros_repostar\":1700.0,\"combustibleid\":2,\"vehiculoid\":1,\"conductorid\":1,\"litros\":30.0},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            boolean result = testResult.ComprobarResultadosRepostar(idrepostar, INSERTAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Septimo Test para insertar una Revision nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestG() throws IOException, SQLException {
        String idrevisiones = "1";
        try {

            System.out.println("--------------TEST G-----------------");
            System.out.println("-----INSERTAR NUEVA REVISION---------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"revision\":{\"datosRev\":[],\"idrevision\":1,\"fecha_revision\":\"2023-02-03\",\"kilometros_revision\":50000.0,\"vehiculoid\":1,\"mantenimientoid\":1,\"estado_revision\":false},\"clase\":\"Revisiones.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"revision\":{\"datosRev\":[],\"idrevision\":" + idrevisiones + ",\"fecha_revision\":\"2023-02-03\",\"kilometros_revision\":50000.0,\"vehiculoid\":1,\"mantenimientoid\":1,\"estado_revision\":false},\"clase\":\"Revisiones.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            boolean result = testResult.ComprobarResultadosRevison(idrevisiones, INSERTAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Octavo Test para insertar un Mantenimiento nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestH() throws IOException, SQLException {
        String nombre = "Líquido Refrigeración";
        try {

            System.out.println("----------------TEST H-----------------");
            System.out.println("-----INSERTAR NUEVO MANTENIMIENTO-------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Líquido Refrigeración\",\"kilometros_mantenimiento\":60000},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":'" + nombre + "',\"kilometros_mantenimiento\":60000},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":2}";

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
            boolean result = testResult.ComprobarResultadosManteniento(nombre, INSERTAR);//obtenemos el resultado de la comprobacion si se ha insertado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestInsertar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Noveno Test para insertar un combustible nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestI() throws IOException, SQLException {
        String nombre = "DieselPlus";
        try {

            System.out.println("--------------TEST I------------------");
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

}
