package Interfaces;

import Base_de_datos.ConexionDB;
import Base_de_datos.ClienteDAO;
import Clases.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface IPersonaDAO {

    static boolean noExisteDni(String dniIngresado) {

        ArrayList<Cliente> personas = ClienteDAO.listarClientes();

        return true;

    }

    static boolean ExisteContraseña(int contraseña) {
        String sql = ("SELECT contrasenia FROM cliente WHERE contrasenia=?");

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, contraseña);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {

            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al verificar la Contraseña");

        }

        return false;
    }
}
