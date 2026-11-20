package Menues;

import Base_de_datos.ClienteDAO;
import Clases.Persona;
import Session.Sesion;

import java.util.Scanner;

public class MenuPrincipal {
    private static Scanner sc = new Scanner(System.in);

    public static void menu() {

        String email = Sesion.recuperarSesion();

        if (email == null){

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
        }else {
            Persona persona = ClienteDAO.buscarPorEmail(email);
            MenuClientes.menu(persona);

        }


    }
}
