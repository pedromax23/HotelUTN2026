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

    // Metodo de login que recibe el email y la contraseña
    public static void login(String email, String contrasenia) {

        // Comprobamos si los campos estan vacios
        if (email.isEmpty() || contrasenia.isEmpty()) {
            System.out.println("Debe completar todos los campos.");
            return;
        }

        // Comprobamos si es empleado
        String rol = EmpleadoDAO.obtenerRolSiCredencialesValidas(email, contrasenia);

        // Si el rol es diferente de null
        if (rol != null) {

            // Guardamos el email en el archivo de la session
            Sesion.guardarSesion(email);

            System.out.println("Bienvenido/a.");
            System.out.println("Inicio de sesion como: " + rol);

            // Verificamos el rol del usuario con un switch y redirigimos al menu correspondiente
            switch (rol.toUpperCase()) {

                case "ADMINISTRADOR":
                    MenuAdministrador.menu();
                    break;

                case "RECEPCIONISTA":
                    MenuRecepcionista.menu();
                    break;

                default:
                    System.out.println("Rol no reconocido.");
            }

            return;
        }


        // Si no encuentra ningun empleado, comprobamos si es cliente
        if (ClienteDAO.validarCredenciales(email, contrasenia)) {

            // Guardamos el email en el archivo de la session
            Sesion.guardarSesion(email);

            System.out.println("Bienvenido/a, cliente.");

            // Redirigimos al menu cliente
            MenuCliente.menu();

        } else {

            System.out.println("Email o contrasena incorrectos.");
        }
    }

    public static void register(String dni, String email, String contrasenia, String confirmacion) {

        // Comprobamos si los campos estan vacios
        if (dni.isEmpty() || email.isEmpty()) {
            System.out.println("El DNI y el email son obligatorios.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("El email no tiene un formato valido.");
            return;
        }

        // Comprueba si el DNI esta repetido
        if (ClienteDAO.existePorDni(dni)) {
            System.out.println("El DNI ya se encuentra registrado.");
            return;
        }

        // Comprueba si el email ya existe
        if (ClienteDAO.existePorEmail(email)) {
            System.out.println("El email ya se encuentra registrado.");
            return;
        }

        // Verifica que no este vacia la contraseña
        if (contrasenia.isEmpty()) {
            System.out.println("La contrasena no puede estar vacia.");
            return;
        }

        // Comprara las contraseñas
        if (!contrasenia.equals(confirmacion)) {
            System.out.println("Las contrasenas no coinciden.");
            return;
        }

        // Registrar cliente
        if (ClienteDAO.registrarClienteBasico(dni, email, contrasenia)) {

            System.out.println("Registro exitoso.");
            System.out.println("Ya puede iniciar sesion.");

        } else {

            System.out.println("No se pudo completar el registro.");
        }
    }

}
