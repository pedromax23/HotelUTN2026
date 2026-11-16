package Base_de_datos;

import Clases.Cliente;
import Interfaces.IPersonaDAO;
import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuariosDAO implements IPersonaDAO {

    public static void registrarUsuario(String dni, String email, int contrasenia) {

        String sql = "INSERT INTO cliente (dni, email, contrasenia) VALUES (?, ?, ?)";

        try (Connection conexion = Base_De_Datos.CreaConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setString(1, dni);
            ps.setString(2, email);
            ps.setInt(3, contrasenia);
            ps.executeUpdate();
            System.out.println("Usuario Registrado");

        } catch (SQLException ex) {

            Logger.getLogger(Base_De_Datos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al registrar el Usuario");

        }
    }

    public static void registrarDatosUsuario(String dni, String nombre, String apellido, char sexo, int edad, String domicilio) {

        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, sexo = ?, edad = ?, domicilio = ? WHERE dni = ?";

        try (Connection conexion = Base_De_Datos.CreaConexion();
            PreparedStatement ps = conexion.prepareStatement(sql);){


            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, String.valueOf(sexo));
            ps.setInt(4, edad);
            ps.setString(5, domicilio);
            ps.setString(6, dni);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Se guardaron los datos");

            } else {
                System.out.println("El dni no coincide.");

            }

        } catch (SQLException ex) {

            Logger.getLogger(Base_De_Datos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al actualizar los datos.");

        }
    }

    public static ArrayList<Cliente> listarClientes() {

        String sql = "SELECT * FROM cliente";

        try {
            Connection conexion = Base_De_Datos.CreaConexion();

            Statement st = conexion.createStatement();
            st.executeQuery(sql);

            ResultSet rs = st.getResultSet();

            ArrayList<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {

                String sexoStr = rs.getString("sexo");
                char sexo = ' ';

                if (sexoStr != null && !sexoStr.isEmpty()) {
                    sexo = sexoStr.charAt(0);
                }

                System.out.println("Sexo (char): " + sexo);
                Cliente cliente = new Cliente(rs.getString("nombre"), rs.getString("apellido"), sexo, rs.getString("dni"), rs.getInt("edad"), rs.getString("email"), rs.getString("domicilio"));

            }

            return clientes;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static boolean NoExisteEmail(String email) {

        ArrayList<Cliente> clientes = listarClientes();

        for(Cliente cliente : clientes) {
            if(cliente.getEmail().equals(email)) {
                return false;
            }
        }

        return true;
    }

    public static Cliente buscarPorEmail(String email) {
        char sexo;

        String sql = "SELECT * FROM cliente WHERE email = ?";

        try (Connection conexion = Base_De_Datos.CreaConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    if (rs.getString("sexo") != null) {
                        sexo = rs.getString("sexo").charAt(0);
                    } else {
                        sexo = '\0';
                    }

                    return new Cliente(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            sexo,
                            rs.getString("dni"),
                            rs.getInt("edad"),
                            rs.getString("email"),
                            rs.getString("domicilio"),
                            rs.getInt("contrasenia")
                    );

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return null;
    }

}
