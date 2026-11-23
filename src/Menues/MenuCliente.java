
package Menues;

import Base_de_datos.ClienteDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ReservaDAO;
import Logica.ReservaService;
import Session.Sesion;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    private static final Scanner scanner = new Scanner(System.in);

    public static void menu() {

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

    private static void reservarHabitacion() {
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

        List<Integer> disponibles = ReservaService.obtenerHabitacionesDisponibles(fechaInicio, fechaFin);
        if (disponibles == null) {
            System.out.println("Las fechas ingresadas no son validas (ingreso >= hoy y salida > ingreso).");
            return;
        }

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
            numeroHabitacion = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Numero de habitacion invalido.");
            return;
        }

        String resultado = ReservaService.confirmarReserva(numeroHabitacion, fechaInicio, fechaFin);
        System.out.println(resultado);
    }

    private static void consultarReservas() {
        System.out.println("\n===== MIS RESERVAS =====");

        String email = Sesion.recuperarSesion();
        if (email == null || email.trim().isEmpty()) {
            System.out.println("No hay una sesion activa.");
            return;
        }

        String dni = ClienteDAO.obtenerDniPorEmail(email);
        if (dni == null) {
            System.out.println("No se pudo identificar al cliente.");
            return;
        }

        List<String> reservas = ReservaService.listarReservasCliente(dni);

        if (reservas == null || reservas.isEmpty()) {
            System.out.println("No tenes reservas registradas.");
            return;
        }

        for (String reserva : reservas) {
            System.out.println(reserva);
        }
    }
}

