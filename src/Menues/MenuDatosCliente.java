package Menues;

import Clases.Persona;

import java.util.Scanner;

public class MenuDatosCliente {
    public static void menu(Persona cliente){
        int opcion=0;
        Scanner scanner= new Scanner(System.in);
        System.out.println("1.Salir \n");
        System.out.println(cliente.toString());
        do{
            opcion = scanner.nextInt();
        }while(opcion!=1);
    }
}
