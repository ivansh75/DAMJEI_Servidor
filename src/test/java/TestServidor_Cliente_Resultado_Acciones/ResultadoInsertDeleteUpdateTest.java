/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_Resultado_Acciones;

import damjei_BD.ConexionBD;
import static damjei_GestionClases.GestionEmpleados.ELIMINAR;
import static damjei_GestionClases.GestionEmpleados.INSERTAR;
import static damjei_GestionClases.GestionEmpleados.MODIFICAR;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ivimar
 */
public class ResultadoInsertDeleteUpdateTest {

    PreparedStatement ps = null;
    ResultSet rs = null;

    ConexionBD cn = new ConexionBD();
    Connection con = cn.establecerConexion();

    public ResultadoInsertDeleteUpdateTest() {
    }

    /**
     * prueba de resultado Tercer Test para Eliminar un empleado prueba de
     * resultado Tercer Test para Listar un empleado
     *
     * @param variable
     * @param accion
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public Boolean ComprobarResultadosEmpleado(String variable, int accion) throws IOException, SQLException {
        boolean prueba = false;

        String sqli = """
                                      SELECT * FROM public.empleados
                                       ORDER BY idempleado ASC""";

        ps = con.prepareStatement(sqli);
        rs = ps.executeQuery();

        while (rs.next()) {
            switch (accion) {
                case INSERTAR:
                    if (variable.equalsIgnoreCase(rs.getString("dni"))) {
                        prueba = true;
                    }
                case ELIMINAR:
                    if (!variable.equalsIgnoreCase(rs.getString("dni"))) {
                        prueba = true;
                    }
                case MODIFICAR:
                    if (variable.equalsIgnoreCase(rs.getString("categoria"))) {
                        prueba = true;
                    }
            }
        }
        rs.close();
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();

        return prueba;

    }

    /**
     * Prueba de resultado Cuarto Test para Eliminar un vehiculo Prueba de
     * resultado Cuarto Test para Listar un vehiculo
     *
     * @param variable
     * @param accion
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public Boolean ComprobarResultadosVehiculo(String variable, int accion) throws IOException, SQLException {
        boolean prueba = false;

        String sqli = """
                                      SELECT * FROM public.vehiculos
                                       ORDER BY idvehiculo ASC""";

        ps = con.prepareStatement(sqli);
        rs = ps.executeQuery();

        while (rs.next()) {
            switch (accion) {
                case ELIMINAR:
                    if (!variable.equalsIgnoreCase(rs.getString("matricula"))) {
                        prueba = true;
                    }
                case INSERTAR:
                    if (variable.equalsIgnoreCase(rs.getString("matricula"))) {
                        prueba = true;
                    }

                case MODIFICAR:
                    if (variable.equalsIgnoreCase(rs.getString("fecha_baja"))) {
                        prueba = true;
                    }

            }
        }

        rs.close();
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();

        return prueba;

    }

    /**
     * Prueba de resultado Quinto Test para Eliminar un Repostaje Prueba de
     * resultado Quinto Test para Listar un Repostaje
     *
     * @param variable
     * @param accion
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public Boolean ComprobarResultadosRepostar(String variable, int accion) throws IOException, SQLException {
        boolean prueba = false;

        String sqli = """
                                      SELECT * FROM public.repostar
                                       ORDER BY idrepostar ASC""";

        ps = con.prepareStatement(sqli);
        rs = ps.executeQuery();

        while (rs.next()) {
            String result = String.valueOf(rs.getString("idrepostar"));
            switch (accion) {
                case ELIMINAR:
                    if (!variable.equalsIgnoreCase(result)) {                  
                        prueba = true;
                    }
                case INSERTAR:
                    if (variable.equalsIgnoreCase(result)) {
                        prueba = true;
                    }
                case MODIFICAR:
                    //if (variable.equalsIgnoreCase(String.valueOf(rs.getString("conductorid")))) {
                    if (variable.equalsIgnoreCase(result)) {
                        prueba = true;
                    }
            }
        }

        rs.close();
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();

        return prueba;

    }

    /**
     * Prueba de resultado Sexto Test para Eliminar una Revision Prueba de
     * resultado Sexto Test para Listar una Revision
     *
     * @param variable
     * @param accion
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public Boolean ComprobarResultadosRevison(String variable, int accion) throws IOException, SQLException {
        boolean prueba = false;

        String sqli = """
                                      SELECT * FROM public.revisiones
                                       ORDER BY idrevision ASC""";

        ps = con.prepareStatement(sqli);
        rs = ps.executeQuery();

        while (rs.next()) {
            switch (accion) {
                case ELIMINAR:
                    if (!variable.equalsIgnoreCase(String.valueOf(rs.getString("idrevision")))) {
                        prueba = true;
                    }
                case INSERTAR:
                    if (variable.equalsIgnoreCase(String.valueOf(rs.getString("idrevision")))) {
                        prueba = true;
                    }
                case MODIFICAR:
                    if (variable.equalsIgnoreCase(String.valueOf(rs.getString("mantenimientoid")))) {
                        prueba = true;
                    }
            }
        }

        rs.close();
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();

        return prueba;

    }

    /**
     * Prueba de resultado Septimo Test para Eliminar un Mantenimiento Prueba de
     * resultado Septimo Test para Listar un Mantenimiento
     *
     * @param variable
     * @param accion
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public Boolean ComprobarResultadosManteniento(String variable, int accion) throws IOException, SQLException {
        boolean prueba = false;

        String sqli = """
                                      SELECT * FROM public.mantenimiento
                                       ORDER BY idmantenimiento ASC""";

        ps = con.prepareStatement(sqli);
        rs = ps.executeQuery();

        while (rs.next()) {
            switch (accion) {
                case ELIMINAR:
                    if (!variable.equalsIgnoreCase(rs.getString("nombre"))) {
                        prueba = true;
                    }
                case INSERTAR:
                    if (variable.equalsIgnoreCase(rs.getString("nombre"))) {
                        prueba = true;
                    }
                case MODIFICAR:
                    if (variable.equalsIgnoreCase(rs.getString("kilometros_mantenimiento"))) {
                        prueba = true;
                    }
            }
        }

        rs.close();
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return prueba;

    }

    /**
     * Prueba de resultado Octavo Test para Eliminar un combustible Prueba de
     * resultado Octavo Test para Listar un combustible
     *
     * @param variable
     * @param accion
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public Boolean ComprobarResultadosCombustible(String variable, int accion) throws IOException, SQLException {
        boolean prueba = false;

        String sqli = """
                                      SELECT * FROM public.combustible
                                       ORDER BY idcombustible ASC""";

        ps = con.prepareStatement(sqli);
        rs = ps.executeQuery();

        while (rs.next()) {
            switch (accion) {
                case ELIMINAR:
                    if (!variable.equalsIgnoreCase(rs.getString("nombre"))) {
                        prueba = true;
                    }
                case INSERTAR:
                    if (variable.equalsIgnoreCase(rs.getString("nombre"))) {
                        prueba = true;
                    }

                case MODIFICAR:
                    if (variable.equalsIgnoreCase(String.valueOf(rs.getFloat("precio")))) {
                        prueba = true;
                    }

            }
        }

        rs.close();
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();
        return prueba;

    }

    /**
     * prueba de resultado Tercer Test para Eliminar un empleado prueba de
     * resultado Tercer Test para Listar un empleado
     *
     * @param variable
     * @param accion
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public Boolean ComprobarResultadosConductor(String variable, int accion) throws IOException, SQLException {
        boolean prueba = false;

        String sqli = """
                                      SELECT * FROM public.conductor
                                       ORDER BY idconductor ASC""";

        ps = con.prepareStatement(sqli);
        rs = ps.executeQuery();

        while (rs.next()) {

            if (accion == MODIFICAR) {
                if (variable.equalsIgnoreCase(rs.getString("fecha_caducidad_carnet"))) {
                    prueba = true;
                }
            }
        }
        rs.close();
        ps.close(); // Cerrar el PreparedStatement
        cn.cerrarConexion();

        return prueba;

    }

}
