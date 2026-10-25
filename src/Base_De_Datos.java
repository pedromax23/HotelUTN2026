import java.sql.*;
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
                    "nombre VARCHAR(50) NULL, " +
                    "apellido VARCHAR(50) NULL, " +
                    "sexo CHAR(1)  NULL, " +
                    "edad INT  NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "domicilio VARCHAR(150)  NULL, " +
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
    public void registrarUsuario(String dni, String email, String contrasenia) {
        String sql = "INSERT INTO cliente (dni, email, contrasenia) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, email);
            ps.setString(3, contrasenia);
            ps.executeUpdate();
            System.out.println("Usuario Registrado");
        } catch (SQLException ex) {
            Logger.getLogger(Base_De_Datos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al registrar el Usuario");
        }
    }
}
