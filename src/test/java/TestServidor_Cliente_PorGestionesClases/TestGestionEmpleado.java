/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_PorGestionesClases;

import TestSocketSSL_Client.SocketSSL_Cliente;
import Test_ClientePruebas.DatosEmpleado;
import Test_ClientePruebas.Empleat;
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
              
              // Crear una instancia de la clase y establecer sus valores
              DatosEmpleado datos = new DatosEmpleado();
              //ArrayList<String> list = new ArrayList<>();
              Empleat empleat = new Empleat();
              empleat.setIdempleado(8);
              empleat.setNombre("Joan");
              empleat.setApellidos("Ramon Serra");
              empleat.setDni("12345678j");
              empleat.setCategoria("conductor");
              empleat.setFecha_carnet("1995-1-20");
              empleat.setFecha_caducidad_carnet("2005-1-30");
              empleat.setId_empresa(1);
              empleat.setContraseña("12345678j");
              empleat.setAdministrador(false);
              datos.setEmpleat(empleat);
              datos.setAccio(2);
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
              
              // Crear una instancia de la clase y establecer sus valores
              DatosEmpleado datos = new DatosEmpleado();
              //ArrayList<String> list = new ArrayList<>();
              Empleat empleat = new Empleat();
              empleat.setIdempleado(9);
              empleat.setNombre("Octavio");
              empleat.setApellidos("Sánchez Fernández");
              empleat.setDni("12345678s");
              empleat.setCategoria("rrhh");
              empleat.setId_empresa(1);
              empleat.setContraseña("12345678s");
              empleat.setAdministrador(true);
              datos.setEmpleat(empleat);
              datos.setAccio(2);
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
              Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
          }

    }

    /**
     * Quinto Test para Actualizar un empleado
     *
     * @throws IOException
     */
    @Test
    public void TestE() throws IOException {
          try {

              
              System.out.println("-----------TEST E-------------");
              System.out.println("-----ACTUALIZAR EMPLEADO-------");
              // Crear una instancia de la clase y establecer sus valores
              DatosEmpleado datos = new DatosEmpleado();
              Empleat empleat = new Empleat();
              empleat.setDni("admin");
              datos.setEmpleat(empleat);
              datos.setAccio(3);
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
              SocketSSL_Cliente empleadoSSL = new SocketSSL_Cliente();
              Socket socket = empleadoSSL.connect(HOST, PUERTO);
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              out.println(json);
              
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
              
              // Crear una instancia de la clase y establecer sus valores
              DatosEmpleado datos = new DatosEmpleado();
              Empleat empleat = new Empleat();
              empleat.setDni("12348765j");
              datos.setEmpleat(empleat);
              datos.setAccio(4);
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
              
              // Crear una instancia de la clase y establecer sus valores
              DatosEmpleado datos = new DatosEmpleado();
              Empleat empleat = new Empleat();
              empleat.setIdempleado(1);
              datos.setEmpleat(empleat);
              datos.setAccio(7);
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
              
              String jsonR = data.toString();
              
              socket.close();
              
              Assert.assertEquals(jsonR, data);
          } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
              Logger.getLogger(TestGestionEmpleado.class.getName()).log(Level.SEVERE, null, ex);
          }

    }

}
