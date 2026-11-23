package Logica;

import Base_de_datos.HabitacionDAO;
import Enums.EstadoHabitacion;

import java.util.List;

public class HabitacionService {

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
