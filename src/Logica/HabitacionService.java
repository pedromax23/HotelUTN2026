package Logica;

import Base_de_datos.HabitacionDAO;
import Enums.EstadoHabitacion;

import java.time.LocalDate;
import java.util.List;

public class HabitacionService {

    public static boolean fechasValidas(LocalDate inicio, LocalDate fin) {
        return inicio != null && fin != null
                && !inicio.isBefore(LocalDate.now())
                && fin.isAfter(inicio);
    }

    public static List<Integer> buscarHabitacionesDisponibles(LocalDate inicio, LocalDate fin) {
        if (!fechasValidas(inicio, fin)) {
            return null;
        }
        return HabitacionDAO.buscarNumerosHabitacionesDisponibles(inicio, fin);
    }

    public static List<String> listarHabitacionesNoDisponibles() {
        return HabitacionDAO.listarHabitacionesNoDisponibles();
    }

    public static ResultadoOperacion<Void> cambiarEstadoHabitacion(int numero, EstadoHabitacion nuevoEstado, String motivo) {
        if (numero <= 0) {
            return ResultadoOperacion.error("Numero de habitacion invalido.");
        }

        if (nuevoEstado == null) {
            return ResultadoOperacion.error("Estado invalido.");
        }

        String motivoLimpio = null;

        if (nuevoEstado != EstadoHabitacion.DISPONIBLE) {
            if (motivo == null || motivo.isBlank()) {
                return ResultadoOperacion.error("Debe ingresar un motivo para el estado " + nuevoEstado + ".");
            }
            motivoLimpio = motivo.trim();
        }

        boolean actualizado = HabitacionDAO.actualizarEstadoHabitacion(
                numero,
                nuevoEstado.name(),
                motivoLimpio
        );

        if (!actualizado) {
            return ResultadoOperacion.error("No se pudo actualizar la habitacion en la base de datos.");
        }

        return ResultadoOperacion.ok("Estado de la habitacion #" + numero + " actualizado a " + nuevoEstado + ".");
    }

}
