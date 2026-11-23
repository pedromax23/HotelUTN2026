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

    public static boolean cambiarEstadoHabitacion(int numero, EstadoHabitacion nuevoEstado, String motivo) {
        if (numero <= 0 || nuevoEstado == null) {
            return false;
        }

        String motivoLimpio = null;

        if (nuevoEstado != EstadoHabitacion.DISPONIBLE) {
            if (motivo == null || motivo.isBlank()) {
                return false;
            }
            motivoLimpio = motivo.trim();
        }

        return HabitacionDAO.actualizarEstadoHabitacion(
                numero,
                nuevoEstado.name(),
                motivoLimpio
        );
    }

}
