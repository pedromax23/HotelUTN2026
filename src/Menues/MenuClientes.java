package Menues;

import Clases.Persona;

import java.util.Scanner;

public class MenuClientes {
    public static void menu(Persona cliente){
        Scanner scanner= new Scanner(System.in);
        System.out.println("1.Registrar habitacion\n "+"2.Cuenta \n"+"3.Salir \n");
        int opcion = scanner.nextInt();
        do{
            switch (opcion){
                case 1: {

                    break;
                }
                case 2:{
                    MenuDatosCliente.menu(cliente);

                    break;
                }
            }

        }while(opcion==3);
    }
}
