package Logica;

import Base_de_datos.ClienteDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ReservaDAO;
import Session.Sesion;

import java.time.LocalDate;
import java.util.List;

public class ReservaService {

    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final HabitacionDAO habitacionDAO = new HabitacionDAO();
    private static final ReservaDAO reservaDAO = new ReservaDAO();

    public static List<Integer> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return null;
        }
        if (fechaInicio.isBefore(LocalDate.now()) || !fechaFin.isAfter(fechaInicio)) {
            return null;
        }
        return habitacionDAO.buscarNumerosHabitacionesDisponibles(fechaInicio, fechaFin);
    }

    public static String confirmarReserva(int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Integer> disponibles = obtenerHabitacionesDisponibles(fechaInicio, fechaFin);
        if (disponibles == null) {
            return "Las fechas ingresadas no son validas.";
        }
        if (!disponibles.contains(numeroHabitacion)) {
            return "La habitacion seleccionada no esta disponible.";
        }

        String email = Sesion.recuperarSesion();
        if (email == null || email.trim().isEmpty()) {
            return "No hay una sesion activa.";
        }

        String dni = clienteDAO.obtenerDniPorEmail(email);
        if (dni == null) {
            return "No se pudo identificar al cliente.";
        }

        boolean reservada = reservaDAO.crearReserva(dni, numeroHabitacion, fechaInicio, fechaFin);
        if (!reservada) {
            return "No se pudo realizar la reserva.";
        }

        return "\n¡Reserva realizada correctamente!"
                + "\nHabitacion: " + numeroHabitacion
                + "\nIngreso: " + fechaInicio
                + "\nSalida: " + fechaFin
                + "\nEstado: PENDIENTE";
    }

    public static List<String> listarReservasCliente(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return List.of();
        }
        return reservaDAO.listarReservasPorCliente(dni);
    }
}
