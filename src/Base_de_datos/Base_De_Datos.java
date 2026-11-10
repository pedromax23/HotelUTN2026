package Base_de_datos;

import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Base_De_Datos {


    public static Connection CreaConexion() {

        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "18122005");
            Class.forName("com.mysql.cj.jdbc.Driver");

            Iniciar_DB.crearTablasHotel();

            System.out.println("Conexion establecida con exito");

            return conexion;

        } catch (ClassNotFoundException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error al conectar con la base de datos");

        }

        return null;
    }
}

