/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorAcciones;

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
public class TestInsertar {

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
     * Segundo Test Iniciamos primero usuario empleado como Administrador
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
        empleat.setContraseña("admin1");
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
     * Tercer Test para insertar un empleado nuevo como administrador
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {
        boolean correcte = false;

        System.out.println("----------------------TEST C-------------------------");
        System.out.println("-----INSERTAR EMPLEADO NUEVO COMO ADMINISTRADOR-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        //ArrayList<String> list = new ArrayList<>();
        Empleat empleat = new Empleat();
        empleat.setIdempleado(8);
        empleat.setNombre("Joan");
        empleat.setApellidos("Ramon Serra");
        empleat.setDni("12345678j");
        empleat.setCategoria("rrhh");
        empleat.setId_empresa(1);
        empleat.setContraseña("12345678j");
        empleat.setAdministrador(true);
        datos.setEmpleat(empleat);
        datos.setAccio(2);
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

        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        JsonElement correctaJson = jsonObject.get("correcte");
        if (correctaJson != null && !correctaJson.isJsonNull()) {
            correcte = correctaJson.getAsBoolean();

        }

        socket.close();

        Assert.assertEquals(testCorrecte, correcte);

    }

    /**
     * Cuarto Test para insertar un empleado nuevo conductor
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {
        boolean correcte = false;

        System.out.println("----------------------TEST D-------------------------");
        System.out.println("-----INSERTAR EMPLEADO NUEVO COMO CONDUCTOR-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        //ArrayList<String> list = new ArrayList<>();
        Empleat empleat = new Empleat();
        empleat.setIdempleado(8);
        empleat.setNombre("Ivan");
        empleat.setApellidos("Sánchez Sánchez");
        empleat.setDni("12345678b");
        empleat.setCategoria("conductor");
        empleat.setFecha_carnet("1995-1-20");
        empleat.setFecha_caducidad_carnet("2005-1-30");
        empleat.setId_empresa(1);
        empleat.setContraseña("ivansan");
        empleat.setAdministrador(false);
        datos.setEmpleat(empleat);
        datos.setAccio(2);
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

        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        JsonElement correctaJson = jsonObject.get("correcte");
        if (correctaJson != null && !correctaJson.isJsonNull()) {
            correcte = correctaJson.getAsBoolean();

        }

        socket.close();

        Assert.assertEquals(testCorrecte, correcte);

    }

    /**
     * Cuarto Test para insertar un vehiculo nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {
        boolean correcte = false;

        System.out.println("-----------TEST E-----------------");
        System.out.println("-----INSERTAR NUEVO VEHICULO-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosVehicle datos = new DatosVehicle();
        Vehicle vehicle = new Vehicle();
        vehicle.setIdvehiculo(8);
        vehicle.setMarca("BMW");
        vehicle.setModelo("Seri10");
        vehicle.setMatricula("1234BMW");
        vehicle.setKilometros_alta(Float.valueOf(1000));
        vehicle.setFecha_alta("2023-02-09");
        vehicle.setEmpresaid(1);
        vehicle.setKilometros_actuales(Float.valueOf(1000));
        datos.setVehicle(vehicle);
        datos.setAccio(2);
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

        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        JsonElement correctaJson = jsonObject.get("correcte");
        if (correctaJson != null && !correctaJson.isJsonNull()) {
            correcte = correctaJson.getAsBoolean();

        }

        socket.close();

        Assert.assertEquals(testCorrecte, correcte);

    }

    /**
     * Sexto Test para insertar un Repostaje nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST F-----------------");
        System.out.println("-----INSERTAR NUEVO REPOSTAR---------");

        // Crear una instancia de la clase y establecer sus valores
        DatosRepostar datos = new DatosRepostar();

        RepostarObjeto repObjet = new RepostarObjeto();
        repObjet.setIdrepostar(1);
        repObjet.setFecha_repostar("2023-02-03");
        repObjet.setImporte_repostar(Float.valueOf("100"));
        repObjet.setKilometros_repostar(Float.valueOf("1700"));
        repObjet.setCombustibleid(2);
        repObjet.setVehiculoid(2);
        repObjet.setConductorid(3);
        repObjet.setLitros(Float.valueOf("30"));
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
     * Septimo Test para insertar una Revision nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestG() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST G-----------------");
        System.out.println("-----INSERTAR NUEVA REVISION---------");
        // Crear una instancia de la clase y establecer sus valores
        DatosRevisiones datos = new DatosRevisiones();

        RevisionesObjeto revObjet = new RevisionesObjeto();
        revObjet.setIdrevision(1);
        revObjet.setFecha_revision("2023-02-03");
        revObjet.setKilometros_revision(Float.valueOf("50000"));
        revObjet.setVehiculoid(1);
        revObjet.setMantenimientoid(1);
        revObjet.setEstado_revision(false);
        datos.setRevision(revObjet);
        datos.setAccio(2);
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

        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        JsonElement correctaJson = jsonObject.get("correcte");
        if (correctaJson != null && !correctaJson.isJsonNull()) {
            correcte = correctaJson.getAsBoolean();

        }

        socket.close();

        Assert.assertEquals(testCorrecte, correcte);

    }

    /**
     * Octavo Test para insertar un Mantenimiento nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestH() throws IOException {
        boolean correcte = false;

        System.out.println("----------------TEST H-----------------");
        System.out.println("-----INSERTAR NUEVO MANTENIMIENTO-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosMantenimiento datos = new DatosMantenimiento();

        MantenimientoObjeto mObjeto = new MantenimientoObjeto();
        mObjeto.setIdmantenimiento(8);
        mObjeto.setNombre("Liquido Refrigeración");
        datos.setMantenimiento(mObjeto);
        datos.setAccio(2);
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

        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        JsonElement correctaJson = jsonObject.get("correcte");
        if (correctaJson != null && !correctaJson.isJsonNull()) {
            correcte = correctaJson.getAsBoolean();

        }

        socket.close();

        Assert.assertEquals(testCorrecte, correcte);

    }

    /**
     * Noveno Test para insertar un combustible nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestI() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST I------------------");
        System.out.println("-----INSERTAR NUEVO COMBUSTIBLE-------");

        // Crear una instancia de la clase y establecer sus valores
        DatosCombustible datos = new DatosCombustible();

        CombustibleObjeto comObj = new CombustibleObjeto();
        comObj.setIdcombustible(8);
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

}
