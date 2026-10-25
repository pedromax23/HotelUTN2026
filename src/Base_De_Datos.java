import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Base_De_Datos {

    Connection conexion;

    public void CreaConexion(){
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "18122005");
            System.out.println("Conexion establecida con exito");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error al conectar con la base de datos");
        }

    }
    public void crearTablasHotel() {
        try {
            Statement stmt = conexion.createStatement();
            String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                    "dni VARCHAR(20) PRIMARY KEY, " +
                    "nombre VARCHAR(50) NOT NULL, " +
                    "apellido VARCHAR(50) NOT NULL, " +
                    "sexo CHAR(1) NOT NULL, " +
                    "edad INT NOT NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "domicilio VARCHAR(150) NOT NULL, " +
                    "contrasenia VARCHAR(100) NOT NULL" +
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
            System.out.println("Las tablas del hotel se crearon (o ya existían) ");

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
