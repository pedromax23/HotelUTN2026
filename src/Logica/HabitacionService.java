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

    // Funcion para cambiar el estado de la habitacion
    public static ResultadoOperacion<Void> cambiarEstadoHabitacion(int numero, EstadoHabitacion nuevoEstado, String motivo) {

        // Validamos el numero de la habitacion
        if (numero <= 0) {

            // LLamamos al metodo estatico (.error de ResultadoOperacion) y le pasamos el mensaje como parametro
            return ResultadoOperacion.error("Numero de habitacion invalido.");
        }

        // Validamos si el estado que ingresamos existe
        if (nuevoEstado == null) {

            return ResultadoOperacion.error("Estado invalido.");
        }

        // Definimos el motivo en null hasta definirse el estado
        String motivoLimpio = null;

        // Pregunta si el estado es diferente de DISPONIBLE
        if (nuevoEstado != EstadoHabitacion.DISPONIBLE) {

            // Pregunta si el motivo actual esta vacio
            if (motivo == null || motivo.isBlank()) {

                // Usamos el metodo estatico de ResultadoOperacion para crear un error con el mensaje
                return ResultadoOperacion.error("Debe ingresar un motivo para el estado " + nuevoEstado + ".");
            }

            // Guardamos el motivo sin espacios extras
            motivoLimpio = motivo.trim();
        }

        // LLamamos al metodo para hablar con SQL y actualizamos el estado de la habitacion
        boolean actualizado = HabitacionDAO.actualizarEstadoHabitacion(
                numero,
                nuevoEstado.name(),
                motivoLimpio
        );

        //
        if (!actualizado) {
            return ResultadoOperacion.error("No se pudo actualizar la habitacion en la base de datos.");
        }

        return ResultadoOperacion.ok("Estado de la habitacion #" + numero + " actualizado a " + nuevoEstado + ".");
    }

    public static List<String> listarHabitacionesOcupadas() {

        List<String> habitaciones = HabitacionDAO.listarHabitacionesOcupadas();

        return habitaciones;
    }

}
