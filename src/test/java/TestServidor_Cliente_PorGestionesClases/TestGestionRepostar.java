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
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.Encriptador;
import TestSocketSSL_Client.SocketSSL_Cliente;
import static damjei_GestionClases.GestionEmpleados.ELIMINAR;
import static damjei_GestionClases.GestionEmpleados.INSERTAR;
import static damjei_GestionClases.GestionEmpleados.MODIFICAR;
import static damjei_GestionClases.GestionRepostar.REPOSTAR;
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
public class TestGestionRepostar {

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
            Logger.getLogger(TestGestionRepostar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para insertar un Repostaje nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */ 
    @Test
    public void TestC() throws IOException, SQLException {
        String idrepostar = "1";
        try {

            System.out.println("--------------TEST C-----------------");
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
     *
     * Cuarto Test para Mostrar Lista de Repostar Vemos como esta añadido,
     * modificcado o eliminado el Repostar
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestD() throws IOException, SQLException {

        try {

            System.out.println("--------------TEST D-----------------");
            System.out.println("-----MOSTRAR LISTA DE REPOSTAR-------");

            // Serializar el objeto en formato de Gson
            String datos = "{\"repostar\":{\"datosRep\":[]},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":5}";

            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            int length = data.length();

            socket.close();

            int result = testResultList.ComprobarListarRepostar(REPOSTAR);//obtenemos la medida del String              
            Assert.assertEquals(result, length);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestListar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Quinto Test para Eliminar un Repostaje
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestE() throws IOException, SQLException {
        String idrepostar = "49";
        try {

            System.out.println("----------TEST E-------------");
            System.out.println("-----ELIMINAR REPOSTAR-------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":8},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":4}";
            String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":" + idrepostar + "},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            boolean result = testResult.ComprobarResultadosRepostar(idrepostar, ELIMINAR);//obtenemos el resultado de la comprobacion si se ha borrado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test par Modificar un Repostaje
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */ 
    @Test
    public void TestF() throws IOException, SQLException {
        String conductorid = "3";
        try {

            System.out.println("--------------TEST F------------------");
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
     * Septimo Test Mostrar un Repostaje por su id
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */   
    @Test
    public void TestG() throws IOException, SQLException {
        String idrepostar = "1";
        try {

            System.out.println("--------------TEST G---------------------");
            System.out.println("-----MOSTRAR LISTA DE UN REPOSTAJE-------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":1},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":7}";
           String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":" + idrepostar + "},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":7}";
           
            // Enviar los datos al servidor usando una conexión de socket
            SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(datos);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            int length = data.length();

            socket.close();

            int result = testResultList.ComprobarListarRepostar(idrepostar);//obtenemos la medida del String              
            Assert.assertEquals(result, length);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestListarId.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Octavo Test para insertar un Repostaje nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */ 
    @Test
    public void TestH() throws IOException, SQLException {
        String idrepostar = "5";
        String kilometros_repostar = "90000";
        try {

            System.out.println("--------------------------------------------TEST H----------------------------------------------");
            System.out.println("-----INSERTAR NUEVO REPOSTAR Y DEBE AVISAR DE REVISIONES Y MANTENIMIENTOS PENDIENTES KM---------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":1,\"fecha_repostar\":\"2023-02-03\",\"importe_repostar\":100.0,\"kilometros_repostar\":1700.0,\"combustibleid\":2,\"vehiculoid\":1,\"conductorid\":1,\"litros\":30.0},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":" + idrepostar + ",\"fecha_repostar\":\"2023-02-03\",\"importe_repostar\":100.0,\"kilometros_repostar\":" + kilometros_repostar + ",\"combustibleid\":2,\"vehiculoid\":3,\"conductorid\":1,\"litros\":30.0},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":2}";

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
     * Noveno Test para insertar un Repostaje nuevo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */   
    @Test
    public void TestI() throws IOException, SQLException {
        String idrepostar = "5";
        String fecha_repostar = "2027-02-03";
        try {

            System.out.println("--------------------------------------------TEST I----------------------------------------------");
            System.out.println("-----INSERTAR NUEVO REPOSTAR Y DEBE AVISAR DE REVISIONES Y MANTENIMIENTOS PENDIENTES FECHA---------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":1,\"fecha_repostar\":\"2023-02-03\",\"importe_repostar\":100.0,\"kilometros_repostar\":1700.0,\"combustibleid\":2,\"vehiculoid\":1,\"conductorid\":1,\"litros\":30.0},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":2}";
            String datos = "{\"repostar\":{\"datosRep\":[],\"idrepostar\":" + idrepostar + ",\"fecha_repostar\":" + fecha_repostar + ",\"importe_repostar\":100.0,\"kilometros_repostar\":1700.0,\"combustibleid\":1,\"vehiculoid\":3,\"conductorid\":1,\"litros\":30.0},\"clase\":\"Repostar.class\",\"token\":" + testToken + ",\"accio\":2}";

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

}
