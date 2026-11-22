package Base_de_datos;

import Base_de_datos.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Registro inicial del cliente (DNI, email, contraseña)
    public boolean registrarClienteBasico(String dni, String email, String contrasenia) {
        String sql = "INSERT INTO cliente (dni, email, contrasenia) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.setString(2, email);
            ps.setString(3, contrasenia);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    // Completar o actualizar datos personales del cliente
    public boolean actualizarDatosCliente(String dni, String nombre, String apellido,
                                          String sexo, int edad, String domicilio, String origen) {
        String sql = "UPDATE cliente " +
                "SET nombre = ?, apellido = ?, sexo = ?, edad = ?, domicilio = ?, origen = ? " +
                "WHERE dni = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, sexo);
            ps.setInt(4, edad);
            ps.setString(5, domicilio);
            ps.setString(6, origen);
            ps.setString(7, dni);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar datos del cliente: " + e.getMessage());
            return false;
        }
    }

    // Verificar si ya existe un cliente con ese DNI
    public boolean existePorDni(String dni) {
        String sql = "SELECT 1 FROM cliente WHERE dni = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar DNI de cliente: " + e.getMessage());
            return false;
        }
    }

    // Verificar si ya existe un cliente con ese email
    public boolean existePorEmail(String email) {
        String sql = "SELECT 1 FROM cliente WHERE email = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar email de cliente: " + e.getMessage());
            return false;
        }
    }

    // Validar login de cliente por email y contraseña
    public boolean validarCredenciales(String email, String contrasenia) {
        String sql = "SELECT 1 FROM cliente WHERE email = ? AND contrasenia = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, contrasenia);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar credenciales de cliente: " + e.getMessage());
            return false;
        }
    }

    // Listar clientes (resumen útil para consola o reportes)
    public List<String> listarClientesResumen() {
        String sql = "SELECT dni, nombre, apellido, email, origen FROM cliente ORDER BY apellido, nombre";
        List<String> clientes = new ArrayList<>();

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombreCompleto = (rs.getString("nombre") != null ? rs.getString("nombre") : "") + " " +
                        (rs.getString("apellido") != null ? rs.getString("apellido") : "");
                clientes.add(String.format("DNI: %s | %s | Email: %s | Origen: %s",
                        rs.getString("dni"),
                        nombreCompleto.trim().isEmpty() ? "Sin completar" : nombreCompleto.trim(),
                        rs.getString("email"),
                        rs.getString("origen") != null ? rs.getString("origen") : "N/A"));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public String obtenerDniPorEmail(String email) {

        String sql = "SELECT dni FROM cliente WHERE email = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("dni");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener DNI: " + e.getMessage());
        }

        return null;
    }

}