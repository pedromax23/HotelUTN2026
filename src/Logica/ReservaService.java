package Logica;

import Base_de_datos.ClienteDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ReservaDAO;
import Session.Sesion;

import java.time.LocalDate;
import java.util.List;

public class ReservaService {

    public static List<Integer> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return null;
        }
        if (fechaInicio.isBefore(LocalDate.now()) || !fechaFin.isAfter(fechaInicio)) {
            return null;
        }
        return HabitacionDAO.buscarNumerosHabitacionesDisponibles(fechaInicio, fechaFin);
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
        System.out.println(email);
        if (email == null || email.trim().isEmpty()) {
            return "No hay una sesion activa.";
        }

        String dni = ClienteDAO.obtenerDniPorEmail(email);
        if (dni == null) {
            return "No se pudo identificar al cliente.";
        }

        boolean reservada = ReservaDAO.crearReserva(dni, numeroHabitacion, fechaInicio, fechaFin);
        if (!reservada) {
            return "No se pudo realizar la reserva.";
        }

        return "\n¡Reserva realizada correctamente!"
                + "\nHabitacion: " + numeroHabitacion
                + "\nIngreso: " + fechaInicio
                + "\nSalida: " + fechaFin
                + "\nEstado: PENDIENTE";
    }

    public static ResultadoOperacion<Integer> confirmarReservaRecepcionista(String dni, int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        // Verifica que los campos no esten vacios
        if (dni == null || dni.isBlank()) {
            return ResultadoOperacion.error("No se pudo identificar al cliente.");
        }
        if (fechaInicio == null || fechaFin == null || fechaInicio.isBefore(LocalDate.now()) || !fechaFin.isAfter(fechaInicio)) {
            return ResultadoOperacion.error("Las fechas ingresadas no son validas.");
        }

        // Listamos las habitaciones disponibles
        List<Integer> disponibles = HabitacionDAO.buscarNumerosHabitacionesDisponibles(fechaInicio, fechaFin);

        // Verificamos si hay habitaciones disponibles
        if (disponibles == null || disponibles.isEmpty()) {
            return ResultadoOperacion.error("No hay habitaciones disponibles para esas fechas.");
        }
        if (!disponibles.contains(numeroHabitacion)) {
            return ResultadoOperacion.error("La habitacion seleccionada no esta disponible.");
        }

        // Realizamos la reserva
        boolean reservada = ReservaDAO.crearReserva(dni.trim(), numeroHabitacion, fechaInicio, fechaFin);

        // Si la reserva falla guardamos la respuesta de la operacion en la clase genrica
        if (!reservada) {
            return ResultadoOperacion.error("No se pudo realizar la reserva.");
        }

        // Si la reserva se registra guardamos el mensaje en la clase generica
        return ResultadoOperacion.ok("¡Reserva realizada correctamente!", numeroHabitacion);
    }

    public static List<String> listarReservasCliente(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return List.of();
        }
        return ReservaDAO.listarReservasPorCliente(dni);
    }

    public static boolean realizarCheckIn(int idReserva) {
        if (idReserva <= 0) {
            return false;
        }

        return ReservaDAO.realizarCheckIn(idReserva);
    }

    public static boolean realizarCheckOut(int idReserva) {
        if (idReserva <= 0) {
            return false;
        }
        return ReservaDAO.realizarCheckOut(idReserva);
    }

    public static List<String> listarClientesResumen() {
        return ClienteDAO.listarClientesResumen();
    }

    public static List<String> listarHabitacionesNoDisponibles() {
        return HabitacionDAO.listarHabitacionesNoDisponibles();
    }
}
