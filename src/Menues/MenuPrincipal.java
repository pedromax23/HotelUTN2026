package Menues;

import Base_de_datos.UsuariosDAO;
import Validaciones.ValidacionUsuario;
import Exceptions.ContraseñaNoCoincide;
import Exceptions.EmailNoValido;

import java.util.Scanner;

public class MenuPrincipal {

    public static void menu() {

        Scanner sc = new Scanner(System.in);

        System.out.println("1 - Crear Cuenta\n2 - Iniciar Sesion");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1: {

                MenuUsuario.crearCuenta();

                MenuUsuario.iniciarSession();

            }
            case 2:{

                MenuUsuario.iniciarSession();

            }
        }
    }
}
