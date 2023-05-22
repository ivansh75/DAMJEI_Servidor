/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestServidor_Cliente_Resultado_Acciones;

import damjei_BD.SqlCombustible;
import damjei_BD.SqlConductor;
import damjei_BD.SqlEmpleado;
import damjei_BD.SqlMantenimiento;
import damjei_BD.SqlRepostar;
import damjei_BD.SqlRevisiones;
import damjei_BD.SqlVehiculos;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Ivimar
 */
public class ResultadoListarListarIdTest {

    /**
     * prueba de resultado Tercer Test para Listar empleados Tercer Test para
     * Listar un empleado
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListadoEmpleado(String variable) throws IOException, SQLException {
        String prueba;
        SqlEmpleado sql = new SqlEmpleado();

        prueba = sql.ListarEmpleados(variable);

        int lenght = prueba.length();

        return lenght;

    }

    /**
     * prueba de resultado Cuarto Test para Listar vehiculos prueba de resultado
     * Cuarto Test para Listar un vehiculo
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListarVehiculo(String variable) throws IOException, SQLException {
        String prueba;
        SqlVehiculos sql = new SqlVehiculos();

        prueba = sql.ListarVehiculo(variable);

        int lenght = prueba.length();

        return lenght;

    }

    /**
     * Prueba de resultado Quinto Test para Listar Repostajes Prueba de
     * resultado Quinto Test para Listar un Repostaje
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListarRepostar(String variable) throws IOException, SQLException {
        String prueba;
        SqlRepostar sql = new SqlRepostar();

        prueba = sql.ListarRepostar(variable);

        int lenght = prueba.length();

        return lenght;

    }

    /**
     * Prueba de resultado Sexto Test para Listar Revisiones Prueba de resultado
     * Sexto Test para Listar una Revisión
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListarRevison(String variable) throws IOException, SQLException {
        String prueba;
        SqlRevisiones sql = new SqlRevisiones();

        prueba = sql.ListarRevisiones(variable);

        int lenght = prueba.length();

        return lenght;

    }

    /**
     * Prueba de resultado Septimo Test para Listar Mantenimientos Prueba de
     * resultado Septimo Test para Listar un Mantenimiento
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListarManteniento(String variable) throws IOException, SQLException {
        String prueba;
        SqlMantenimiento sql = new SqlMantenimiento();

        prueba = sql.ListarMantenimiento(variable);

        int lenght = prueba.length();

        return lenght;

    }

    /**
     * Prueba de resultado Octavo Test para Listar combustibles Prueba de
     * resultado Octavo Test para Listar un combustible
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListarCombustible(String variable) throws IOException, SQLException {
        String prueba;
        SqlCombustible sql = new SqlCombustible();

        prueba = sql.ListarCombustible(variable);

        int lenght = prueba.length();

        return lenght;

    }
     /**
     * Prueba de resultado Noveno Test para Listar conductores
     * Prueba de resultado Noveno Test para Listar un conductor
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListarConductor(String variable) throws IOException, SQLException {
        String prueba;
        SqlConductor sql = new SqlConductor();

        prueba = sql.ListarConductor(variable);

        int lenght = prueba.length();

        return lenght;

    }
    
     /**
     * Prueba de resultado Décimo Test para Listar vehículo con revisiones pendientes
     *
     * @param variable
     * @return
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public int ComprobarListarVehiculoRevisiones(String variable) throws IOException, SQLException {
        String prueba;
        SqlRevisiones sql = new SqlRevisiones();

        prueba = sql.ListarRevisionesVehiculo(variable);

        int lenght = prueba.length();

        return lenght;

    }

}
