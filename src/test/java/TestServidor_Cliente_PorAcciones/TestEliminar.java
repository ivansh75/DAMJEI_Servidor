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
import static damjei_GestionClases.GestionEmpleados.ELIMINAR;
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
public class TestEliminar {

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
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tercer Test para Eliminar un empleado
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestC() throws IOException, SQLException {
        String dni = "12345678j";

        try {

            System.out.println("----------TEST C------------");
            System.out.println("-----ELIMINAR EMPLEADO-------");

            // Serializar el objeto en formato Gson
            //String datos = "{\"empleat\":{\"datos\":[],\"dni\":\"12345678j\"},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":4 } ";
            String datos = "{\"empleat\":{\"datos\":[],\"dni\":" + dni + "},\"clase\":\"Empleats.class\",\"token\":" + testToken + ",\"accio\":4 } ";

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
            boolean result = testResult.ComprobarResultadosEmpleado(dni, ELIMINAR);//obtenemos el resultado de la comprobacion si se ha borrado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cuarto Test para Eliminar un vehiculo
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestD() throws IOException, SQLException {
        String matricula = "1234BMW";
        try {

            System.out.println("----------TEST D------------");
            System.out.println("-----ELIMINAR VEHICULO-------");

            // Serializar el objeto en formato Gson
            //String datos = "{\"vehicle\":{\"datosVh\":[],\"matricula\":\"1234BMW\"},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":4}";
            String datos = "{\"vehicle\":{\"datosVh\":[],\"matricula\":" + matricula + "},\"clase\":\"Vehicle.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            boolean result = testResult.ComprobarResultadosVehiculo(matricula, ELIMINAR);//obtenemos el resultado de la comprobacion si se ha borrado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
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
        String idrepostar = "12";
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
     * Sexto Test para Eliminar una Revision
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestF() throws IOException, SQLException {
        String idrevision = "176";
        try {

            System.out.println("----------TEST F-------------");
            System.out.println("-----ELIMINAR REVISION-------");

            // Serializar el objeto en formato de Gson
            //String datos = "{\"revision\":{\"datosRev\":[],\"idrevision\":29},\"clase\":\"Revisiones.class\",\"token\":" + testToken + ",\"accio\":4}";
            String datos = "{\"revision\":{\"datosRev\":[],\"idrevision\":" + idrevision + "},\"clase\":\"Revisiones.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            boolean result = testResult.ComprobarResultadosRevison(idrevision, ELIMINAR);//obtenemos el resultado de la comprobacion si se ha borrado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Septimo Test para Eliminar un Mantenimiento
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestG() throws IOException, SQLException {
        String nombre = "Líquido Refrigeración";
        try {

            System.out.println("----------TEST G------------");
            System.out.println("-----ELIMINAR MANTENIMIENTO-------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":\"Líquido Refrigeración\"},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":4}";
            String datos = "{\"mantenimiento\":{\"datosMt\":[],\"nombre\":'" + nombre + "'},\"clase\":\"Mantenimiento.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            boolean result = testResult.ComprobarResultadosManteniento(nombre, ELIMINAR);//obtenemos el resultado de la comprobacion si se ha borrado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Octavo Test para Eliminar un combustible
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestH() throws IOException, SQLException {
        String nombre = "DieselPlus";
        try {

            System.out.println("-----------TEST H----------------");
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
     * Noveno Test para Eliminar un empleado
     *
     * @throws IOException
     * @throws java.sql.SQLException
     */
    @Test
    public void TestI() throws IOException, SQLException {
        String dni = "12345678b";
        try {

            System.out.println("----------TEST I------------");
            System.out.println("-----ELIMINAR EMPLEADO CONDUCTOR-------");

            // Serializar el objeto en formato JSON usando Gson
            //String datos = "{\"conductor\":{\"datosCon\":[],\"dni\":\"12345678b\"},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":4}";
            String datos = "{\"conductor\":{\"datosCon\":[],\"dni\":" + dni + "},\"clase\":\"Conductor.class\",\"token\":" + testToken + ",\"accio\":4}";

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
            boolean result = testResult.ComprobarResultadosEmpleado(dni, ELIMINAR);//obtenemos el resultado de la comprobacion si se a borrado result=true  
            Assert.assertEquals(result, correcte);

        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestEliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
