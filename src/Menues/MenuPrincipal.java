package Menues;

import Base_de_datos.UsuariosDAO;
import Clases.Persona;

import java.util.Scanner;

public class MenuPrincipal {

    public static void menu() {

        Scanner sc = new Scanner(System.in);
        String email = Sesion.recuperarSesion();
        if (email.equals(null)){
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
            Persona persona = UsuariosDAO.buscarPorEmail(email);
            MenuClientes.menu(persona);

        }


    }
}
