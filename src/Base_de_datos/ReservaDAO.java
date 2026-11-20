package Base_de_datos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public boolean crearReserva(String dniCliente, int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        String sql = "INSERT INTO reserva (dni_cliente, numero_habitacion, fecha_inicio, fecha_fin, estado_reserva) " +
                "VALUES (?, ?, ?, ?, 'PENDIENTE')";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dniCliente);
            ps.setInt(2, numeroHabitacion);
            ps.setDate(3, Date.valueOf(fechaInicio));
            ps.setDate(4, Date.valueOf(fechaFin));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear reserva: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarReservasPorCliente(String dniCliente) {
        String sql = "SELECT r.id_reserva, r.numero_habitacion, r.fecha_inicio, r.fecha_fin, r.estado_reserva, " +
                "c.nombre, c.apellido " +
                "FROM reserva r " +
                "JOIN cliente c ON r.dni_cliente = c.dni " +
                "WHERE r.dni_cliente = ? " +
                "ORDER BY r.fecha_inicio DESC";
        List<String> reservas = new ArrayList<>();

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dniCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String linea = String.format(
                            "Reserva #%d | Hab. %d | %s %s | %s al %s | Estado: %s",
                            rs.getInt("id_reserva"),
                            rs.getInt("numero_habitacion"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getDate("fecha_inicio").toLocalDate(),
                            rs.getDate("fecha_fin").toLocalDate(),
                            rs.getString("estado_reserva")
                    );
                    reservas.add(linea);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar reservas del cliente: " + e.getMessage());
        }
        return reservas;
    }

    public boolean actualizarEstadoReserva(int idReserva, String nuevoEstado) {
        String sql = "UPDATE reserva SET estado_reserva = ? WHERE id_reserva = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idReserva);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de reserva: " + e.getMessage());
            return false;
        }
    }
}