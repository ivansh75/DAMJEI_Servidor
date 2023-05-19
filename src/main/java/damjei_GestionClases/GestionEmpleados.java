/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package damjei_GestionClases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import damjei_BD.SqlConductor;
import damjei_BD.SqlEmpleado;
import damjei_ConexionCliente.ClientRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Clase donde obtenemos el Gson objeto del empleado
 *
 * @author Ivimar
 */
public class GestionEmpleados {

    public static final String EMPLEADOS = "empleados";
    //constantes de los titulos atributos
    public static final String IDEMPLEADO = "idempleado";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDOS = "apellidos";
    public static final String DNI = "dni";
    public static final String CATEGORIA = "categoria";
    public static final String FECHA_CARNET = "fecha_carnet";
    public static final String FECHA_CADUCIDAD = "fecha_caducidad_carnet";
    public static final String EMPRESAID = "empresaid";
    public static final String CONTRASEÑA = "contraseña";
    public static final String ADMINISTRADOR = "administrador";
    public static final String DATOS_EMPLEADO = "empleat";
    public static final String DATOS_CLASE = "clase";
    public static final String DATOS_ACCION = "accio";
    public static final String DATOS_TOKEN = "token";

    //constantes globales para las acciones
    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;
    public static final int INSERTAR = 2;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;

    public static final String ERROR_GESTIONUSUARIO = "Error en la gestion de Usuarios";
    public static final String ERROR_AUTORIZACION = "Este usuario debe registrarse-Login";

    String respostaCl;
    boolean conexio = false;

    /**
     *
     * Constructor simple
     */
    public GestionEmpleados() {
    }

    public String GestionEmpleados(String json) throws SQLException, ParseException {

        Gson gson = new Gson();
        ClientRequest request = new ClientRequest();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        // Obtener el objeto Empleat del JSON 
        JsonObject empleatJson = jsonObject.getAsJsonObject(DATOS_EMPLEADO);
        if (empleatJson != null && !empleatJson.isJsonNull()) {
            request.setAccion(jsonObject.get(DATOS_ACCION).getAsInt());

            switch (request.getAccion()) {
                case LOGIN: {
                    request.setString1(empleatJson.get(DNI).getAsString());
                    request.setString2(empleatJson.get(CONTRASEÑA).getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.LoginUsuari(request.getString1(), request.getString2());
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }

                case LOGOUT: {
                    request.setString1(jsonObject.get(DATOS_TOKEN).getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.LogoutUsuari(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }

                case INSERTAR: {

                    request.setDatos(empleatJson.get(String.valueOf(IDEMPLEADO)).getAsString());
                    request.setDatos(empleatJson.get(NOMBRE).getAsString());
                    request.setDatos(empleatJson.get(APELLIDOS).getAsString());
                    request.setDatos(empleatJson.get(DNI).getAsString());
                    request.setDatos(empleatJson.get(CATEGORIA).getAsString());
                    request.setDatos(empleatJson.get(String.valueOf(EMPRESAID)).getAsString());
                    request.setDatos(empleatJson.get(CONTRASEÑA).getAsString());
                    request.setDatos(empleatJson.get(String.valueOf(ADMINISTRADOR)).getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    //para asignar valores el 0 el el array, el 1 idempleado......
                    String rs = sql.InsertarUsuari(request.getDatos());

                    if (empleatJson.get(CATEGORIA).getAsString().equalsIgnoreCase("conductor")) {
                        //ArrayList<String> datos = new ArrayList<>();
                        String fecha = (empleatJson.get(FECHA_CARNET).getAsString());
                        String fechaCaducidad = (empleatJson.get(FECHA_CADUCIDAD).getAsString());
                        SqlConductor sqlc = new SqlConductor();
                        String rsc = sqlc.InsertConductorFechas(fecha, fechaCaducidad);
                    }
                    respostaCl = rs;
                    return respostaCl;

                }
                case ELIMINAR: {
                    request.setString1(empleatJson.get(DNI).getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.EliminarUsuario(request.getString1());

                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }
                case LISTAR: {
                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.ListarEmpleados(EMPLEADOS);
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }

                case MODIFICAR: {
                    String rs ;
                    request.setString1(empleatJson.get(DNI).getAsString());

                    if (empleatJson.get(CONTRASEÑA) != null) {
                        request.setString2(empleatJson.get(CONTRASEÑA).getAsString());
                        request.setString3(CONTRASEÑA);
                    } else if (empleatJson.get(CATEGORIA) != null) {
                        request.setString2(empleatJson.get(CATEGORIA).getAsString());
                        request.setString3(CATEGORIA);
                    } else if (empleatJson.get(FECHA_CADUCIDAD) != null) {
                        request.setString2(empleatJson.get(FECHA_CADUCIDAD).getAsString());
                        request.setString3(FECHA_CADUCIDAD);
                    } else if (empleatJson.get(ADMINISTRADOR) != null) {
                        request.setString2(empleatJson.get(String.valueOf(ADMINISTRADOR)).getAsString());
                        request.setString3(ADMINISTRADOR);
                    } else if (empleatJson.get(EMPRESAID) != null) {
                        request.setString2(empleatJson.get(String.valueOf(EMPRESAID)).getAsString());
                        request.setString3(EMPRESAID);
                    }
                    request.setAccion(jsonObject.get(DATOS_ACCION).getAsInt());
                    //comprobamos si el atributo es de fecha caducidad es de conductor, sino seguimos con empleados
                    if (request.getString3().equalsIgnoreCase(FECHA_CADUCIDAD)){
                        SqlConductor sqlcon = new SqlConductor();
                        rs = sqlcon.ModificarConductor(request.getString1(), request.getString2(), request.getString3());
                    
                        
                    }else{                       

                    SqlEmpleado sql = new SqlEmpleado();
                    rs = sql.ModificarUsusario(request.getString1(), request.getString2(), request.getString3());
                    }
                    
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }

                case LISTARID: {

                    request.setString1(empleatJson.get(String.valueOf(IDEMPLEADO)).getAsString());

                    SqlEmpleado sql = new SqlEmpleado();
                    String rs = sql.ListarEmpleados(request.getString1());
                    if (!rs.isEmpty()) {
                        respostaCl = rs;
                        return respostaCl;
                    } else {
                        String error = ("correcta:" + conexio + " error: " + ERROR_GESTIONUSUARIO);
                        return respostaCl = gson.toJson(error);
                    }
                }
            }

        }

        String error = ("correcta: " + conexio + " error: " + ERROR_GESTIONUSUARIO);
        return respostaCl = gson.toJson(error);
    }
}
