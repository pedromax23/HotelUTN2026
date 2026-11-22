
package Menues;

import Base_de_datos.ClienteDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ReservaDAO;
import Session.Sesion;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    private final Scanner scanner;
    private final ClienteDAO clienteDAO;
    private final HabitacionDAO habitacionDAO;
    private final ReservaDAO reservaDAO;

    public MenuCliente(Scanner scanner) {
        this.scanner = scanner;
        this.clienteDAO = new ClienteDAO();
        this.habitacionDAO = new HabitacionDAO();
        this.reservaDAO = new ReservaDAO();
    }

    public void menu() {

        int opcion;

        do {
            System.out.println("\nMENU CLIENTE");
            System.out.println("1. Reservar habitacion");
            System.out.println("2. Consultar mis reservas");
            System.out.println("0. Cerrar sesion");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {

                    case 1:
                        reservarHabitacion();
                        break;

                    case 2:
                        consultarReservas();
                        break;

                    case 0:
                        Sesion.cerrarSesion();
                        System.out.println("Sesion cerrada.");
                        break;

                    default:
                        System.out.println("Opcion invalida.");
                }

            } catch (NumberFormatException e) {
                opcion = -1;
                System.out.println("Debe ingresar un numero.");
            }

        } while (opcion != 0);
    }

    private void reservarHabitacion() {

        System.out.println("\nRESERVAR HABITACION");

        System.out.print("Fecha de ingreso (AAAA-MM-DD): ");
        LocalDate fechaInicio;

        try {
            fechaInicio = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto.");
            return;
        }

        System.out.print("Fecha de salida (AAAA-MM-DD): ");
        LocalDate fechaFin;

        try {
            fechaFin = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto.");
            return;
        }

        // Validar fechas
        if (!fechaInicio.isAfter(LocalDate.now().minusDays(1))) {
            System.out.println("La fecha de ingreso debe ser hoy o posterior.");
            return;
        }

        if (!fechaFin.isAfter(fechaInicio)) {
            System.out.println("La salida debe ser posterior al ingreso.");
            return;
        }

        // Buscar habitaciones disponibles
        List<Integer> disponibles =
                habitacionDAO.buscarNumerosHabitacionesDisponibles(
                        fechaInicio, fechaFin
                );

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para esas fechas.");
            return;
        }

        System.out.println("\nHabitaciones disponibles:");

        for (Integer numero : disponibles) {
            System.out.println("Habitacion numero: " + numero);
        }

        System.out.print("\nIngrese el numero de habitacion que desea reservar: ");

        int numeroHabitacion;

        try {
            numeroHabitacion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Numero de habitacion invalido.");
            return;
        }

        // Comprobar que la habitación elegida esté disponible
        if (!disponibles.contains(numeroHabitacion)) {
            System.out.println("La habitacion seleccionada no esta disponible.");
            return;
        }

        // Recuperar el usuario de la sesion
        String email = Sesion.recuperarSesion();

        if (email == null || email.isEmpty()) {
            System.out.println("No hay una sesion activa.");
            return;
        }

        String dni = clienteDAO.obtenerDniPorEmail(email);

        if (dni == null) {
            System.out.println("No se pudo identificar al cliente.");
            return;
        }

        // Crear reserva
        boolean reservada = reservaDAO.crearReserva(
                dni,
                numeroHabitacion,
                fechaInicio,
                fechaFin
        );

        if (reservada) {
            System.out.println("\n¡Reserva realizada correctamente!");
            System.out.println("Habitacion: " + numeroHabitacion);
            System.out.println("Ingreso: " + fechaInicio);
            System.out.println("Salida: " + fechaFin);
            System.out.println("Estado: PENDIENTE");

        } else {
            System.out.println("No se pudo realizar la reserva.");
        }
    }

    private void consultarReservas() {

        System.out.println("\n===== MIS RESERVAS =====");

        String email = Sesion.recuperarSesion();

        if (email == null || email.isEmpty()) {
            System.out.println("No hay una sesion activa.");
            return;
        }

        String dni = clienteDAO.obtenerDniPorEmail(email);

        if (dni == null) {
            System.out.println("No se pudo identificar al cliente.");
            return;
        }

        List<String> reservas =
                reservaDAO.listarReservasPorCliente(dni);

        if (reservas.isEmpty()) {
            System.out.println("No tenes reservas registradas.");
            return;
        }

        for (String reserva : reservas) {
            System.out.println(reserva);
        }
    }
}

