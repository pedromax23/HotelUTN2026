package Base_de_datos;

import com.sun.tools.javac.Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Iniciar_DB {

    public static void crearTablasHotel() {

        try {
            Connection conexion = Base_De_Datos.CreaConexion();

            Statement stmt = conexion.createStatement();
            String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                    "dni VARCHAR(20) PRIMARY KEY, " +
                    "nombre VARCHAR(50) NULL, " +
                    "apellido VARCHAR(50) NULL, " +
                    "sexo CHAR(1)  NULL, " +
                    "edad INT  NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "domicilio VARCHAR(150)  NULL, " +
                    "contrasenia INT NOT NULL" +
                    ")";
            stmt.execute(sqlCliente);

            String sqlHabitacion = "CREATE TABLE IF NOT EXISTS habitacion (" +
                    "numeroHabitacion INT PRIMARY KEY, " +
                    "tipo VARCHAR(50) NOT NULL, " +
                    "disponible BOOLEAN NOT NULL, " +
                    "enUso BOOLEAN NOT NULL" +
                    ")";
            stmt.execute(sqlHabitacion);

            String sqlReserva = "CREATE TABLE IF NOT EXISTS reserva (" +
                    "idReserva INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                    "dni_cliente VARCHAR(20) NOT NULL, " +
                    "numeroHabitacion INT NOT NULL, " +
                    "diasEstadia INT NOT NULL, " +
                    "valorEstadia INT NOT NULL, " +
                    "FOREIGN KEY (dni_cliente) REFERENCES cliente(dni), " +
                    "FOREIGN KEY (numeroHabitacion) REFERENCES habitacion(numeroHabitacion)" +
                    ")";
            stmt.execute(sqlReserva);

            stmt.close();
            conexion.close();
            System.out.println("Las tablas del hotel se crearon (o ya existían) ");

        } catch (SQLException ex) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al cargar las tablas");

        }
    }

}
