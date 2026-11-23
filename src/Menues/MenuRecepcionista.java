
package Menues;

import Base_de_datos.ClienteDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ReservaDAO;
import Logica.*;
import Session.Sesion;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuRecepcionista {

    private static final Scanner scanner = new Scanner(System.in);

    public static void menu() {

        int opcion;

        do {
            System.out.println("\nMENU RECEPCIONISTA");
            System.out.println("1. Registrar reserva");
            System.out.println("2. Consultar habitaciones disponibles");
            System.out.println("3. Realizar check-in");
            System.out.println("4. Realizar check-out");
            System.out.println("5. Listar clientes");
            System.out.println("6. Consultar habitaciones no disponibles");
            System.out.println("7. Listar reservas pendientes");
            System.out.println("8. Listar habitaciones ocupadas");
            System.out.println("0. Cerrar sesion");
            System.out.print("Seleccione una opcion: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    registrarReserva();
                    break;
                case 2:
                    consultarDisponibilidad();
                    break;
                case 3:
                    realizarCheckIn();
                    break;
                case 4:
                    realizarCheckOut();
                    break;
                case 5:
                    listarClientes();
                    break;
                case 6:
                    listarHabitacionesNoDisponibles();
                    break;
                case 0:
                    Sesion.cerrarSesion();
                    System.out.println("Sesion cerrada.");
                    break;
                case 7:
                    listarReservasPendientes();
                    break;
                case 8:
                    listarHabitacionesOcupadas();
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void registrarReserva() {

        System.out.println("\nREGISTRAR RESERVA");

        // Pedimos el dni del cliente
        System.out.print("DNI del cliente: ");
        String dni = scanner.nextLine().trim();

        // Verificamos que exista el cliente
        if (!ClienteService.existeCliente(dni)) {
            System.out.println("No existe un cliente con ese DNI.");
            return;
        }

        // Guardamos las fechas de inicio y fin de la reserva
        LocalDate inicio = leerFecha("Fecha de ingreso: ");
        if (inicio == null) return;
        LocalDate fin = leerFecha("Fecha de salida: ");
        if (fin == null) return;

        // Listamos las habitacion disponibles
        List<Integer> disponibles = HabitacionService.buscarHabitacionesDisponibles(inicio, fin);

        // Mostramos las habitaciones disponibles
        System.out.println("\nHabitaciones disponibles:");
        for (Integer numero : disponibles) {
            System.out.println("Habitacion: " + numero);
        }

        // Guarmos la opcion del usuario
        System.out.print("Numero de habitacion: ");
        int numeroHabitacion = leerOpcion();

        // Guardamos la reserva del usuario y registramos la operacion en la clase generica
        ResultadoOperacion<Integer> resultado = ReservaService.confirmarReservaRecepcionista(dni, numeroHabitacion, inicio, fin);
        System.out.println(resultado.getMensaje());
    }

    private static void consultarDisponibilidad() {

        System.out.println("\n--- CONSULTAR DISPONIBILIDAD ---");

        // Pedimos los datos de las fechas
        LocalDate inicio = leerFecha("Fecha de ingreso: ");
        if (inicio == null) return;

        LocalDate fin = leerFecha("Fecha de salida: ");
        if (fin == null) return;

        List<Integer> disponibles = HabitacionService.buscarHabitacionesDisponibles(inicio, fin);
        if (disponibles == null) {
            System.out.println("Periodo invalido.");
            return;
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles.");
            return;
        }

        System.out.println("Habitaciones disponibles:");
        for (Integer numero : disponibles) {
            System.out.println("Habitacion: " + numero);
        }
    }

    private static void realizarCheckIn() {

        System.out.println("\n--- CHECK-IN ---");
        System.out.print("ID de la reserva: ");
        int idReserva = leerOpcion();

        if (idReserva <= 0) {
            System.out.println("ID invalido.");
            return;
        }

        if (ReservaService.realizarCheckIn(idReserva)) {
            System.out.println("Check-in realizado correctamente.");
        } else {
            System.out.println(
                    "No se pudo realizar el check-in. " +
                            "Verifique que la reserva exista y este pendiente."
            );
        }
    }

    private static void realizarCheckOut() {

        System.out.println("\n--- CHECK-OUT ---");
        System.out.print("ID de la reserva: ");
        int idReserva = leerOpcion();

        if (idReserva <= 0) {
            System.out.println("ID invalido.");
            return;
        }

        if (ReservaService.realizarCheckOut(idReserva)) {
            System.out.println("Check-out realizado correctamente.");
        } else {
            System.out.println(
                    "No se pudo realizar el check-out. " +
                            "Verifique que la reserva tenga un check-in activo."
            );
        }
    }

    private static void listarClientes() {

        System.out.println("\n CLIENTES");
        List<String> clientes = ClienteService.listarClientesResumen();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (String cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void listarHabitacionesNoDisponibles() {

        System.out.println("\n--- HABITACIONES NO DISPONIBLES ---");
        List<String> habitaciones = HabitacionService.listarHabitacionesNoDisponibles();

        if (habitaciones == null || habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones fuera de servicio.");
            return;
        }

        for (String habitacion : habitaciones) {
            System.out.println(habitacion);
        }
    }

    private static LocalDate leerFecha(String mensaje) {

        System.out.print(mensaje);
        try {
            return LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Formato incorrecto. Use AAAA-MM-DD.");
            return null;
        }
    }

    private static int leerOpcion() {

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listarReservasPendientes() {

        System.out.println("\n===== RESERVAS PENDIENTES =====");

        List<String> reservas = EmpleadoService.listarReservasPendientes();

        // Verifica que las reservas no esten vacias
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas pendientes.");
            return;
        }

        // Muestra las reservas
        for (String reserva : reservas) {
            System.out.println(reserva);
        }

        System.out.println("\nEstas reservas pueden utilizarse " + "para realizar el check-in.");
    }

    private static void listarHabitacionesOcupadas() {

        System.out.println("\n===== HABITACIONES OCUPADAS =====");

        List<String> habitaciones = HabitacionService.listarHabitacionesOcupadas();

        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones ocupadas actualmente.");
            return;
        }

        for (String habitacion : habitaciones) {
            System.out.println(habitacion);
        }
    }

}

