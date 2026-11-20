
package Menues;

import Base_de_datos.ClienteDAO;
import Base_de_datos.EmpleadoDAO;
import Session.Sesion;

import java.util.Scanner;

public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public static void menu() {
        int opcion;

        do {
            System.out.println(" SISTEMA DE GESTION HOTELERA");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse como cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;

                case 2:
                    registrarse();
                    break;

                case 0:
                    System.out.println("Gracias por utilizar el sistema.");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void iniciarSesion() {

        System.out.println("\n\tINICIAR SESION ");

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Contrasena: ");
        String contrasenia = scanner.nextLine();

        if (email.isEmpty() || contrasenia.isEmpty()) {
            System.out.println("Debe completar todos los campos.");
            return;
        }

        // Primero comprobamos si es empleado
        String rol = empleadoDAO.obtenerRolSiCredencialesValidas(
                email, contrasenia);

        if (rol != null) {

            Sesion.guardarSesion(email);

            System.out.println("Bienvenido/a.");
            System.out.println("Inicio de sesion como: " + rol);

            return;
        }

        // Si no es empleado, comprobamos si es cliente
        if (clienteDAO.validarCredenciales(email, contrasenia)) {

            Sesion.guardarSesion(email);

            System.out.println("Bienvenido/a, cliente.");

        } else {

            System.out.println("Email o contrasena incorrectos.");
        }
    }

    private static void registrarse() {

        System.out.println("\n\tREGISTRO DE CLIENTE");

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        if (dni.isEmpty() || email.isEmpty()) {
            System.out.println("El DNI y el email son obligatorios.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("El email no tiene un formato valido.");
            return;
        }

        // Comprobar DNI
        if (clienteDAO.existePorDni(dni)) {
            System.out.println("El DNI ya se encuentra registrado.");
            return;
        }

        // Comprobar email
        if (clienteDAO.existePorEmail(email)) {
            System.out.println("El email ya se encuentra registrado.");
            return;
        }

        System.out.print("Contrasena: ");
        String contrasenia = scanner.nextLine();

        System.out.print("Repita la contrasena: ");
        String confirmacion = scanner.nextLine();

        if (contrasenia.isEmpty()) {
            System.out.println("La contrasena no puede estar vacia.");
            return;
        }

        if (!contrasenia.equals(confirmacion)) {
            System.out.println("Las contrasenas no coinciden.");
            return;
        }

        // Registrar cliente
        if (clienteDAO.registrarClienteBasico(
                dni, email, contrasenia)) {

            System.out.println("Registro exitoso.");
            System.out.println("Ya puede iniciar sesion.");

        } else {

            System.out.println("No se pudo completar el registro.");
        }
    }

    private static int leerOpcion() {

        String entrada = scanner.nextLine();

        try {
            return Integer.parseInt(entrada);

        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
