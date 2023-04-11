/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package damjei_Servidor_Run;

import com.google.gson.Gson;
import damjei_ConexionCliente.ProcesarConsulta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivimar
 */
public class Server {

    private final String COMMUNICACION_S_CL = "Abriendo comunicación Servidor-Cliente";
    private final String INPUT_OUTPUT_ERROR = "Input/Output error";
    //Variables par el servidor
    private static String host = "localhost";
    private static int puerto = 8180;

    //variables par distinguir consultas
    public final String CLIENT_MESSAGE = "CLI:";
    public final String SERVER_RESPONSE = "SRV:";
    public static final String COMMENT = "COM:";

    private int threadCount = 0;
   
    

    public static void main(String[] args) {

        Server rn = new Server();

        try {
            rn.initialize(host, puerto);
            rn.run();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // server thread
    private class RunnableListener implements Runnable {

        private volatile boolean running = false; // read only property
        private ServerSocket sk = null;

        /**
         * Stops server: closes socket used by run method and sets running
         * property to false to end run method execution
         */
        public void shutdown() {
            if (sk != null && !sk.isClosed()) try {
                sk.close();
            } catch (IOException ex) {

            } finally {
                running = false;
            }
        }

        /**
         * gets running property value
         *
         * @return running property value
         */
        public boolean isRunning() {
            return running;
        }

        /**
         * Starts server. Server runs until shutdwon method is called. Sets
         * running property to true
         */
        @Override
        public synchronized void run() {
            Gson gson = new Gson();
            try {
                running = true;
                int i = 0;

                sk = new ServerSocket(puerto);

                while (running) {
                    Socket client = sk.accept();

                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String datos = input.readLine();

                    System.out.println(CLIENT_MESSAGE + "\n" + datos + "\n");
                    try {
                        replay(client, datos);  // sends response to client in a new thread and, in this way, continues listening clients
                    } catch (SQLException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                i++;
            } catch (IOException e) {
                if (sk != null && !sk.isClosed()) {
                    showIOErrorInformation(e, "Eroorrr"); // if sk.isClosed can be a "Server stop" operation
                }
            }
        }
    }
    private RunnableListener listener = new RunnableListener();

    /**
     * Creaadas en el servidor
     *
     * @param host
     * @param puerto
     * @throws java.io.IOException if an I/O error occurs
     * @throws Error if files don't have a good structure
     */
    public void initialize(String host, int puerto) throws IOException {

        this.host = host;
        this.puerto = puerto;

    }

    /**
     * Launches server
     */
    public void run() {

        System.out.println("\n");
        System.out.println(COMMUNICACION_S_CL);  //writes tittle
        System.out.printf(new String(new char[COMMUNICACION_S_CL.length()]).replace("\0", "-"));  // writes underscore of the tittle (at Java 11 can be done with repeat() )
        System.out.println();
        if (listener.isRunning()) {
            return;
        }

        Thread thread = new Thread(listener);
        thread.start();

    }

    // Sends response to client
    // Creates a new thread to allow server continue listening 
    private void replay(Socket client, String datos) throws SQLException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                threadCount++;
                try {
                    ProcesarConsulta dataBase = new ProcesarConsulta();
                    
                    String responseText = null;
                    try {
                        responseText = dataBase.ConsultaCliente(datos);
                    } catch (SQLException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sendResponse(client, responseText);

                    System.out.println(SERVER_RESPONSE + "\n" + responseText + "\n");
                    // Cuando un cliente se conecta
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                threadCount--;
            }
        });

        t.start();
    }

    /**
     * stops server: stops main thread and waits until all secundary threads are
     * finshed (each secundary thread just send a response to a client)
     */
    @SuppressWarnings("empty-statement")
    public void stop() {
        listener.shutdown();
        while (threadCount > 0);
    }

    private void showIOErrorInformation(Exception e, String error) {
        System.err.format(INPUT_OUTPUT_ERROR + ":" + e.getMessage());
    }

    // returns formatted current time in an String
    private String thisMoment() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }

    /**
     * informs if server is running or not return true if server is running,
     * false otherwise
     *
     */
    public boolean isRunning() {
        return listener.isRunning();
    }

    /**
     * send to client a message
     *
     * @param cliente socket open with the client
     * @param responde server response to be sent to client
     *
     * @throws IOException if a network error occurs
     */
    protected void sendResponse(Socket cliente, String responde) throws IOException  {
        Gson gson = new Gson();
        PrintWriter output = new PrintWriter(new OutputStreamWriter(cliente.getOutputStream()), true);
        output.println(responde);

    }
}
