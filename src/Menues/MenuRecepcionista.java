
package Menues;

import Base_de_datos.ClienteDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ReservaDAO;
import Logica.ClienteService;
import Logica.HabitacionService;
import Logica.ReservaService;
import Session.Sesion;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuRecepcionista {

    private static final Scanner scanner = new Scanner(System.in);
    private final ClienteDAO clienteDAO;
    private final HabitacionDAO habitacionDAO;
    private final ReservaDAO reservaDAO;

    public MenuRecepcionista() {
        this.clienteDAO = new ClienteDAO();
        this.habitacionDAO = new HabitacionDAO();
        this.reservaDAO = new ReservaDAO();
    }

    public void menu() {

        int opcion;

        do {
            System.out.println("\nMENU RECEPCIONISTA");
            System.out.println("1. Registrar reserva");
            System.out.println("2. Consultar habitaciones disponibles");
            System.out.println("3. Realizar check-in");
            System.out.println("4. Realizar check-out");
            System.out.println("5. Listar clientes");
            System.out.println("6. Consultar habitaciones no disponibles");
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
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private void registrarReserva() {

        System.out.println("\nREGISTRAR RESERVA");

        System.out.print("DNI del cliente: ");
        String dni = scanner.nextLine().trim();

        if (!ClienteService.existeCliente(dni)) {
            System.out.println("No existe un cliente con ese DNI.");
            return;
        }

        LocalDate inicio = leerFecha("Fecha de ingreso: ");
        if (inicio == null) return;

        LocalDate fin = leerFecha("Fecha de salida: ");
        if (fin == null) return;

        List<Integer> disponibles = HabitacionService.buscarHabitacionesDisponibles(inicio, fin);
        if (disponibles == null) {
            System.out.println("Las fechas ingresadas no son validas.");
            return;
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles.");
            return;
        }

        System.out.println("\nHabitaciones disponibles:");
        for (Integer numero : disponibles) {
            System.out.println("Habitacion: " + numero);
        }

        System.out.print("Numero de habitacion: ");
        int numeroHabitacion = leerOpcion();

        String resultado = ReservaService.confirmarReserva(numeroHabitacion, inicio, fin);
        System.out.println(resultado);
    }

    private void consultarDisponibilidad() {

        System.out.println("\n--- CONSULTAR DISPONIBILIDAD ---");

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

    private void realizarCheckIn() {

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

    private void realizarCheckOut() {

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

    private void listarClientes() {

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

    private void listarHabitacionesNoDisponibles() {

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

    private LocalDate leerFecha(String mensaje) {

        System.out.print(mensaje);
        try {
            return LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Formato incorrecto. Use AAAA-MM-DD.");
            return null;
        }
    }

    private int leerOpcion() {

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

