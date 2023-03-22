/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente_Ejemplo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Class containing a client program to test the mock server that works with
 * sockets
 *
 * @author Ivimar
 */
public class Cliente {

    public Cliente() {
    }

    private static final String USER_ADMIN = "rrhh";
    private static final String USER_CONDUCTOR = "conductor";
    private static final String HOST = "localhost";
    private static final int PORT = 8180;
    private static int sessionUser;
    private static Object Empleat;

    public static void main(String[] args) throws IOException {
        Empleat empleado = new Empleat();

        //Socket socket = new Socket("localhost", 8081);
        Socket socket = connect();
        Gson gson = new Gson();

        String jsonString = "{"+"empleat:," + "nom:"+"admin,"+ "contraseya:"+"admin," +"id_empleat:"+"0,"+ "accio:"+"1," + "clase:"+"Empleats.class"+"}";
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleado));
        //obtEmpleat.addProperty("nom", "admin");
        //obtEmpleat.addProperty("contrasenya", "admin");
        //obtEmpleat.addProperty("id_empleat", 0);
        obtEmpleat.addProperty("accio", 0);
        obtEmpleat.addProperty("clase", "Empleat.class");

        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        String json = gson.toJson(obtEmpleat);
                System.out.println("jhghkljñlk´l------------" +obtEmpleat);
                       

        output.println(obtEmpleat);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        socket.close();
        /* 
        InputStream entrada = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = entrada.read(buffer);
        String resposta = new String(buffer, 0, bytesRead);
         */

    }
    //opens a connection to the server

    private static Socket connect() {
        Socket s;
        if (HOST.isEmpty()) {
            return null;
        } else {
            try {
                s = new Socket(HOST, PORT);
                s.setSoTimeout(4000);
                return s;
            } catch (IOException ex) {
                return null;
            }
        }
    }

}
