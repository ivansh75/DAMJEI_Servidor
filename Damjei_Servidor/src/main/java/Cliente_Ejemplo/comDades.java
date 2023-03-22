/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente_Ejemplo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Ivimar
 */

public class comDades{

    int port = 8180;
    String ip = "localhost";
    Socket socket;
    public comDades(){
        
    }

    public void enviaDades(JsonObject object, Socket socket) throws IOException {
        
        Gson gson = new Gson();

        String json = gson.toJson(object);
        OutputStream sortida = socket.getOutputStream();
        sortida.write(json.getBytes());
        
    }

    public JsonObject repDades(Socket socket) throws IOException {
        
        Gson gson2 = new Gson();
        InputStream entrada = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = entrada.read(buffer);
        String resposta = new String(buffer, 0, bytesRead);
        JsonObject objecte = gson2.fromJson(resposta, JsonObject.class);
        return objecte;
        
    }
}
