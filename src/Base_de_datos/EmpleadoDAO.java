package Base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // Crear un nuevo empleado (ADMINISTRADOR o RECEPCIONISTA)
    public static boolean crearEmpleado(String dni, String email, String contrasenia,
                                 String nombre, String apellido, String rol) {
        String sql = "INSERT INTO empleado (dni, email, contrasenia, nombre, apellido, rol) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.setString(2, email);
            ps.setString(3, contrasenia);
            ps.setString(4, nombre);
            ps.setString(5, apellido);
            ps.setString(6, rol);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear empleado: " + e.getMessage());
            return false;
        }
    }

    // Obtener rol de un empleado a partir de su email (útil al recuperar sesión activa)
    public String obtenerRolPorEmail(String email) {
        String sql = "SELECT rol FROM empleado WHERE email = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rol por email: " + e.getMessage());
        }
        return null;
    }

    // Autentica empleado y devuelve su rol ("ADMINISTRADOR" / "RECEPCIONISTA") o null si falla
    public static String obtenerRolSiCredencialesValidas(String email, String contrasenia) {
        String sql = "SELECT rol FROM empleado WHERE email = ? AND contrasenia = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, contrasenia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar empleado: " + e.getMessage());
        }
        return null;
    }

    // Verificar si ya existe un empleado con ese DNI o email
    public static boolean existePorDniOEmail(String dni, String email) {
        String sql = "SELECT 1 FROM empleado WHERE dni = ? OR email = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.setString(2, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de empleado: " + e.getMessage());
            return false;
        }
    }

    // Actualizar rol de un empleado
    public static boolean actualizarRol(String dni, String nuevoRol) {
        String sql = "UPDATE empleado SET rol = ? WHERE dni = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoRol);
            ps.setString(2, dni);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar rol de empleado: " + e.getMessage());
            return false;
        }
    }

    // Listar empleados
    public static List<String> listarEmpleados() {
        String sql = "SELECT id_empleado, dni, nombre, apellido, email, rol FROM empleado ORDER BY id_empleado";
        List<String> empleados = new ArrayList<>();

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                empleados.add(String.format("#%d | DNI: %s | %s %s | %s | Rol: %s",
                        rs.getInt("id_empleado"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("rol")));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return empleados;
    }
}