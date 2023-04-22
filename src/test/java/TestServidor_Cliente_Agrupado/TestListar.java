/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_Agrupado;

import Test_ClientePruebas.CombustibleObjeto;
import Test_ClientePruebas.DatosCombustible;
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
import damjei_Servidor_Run.Server;
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
public class TestListar {

    private static String testToken;
    //instacia del servidor para gestionar el test
    Server instance = new Server();
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
        boolean correcte = false;

        System.out.println("--------------------TEST B-----------------------");
        System.out.println("-----INICIAR SESION EMPLEADO ADMINISTRADOR-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        empleat.setDni("admin");
        empleat.setContrasenya("admin1");
        datos.setEmpleat(empleat);
        datos.setAccio(0);
        datos.setClase("Empleats.class");

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
     *
     * Tercer Test para Mostrar Lista de empleados. Vemos como esta añadido el
     * empleado nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST C------------------");
        System.out.println("-----MOSTRAR LISTA DE EMPLEADOS-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        datos.setEmpleat(empleat);
        datos.setAccio(5);
        datos.setClase("Empleats.class");
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
     *
     * Cuarto Test para Mostrar Lista de vehiculos Vemos como esta añadido el
     * vehiculo nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST D------------------");
        System.out.println("-----MOSTRAR LISTA DE VEHICULOS-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosVehicle datos = new DatosVehicle();
        Vehicle vehicle = new Vehicle();
        datos.setVehicle(vehicle);
        datos.setAccio(5);
        datos.setClase("Vehicle.class");
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
     *
     * Sexto Test para Mostrar Lista de Revision Vemos como esta añadido,
     * modificcado o eliminado el Repostar
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST F-----------------");
        System.out.println("-----MOSTRAR LISTA DE REVISIONES-------");
        // Crear una instancia de la clase y establecer sus valores
        DatosRevisiones datos = new DatosRevisiones();

        RevisionesObjeto revObjet = new RevisionesObjeto();
        datos.setRevision(revObjet);
        datos.setAccio(5);
        datos.setClase("Revisiones.class");
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
     *
     * Septimo Test para Mostrar Lista de Mantenimiento Vemos como esta añadido,
     * modificcado o eliminado el Mantenimiento
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST G------------------");
        System.out.println("-----MOSTRAR LISTA DE MANTENIMIENTO-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosMantenimiento datos = new DatosMantenimiento();

        MantenimientoObjeto mObjeto = new MantenimientoObjeto();
        datos.setMantenimiento(mObjeto);
        datos.setAccio(5);
        datos.setClase("Mantenimiento.class");
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
     *
     * Octavo Test para Mostrar Lista de Combustibles Podremos ir viendo como se
     * añaden, eliminan y modifican los combustibles
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST H------------------");
        System.out.println("-----MOSTRAR LISTA DE COMBUSTIBLES-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosCombustible datos = new DatosCombustible();

        CombustibleObjeto comObj = new CombustibleObjeto();
        comObj.setNombre("Diesel");
        datos.setCombustible(comObj);
        datos.setAccio(5);
        datos.setClase("Combustible.class");
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
