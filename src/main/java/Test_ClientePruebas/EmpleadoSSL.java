/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test_ClientePruebas;

import com.google.gson.Gson;
import damjei_ConexionCliente.Encriptador;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Class containing a client program to test the mock server that works with
 * sockets
 *
 * @author Ivimar
 */
public class EmpleadoSSL {

    private static final String CLAU_CLIENT = "C:\\Program Files\\Java\\jdk-18.0.1.1\\bin\\client_ks"; //heu de ficar la vostra direcció
    //private static String CLAU_CLIENT = "C:\\Users\\i\\Documents\\NetBeansProjects\\Damjei_Servidor\\Cliente\\client_ks"; //heu de ficar la vostra direcció
    private static final String CLAU_CLIENT_PASSWORD = "456456";

    private static final String HOST = "localhost";
    private static final int PUERTO = 8180;
    private static Socket s;
    private SSLSocket client;

    public EmpleadoSSL() {

    }
    //opens a connection to the server

    public Socket connect(String host, int port) throws KeyStoreException, FileNotFoundException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("src/main/certs/client/clientKey.jks"),
                "clientpass".toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "clientpass".toCharArray());

        KeyStore trustedStore = KeyStore.getInstance("JKS");
        trustedStore.load(new FileInputStream(
                "src/main/certs/client/clientTrustedCerts.jks"), "clientpass"
                .toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustedStore);

        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        KeyManager[] keyManagers = kmf.getKeyManagers();
        sc.init(keyManagers, trustManagers, null);

        SSLSocketFactory ssf = sc.getSocketFactory();
        client = (SSLSocket) ssf.createSocket(host, port);
        client.startHandshake();
        /*
         PrintWriter output = new PrintWriter(client.getOutputStream());
               output.println("Federico");
               output.flush();
               System.out.println("Federico sent");
               BufferedReader input = new BufferedReader(new InputStreamReader(
                     client.getInputStream()));
               String received = input.readLine();
               System.out.println("Received : " + received);
               client.close();
           */    
            
        return client;

    }

    public static void main(String[] args) throws IOException, KeyStoreException {

        try {
            Usuario();
            //InsertarUsuario();
            //Actualizar();
            //Eliminar();
            //Listar();
            //Actualizar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpleadoSSL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Usuario() throws IOException {

        try {
            Encriptador hash = new Encriptador();
            String contraseña = hash.encriptarConSha256("admin1");
            // Crear una instancia de la clase y establecer sus valores
            DatosEmpleado datos = new DatosEmpleado();
            Empleat empleat = new Empleat();
            empleat.setDni("admin");
            empleat.setContraseña(contraseña);
            datos.setEmpleat(empleat);
            datos.setAccio(0);
            datos.setClase("Empleats.class");
            
            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);
            System.out.println("---------ENVIA CLIENTE---" + json);
            
            // Enviar los datos al servidor usando una conexión de socket
            EmpleadoSSL empleadoSSL = new EmpleadoSSL ();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);
            out.flush();
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String data = input.readLine();
            
            System.out.println("---------RECIBE CLIENTE---" + data);
            System.out.flush();
           
            socket.close();
            
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpleadoSSL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void InsertarUsuario() throws IOException {

        try {
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
            
            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);
            System.out.println("---------ENVIA CLIENTE---" + json);
            
            // Enviar los datos al servidor usando una conexión de socket
            EmpleadoSSL empleadoSSL = new EmpleadoSSL ();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String data = input.readLine();
            
            System.out.println("---------RECIBE CLIENTE---" + data);
            
            socket.close();
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpleadoSSL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Actualizar() throws IOException {
        try {
            // Crear una instancia de la clase y establecer sus valores
            DatosEmpleado datos = new DatosEmpleado();
            Empleat empleat = new Empleat();
            empleat.setDni("admin");
            datos.setEmpleat(empleat);
            datos.setAccio(3);
            datos.setClase("Empleats.class");
            
            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);
            System.out.println("---------ENVIA CLIENTE---" + json);
            
            // Enviar los datos al servidor usando una conexión de socket
            EmpleadoSSL empleadoSSL = new EmpleadoSSL ();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String data = input.readLine();
            
            System.out.println("---------RECIBE CLIENTE---" + data);
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpleadoSSL.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    public static void Eliminar() throws IOException {
        try {
            // Crear una instancia de la clase y establecer sus valores
            DatosEmpleado datos = new DatosEmpleado();
            Empleat empleat = new Empleat();
            empleat.setDni("12345678j");
            datos.setEmpleat(empleat);
            datos.setAccio(4);
            datos.setClase("Empleats.class");
            
            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);
            System.out.println("---------ENVIA CLIENTE---" + json);
            
            // Enviar los datos al servidor usando una conexión de socket
            EmpleadoSSL empleadoSSL = new EmpleadoSSL ();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String data = input.readLine();
            
            System.out.println("---------RECIBE CLIENTE---" + data);
            
            socket.close();
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpleadoSSL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Listar() throws IOException {
        // Crear una instancia de la clase y establecer sus valores
        DatosEmpleado datos = new DatosEmpleado();
        Empleat empleat = new Empleat();
        datos.setEmpleat(empleat);
        datos.setAccio(5);
        datos.setClase("Empleats.class");

        // Serializar el objeto en formato JSON usando Gson
        Gson gson = new Gson();
        String json = gson.toJson(datos);
        System.out.println("---------ENVIA CLIENTE---" + json);

        // Enviar los datos al servidor usando una conexión de socket
        Socket socket = new Socket("localhost", 8180);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json);
        out.flush();

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        System.out.println("---------RECIBE CLIENTE---" + data);
        System.out.flush();
        socket.close();

    }

    public static void Modificar() throws IOException {

        try {
            // Crear una instancia de la clase y establecer sus valores
            DatosEmpleado datos = new DatosEmpleado();
            Empleat empleat = new Empleat();
            empleat.setDni("admin");
            empleat.setCategoria("modificada");
            datos.setEmpleat(empleat);
            datos.setAccio(6);
            datos.setClase("Empleats.class");
            
            // Serializar el objeto en formato JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(datos);
            System.out.println("---------ENVIA CLIENTE---" + json);
            
            // Enviar los datos al servidor usando una conexión de socket
            EmpleadoSSL empleadoSSL = new EmpleadoSSL ();
            Socket socket = empleadoSSL.connect(HOST, PUERTO);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String data = input.readLine();
            
            System.out.println("---------RECIBE CLIENTE---" + data);
            
            socket.close();
        } catch (KeyStoreException | FileNotFoundException | CertificateException | UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpleadoSSL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
