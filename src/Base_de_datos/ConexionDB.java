package Base_de_datos;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConexionDB {

    private static final String RUTA_CONFIG = "db.properties";
    private static String url;
    private static String usuario;
    private static String contrasenia;

    // Bloque estático para cargar la configuración una sola vez
    static {
        try (FileInputStream fis = new FileInputStream(RUTA_CONFIG)) {
            Properties props = new Properties();
            props.load(fis);

            url = props.getProperty("db.url");
            usuario = props.getProperty("db.usuario");
            contrasenia = props.getProperty("db.contrasenia");

            // Carga explícita del driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de configuración de la BD: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver JDBC de MySQL: " + e.getMessage());
        }
    }

    // Devuelve una nueva conexión cada vez que se solicita
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contrasenia);
    }


}

