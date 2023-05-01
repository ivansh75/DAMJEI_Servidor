/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorAcciones;

import TestSocketSSL_Client.SocketSSL_Cliente;
import Test_ClientePruebas.CombustibleObjeto;
import Test_ClientePruebas.ConductorObjeto;
import Test_ClientePruebas.DatosCombustible;
import Test_ClientePruebas.DatosConductor;
import Test_ClientePruebas.DatosEmpleado;
import Test_ClientePruebas.DatosMantenimiento;
import Test_ClientePruebas.DatosRepostar;
import Test_ClientePruebas.DatosRevisiones;
import Test_ClientePruebas.DatosVehicle;
import Test_ClientePruebas.Empleat;
import Test_ClientePruebas.MantenimientoObjeto;
import Test_ClientePruebas.RepostarObjeto;
import Test_ClientePruebas.RevisionesObjeto;
import Test_ClientePruebas.Vehicle;
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
            boolean correcte = false;

            System.out.println("--------------------TEST B-----------------------");
            System.out.println("-----INICIAR SESION EMPLEADO ADMINISTRADOR-------");
            /*
           // Crear una instancia de la clase y establecer sus valores
           DatosEmpleado datos = new DatosEmpleado();
           Empleat empleat = new Empleat();
           empleat.setDni("admin");
           empleat.setContraseña("admin1");
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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Tercer Test para modificar un empleado
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {
        try {
            boolean correcte = false;
            System.out.println("----------TEST C-------------");
            System.out.println("-----MODIFICAR EMPLEADO-------");
            // Crear una instancia de la clase y establecer sus valores
            DatosEmpleado datos = new DatosEmpleado();
            Empleat empleat = new Empleat();
            empleat.setDni("admin");
            empleat.setCategoria("modificada");
            datos.setEmpleat(empleat);
            datos.setAccio(6);
            datos.setClase("Empleats.class");
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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cuarto Test par Modificar Vehiculo
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("--------------TEST D------------------");
            System.out.println("--------MODIFICAR VEHICULO------------");

            // Crear una instancia de la clase y establecer sus valores
            DatosVehicle datos = new DatosVehicle();
            Vehicle vehicle = new Vehicle();
            vehicle.setMatricula("SEA1234");
            vehicle.setFecha_baja("2023-4-22");
            datos.setVehicle(vehicle);
            datos.setAccio(6);
            datos.setClase("Vehicle.class");
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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Quinto Test par Modificar un Repostaje
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("--------------TEST E------------------");
            System.out.println("--------MODIFICAR REPOSTAR------------");
            // Crear una instancia de la clase y establecer sus valores
            DatosRepostar datos = new DatosRepostar();

            RepostarObjeto repObjet = new RepostarObjeto();
            repObjet.setIdrepostar(2);
            repObjet.setConductorid(3);
            datos.setRepostar(repObjet);
            datos.setAccio(6);
            datos.setClase("Repostar.class");
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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sexto Test par Modificar una Revision
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("--------------TEST F------------------");
            System.out.println("--------MODIFICAR REVISION------------");
            // Crear una instancia de la clase y establecer sus valores
            DatosRevisiones datos = new DatosRevisiones();

            RevisionesObjeto revObjet = new RevisionesObjeto();
            revObjet.setIdrevision(3);
            revObjet.setMantenimientoid(1);
            datos.setRevision(revObjet);
            datos.setAccio(6);
            datos.setClase("Revisiones.class");

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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
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
            boolean correcte = false;

            System.out.println("--------------TEST G------------------");
            System.out.println("--------MODIFICAR MANTENIMIENTO------------");
            // Crear una instancia de la clase y establecer sus valores
            DatosMantenimiento datos = new DatosMantenimiento();

            MantenimientoObjeto mObjeto = new MantenimientoObjeto();
            mObjeto.setNombre("itv");
            mObjeto.setKilometros_mantenimiento(Float.parseFloat("60000"));
            mObjeto.setFecha_mantenimiento("2024-12-2");
            datos.setMantenimiento(mObjeto);
            datos.setAccio(6);
            datos.setClase("Mantenimiento.class");
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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Octavo Test para Modificar un Combustible
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {
        try {
            boolean correcte = false;

            System.out.println("---------------TEST H--------------------");
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
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Noveno Test para modificar un Conductor
     *
     * @throws IOException
     */
    @Test
    public void TestI() throws IOException {
        try {
            boolean correcte = false;
            System.out.println("----------TEST I-------------");
            System.out.println("-----MODIFICAR CONDUCTOR-------");
            // Crear una instancia de la clase y establecer sus valores
            DatosConductor datos = new DatosConductor();
            ConductorObjeto conObject = new ConductorObjeto();
            conObject.setIdconductor(2);
            conObject.setFecha_caducidad_carnet("2035-01-12");
            datos.setConductor(conObject);
            datos.setAccio(6);
            datos.setClase("Conductor.class");
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
            Logger.getLogger(TestModificar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
