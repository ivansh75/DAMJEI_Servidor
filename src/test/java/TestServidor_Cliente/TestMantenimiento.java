/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente;

import Test_ClientePruebas.DatosEmpleado;
import Test_ClientePruebas.DatosMantenimiento;
import Test_ClientePruebas.Empleat;
import Test_ClientePruebas.MantenimientoObjeto;
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
public class TestMantenimiento {

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

        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        empleat.setDni("admin");
        empleat.setContrasenya("admin1");
        empleat.setId_empresa(1);
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
     * Tercer Test para insertar un Mantenimiento nuevo
     *
     * @throws IOException
     */
    @Test
    public void TestC() throws IOException {
        boolean correcte = false;

        System.out.println("----------------TEST C-----------------");
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
     * Cuarto Test para Actualizar un Mantenimiento
     *
     * @throws IOException
     */
    @Test
    public void TestD() throws IOException {
        boolean correcte = false;

        System.out.println("-----------TEST D-------------");
        System.out.println("-----ACTUALIZAR MANTENIMIENTO-------");
        // Crear una instancia de la clase y establecer sus valores
        DatosMantenimiento datos = new DatosMantenimiento();
        
        MantenimientoObjeto mObjeto = new MantenimientoObjeto();
        mObjeto.setNombre("Liquido Refrigeración");
        datos.setMantenimiento(mObjeto);
        datos.setAccio(3);
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
     *
     * Quinto Test para Mostrar Lista de Mantenimiento
     * Vemos como esta añadido, modificcado o eliminado el Mantenimiento 
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {
        boolean correcte = false;

        System.out.println("--------------TEST E------------------");
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
     * Sexto Test para Eliminar un Mantenimiento
     *
     * @throws IOException
     */
    @Test
    public void TestF() throws IOException {
        boolean correcte = false;

        System.out.println("----------TEST F------------");
        System.out.println("-----ELIMINAR MANTENIMIENTO-------");
        // Crear una instancia de la clase y establecer sus valores
        DatosMantenimiento datos = new DatosMantenimiento();
        
        MantenimientoObjeto mObjeto = new MantenimientoObjeto();
        mObjeto.setNombre("Liquido Refrigeración");
        datos.setMantenimiento(mObjeto);
        datos.setAccio(4);
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
        TestE();
    }

    /**
     * Septimo Test par Modificar Vehiculo
     * @throws IOException 
     */
    @Test
    public void TestG() throws IOException {
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

}
