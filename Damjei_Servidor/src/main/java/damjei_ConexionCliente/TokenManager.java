/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_ConexionCliente;

/**
 *
 * @author Ivimar
 */
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenManager {

    // Token duracion en milliseconds, la multiplicación de todo es un total valido para 1día
    private static final long TOKEN_DURATION = 24 * 60 * 60 * 1000;

    private static Map<String, Long> tokens = new HashMap<>(); // Map to store tokens and their expiration time

    /**
     *
     * Método para generar un nuevo token para una identificación de usuario
     * dada
     */
    public static String generateToken(String userId) {
        String token = UUID.randomUUID().toString(); // Generar un UUID aleatorio como token
        long expirationTime = System.currentTimeMillis() + TOKEN_DURATION; // Calcula expiración del tiempo
        tokens.put(token, expirationTime); // Agregar token y tiempo de vencimiento al mapa
        return token;
    }

    /**
     *
     *
     * Método para validar un token y devolver la identificación de usuario
     * asociada con él (o -1 si el token no es válido)
     *
     */
    public static int validateToken(String token) {
        if (token == null || !tokens.containsKey(token)) {
            return -1; // Token no valido
        }
        long expirationTime = tokens.get(token);
        if (System.currentTimeMillis() > expirationTime) {
            tokens.remove(token);// El token ha caducado, elimínelo del mapa
            return -1; // Token  no valido
        }
        // El token es válido, devuelve la identificación de usuario asociada con él
        return getUserIdFromToken(token);
    }

    /**
     *
     * Método para agregar un token para una identificación de usuario
     * determinada (útil para agregar tokens preexistentes al mapa)
     *
     */
    public static void addToken(String userId, String token) {
        long expirationTime = System.currentTimeMillis() + TOKEN_DURATION; // Calcula la expiración del tiempo
        tokens.put(token, expirationTime); // Agregar token y tiempo de vencimiento al mapa
    }

    /**
     * Método para eliminar un token del mapa
     *
     */
    public static void removeToken(String token) {
        tokens.remove(token);
    }

    /**
     * Método auxiliar para obtener el ID 
     * de usuario asociado con un token
     *
     */
    private static int getUserIdFromToken(String token) {
        return Integer.parseInt(token);
    }

}
