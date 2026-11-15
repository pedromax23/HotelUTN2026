package Interfaces;

import Base_de_datos.Base_De_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface IPersonaDAO {

    boolean noExisteDni (String dniIngresado);

    static boolean ExisteContraseña(int contraseña) {
        String sql = ("SELECT contrasenia FROM cliente WHERE contrasenia=?");

        try (Connection conexion = Base_De_Datos.CreaConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, contraseña);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {

            Logger.getLogger(Base_De_Datos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al verificar la Contraseña");

        }

        return false;
    }
}
