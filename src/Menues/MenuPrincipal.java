
package Menues;

import Base_de_datos.ClienteDAO;
import Base_de_datos.EmpleadoDAO;
import Logica.AuthService;
import Session.Sesion;

import java.util.Scanner;

public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);

    public static void menu() {
        int opcion;

        do {
            System.out.println(" SISTEMA DE GESTION HOTELERA");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse como cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = leerOpcion(); // pedimos la opcion al usuario

            // switch que controla la opcion del usuario y llama a los metodos correspondientes
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

    // Metodo que pide los datos para iniciar session y llama a su servicio
    private static void iniciarSesion() {

        // Peticion de datos
        System.out.println("\n\tINICIAR SESION ");

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Contrasena: ");
        String contrasenia = scanner.nextLine();

        // Usamos el servicio que creamos para separar la logica
        AuthService.login(email, contrasenia);

    }

    private static void registrarse() {

        // Peticion de datos
        System.out.println("\n\tREGISTRO DE CLIENTE");

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Contrasena: ");
        String contrasenia = scanner.nextLine();

        System.out.print("Repita la contrasena: ");
        String confirmacion = scanner.nextLine();

        // Usamos el servicio que creamos para separar la logica
        AuthService.register(dni, email, contrasenia, confirmacion);
    }

    // Funcion para leer la opcion del usuario
    private static int leerOpcion() {

        String entrada = scanner.nextLine();

        try {
            return Integer.parseInt(entrada);

        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
