/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorGestionesClases;

import Test_ClientePruebas.DatosEmpleado;
import Test_ClientePruebas.DatosRepostar;
import Test_ClientePruebas.Empleat;
import Test_ClientePruebas.RepostarObjeto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import damjei_ConexionCliente.Encriptador;
import Servidor.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
    //instacia del servidor para gestionar el test
    Server instance = new Server();
    boolean testCorrecte = true;

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
        Socket socket = new Socket("localhost", 8180);
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

    }

    /**
     * Tercer Test para insertar un Repostaje nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST C-----------------");
        System.out.println("-----INSERTAR NUEVO REPOSTAR---------");

        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();

        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        repObjet.setFecha_repostar("2023-02-03");
        repObjet.setImporte_repostar(Float.parseFloat("100"));
        repObjet.setKilometros_repostar(Float.parseFloat("1700"));
        repObjet.setCombustibleid(1);
        repObjet.setVehiculoid(1);
        repObjet.setConductorid(1);
        repObjet.setLitros(Float.parseFloat("30"));
        datos.setRepostar(repObjet);
        datos.setAccio(2);
        datos.setClase("Repostar.class");
        datos.setToken(testToken);

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
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

    }

    /**
     * Cuarto Test para Actualizar un Repostaje
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {
        boolean correcte = false;

        System.out.println("------------TEST D-------------");
        System.out.println("-----ACTUALIZAR REPOSTAR-------");
        // Crear una instancia de la clase y establecer sus valores
        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();

        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        datos.setRepostar(repObjet);
        datos.setAccio(3);
        datos.setClase("Repostar.class");
        datos.setToken(testToken);

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
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

    }

    /**
     *
     * Quinto Test para Mostrar Lista de Repostar Vemos como esta añadido,
     * modificcado o eliminado el Repostar
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST E-----------------");
        System.out.println("-----MOSTRAR LISTA DE REPOSTAR-------");
        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();

        RepostarObjeto repObjet = new RepostarObjeto();
        datos.setRepostar(repObjet);
        datos.setAccio(5);
        datos.setClase("Repostar.class");
        datos.setToken(testToken);

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        String jsonR = data.toString();

        socket.close();

        Assert.assertEquals(jsonR, data);

    }

    /**
     * Sexto Test para Eliminar un Repostaje
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {
        boolean correcte = false;

        System.out.println("----------TEST F-------------");
        System.out.println("-----ELIMINAR REPOSTAR-------");
        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();

        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(5);
        datos.setRepostar(repObjet);
        datos.setAccio(4);
        datos.setClase("Repostar.class");
        datos.setToken(testToken);

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
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
    }

    /**
     * Septimo Test par Modificar un Repostaje
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST G------------------");
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
        Socket socket = new Socket("localhost", 8180);
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
    }

    /**
     * Octavo Test Mostrar un Repostaje por su id
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST H---------------------");
        System.out.println("-----MOSTRAR LISTA DE UN REPOSTAJE-------");
        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();

        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        datos.setRepostar(repObjet);
        datos.setAccio(7);
        datos.setClase("Repostar.class");
        datos.setToken(testToken);

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        String jsonR = data.toString();

        socket.close();

        Assert.assertEquals(jsonR, data);

    }

}
