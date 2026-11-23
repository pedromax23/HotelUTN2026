package Base_de_datos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

    // Listar habitaciones no disponibles y su motivo (limpieza, mantenimiento, etc.)
    public static List<String> listarHabitacionesNoDisponibles() {
        String sql = "SELECT numero, tipo, estado, motivo_no_disponibilidad " +
                "FROM habitacion " +
                "WHERE estado <> 'DISPONIBLE'";
        List<String> resultado = new ArrayList<>();

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String detalle = String.format(
                        "Hab. #%d (%s) - Estado: %s - Motivo: %s",
                        rs.getInt("numero"),
                        rs.getString("tipo"),
                        rs.getString("estado"),
                        rs.getString("motivo_no_disponibilidad") != null
                                ? rs.getString("motivo_no_disponibilidad")
                                : "Sin especificar"
                );
                resultado.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar habitaciones no disponibles: " + e.getMessage());
        }
        return resultado;
    }

    // Cambiar estado y motivo (si pasa a DISPONIBLE, limpia el motivo)
    public static boolean actualizarEstadoHabitacion(int numero, String nuevoEstado, String motivo) {
        String sql = "UPDATE habitacion SET estado = ?, motivo_no_disponibilidad = ? WHERE numero = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            if ("DISPONIBLE".equalsIgnoreCase(nuevoEstado)) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, motivo);
            }
            ps.setInt(3, numero);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de habitación: " + e.getMessage());
            return false;
        }
    }

    // Habitaciones operativas sin reservas activas/pendientes que choquen con [inicio, fin]
    public List<Integer> buscarNumerosHabitacionesDisponibles(LocalDate inicio, LocalDate fin) {
        String sql = "SELECT h.numero " +
                "FROM habitacion h " +
                "WHERE h.estado IN ('DISPONIBLE', 'RESERVADA') " +
                "AND h.numero NOT IN (" +
                "    SELECT r.numero_habitacion " +
                "    FROM reserva r " +
                "    WHERE r.estado_reserva IN ('PENDIENTE', 'CHECK_IN') " +
                "    AND r.fecha_inicio < ? AND r.fecha_fin > ?" +
                ")";
        List<Integer> disponibles = new ArrayList<>();

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fin));
            ps.setDate(2, Date.valueOf(inicio));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    disponibles.add(rs.getInt("numero"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar habitaciones disponibles: " + e.getMessage());
        }
        return disponibles;
    }
}