package Logica;

import Base_de_datos.ClienteDAO;
import Base_de_datos.EmpleadoDAO;
import Menues.MenuAdministrador;
import Menues.MenuCliente;
import Menues.MenuPrincipal;
import Menues.MenuRecepcionista;
import Session.Sesion;

import java.util.Scanner;

public class AuthService {

    private static final ClienteDAO clienteDAO = new ClienteDAO();

    public static void login(String email, String contrasenia) {

        if (email.isEmpty() || contrasenia.isEmpty()) {
            System.out.println("Debe completar todos los campos.");
            return;
        }

        // Primero comprobamos si es empleado
        String rol = EmpleadoDAO.obtenerRolSiCredencialesValidas(
                email, contrasenia);

        if (rol != null) {

            Sesion.guardarSesion(email);

            System.out.println("Bienvenido/a.");
            System.out.println("Inicio de sesion como: " + rol);

            switch (rol.toUpperCase()) {

                case "ADMINISTRADOR":
                    MenuAdministrador menuAdmin =
                            new MenuAdministrador();
                    menuAdmin.menu();
                    break;

                case "RECEPCIONISTA":
                    MenuRecepcionista menuRecepcionista =
                            new MenuRecepcionista();
                    menuRecepcionista.menu();
                    break;

                default:
                    System.out.println("Rol no reconocido.");
            }

            return;
        }


        // Si no es empleado, comprobamos si es cliente
        if (clienteDAO.validarCredenciales(email, contrasenia)) {

            Sesion.guardarSesion(email);

            System.out.println("Bienvenido/a, cliente.");

            MenuPrincipal.menu();

        } else {

            System.out.println("Email o contrasena incorrectos.");
        }
    }

    public static void register(String dni, String email, String contrasenia, String confirmacion) {
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

}
