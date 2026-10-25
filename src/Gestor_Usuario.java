import java.util.Scanner;

public class Gestor_Usuario {


    public void InterfazDeUsuario(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Crear Cuenta. \n\n2. Iniciar Sesion. ");
        int opcion=scanner.nextInt();
        switch (opcion){
            case 1:{
                System.out.println("Ingrese un email \n\nIngrese un nombre de usuario\n \nIngrese una contraseña");
                String email = scanner.nextLine(); //Verificar si tiene arroba
                String contrasenia = scanner.nextLine(); //Hacer que sea una contraseña segura
                Cliente user = new Cliente(email,contrasenia);

            }
        }
    }
}
