
package Menues;

import Base_de_datos.ClienteDAO;
import Base_de_datos.EmpleadoDAO;
import Logica.AuthService;
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

        AuthService.login(email, contrasenia);

    }

    private static void registrarse() {

        System.out.println("\n\tREGISTRO DE CLIENTE");

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Contrasena: ");
        String contrasenia = scanner.nextLine();

        System.out.print("Repita la contrasena: ");
        String confirmacion = scanner.nextLine();

        AuthService.register(dni, email, contrasenia, confirmacion);
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
