import java.util.Scanner;

public class Gestor_Usuario {


    public void InterfazDeUsuario(Base_De_Datos db) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Crear Cuenta. \n\n2. Iniciar Sesion. ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1: {
                System.out.println("Ingrese un email \n\nIngrese un DNI\n \nIngrese una contraseña");
                String email = scanner.nextLine(); //Verificar si tiene arroba
                String dni = scanner.nextLine(); //Verificar que no se repita
                String contrasenia = scanner.nextLine(); //Hacer que sea una contraseña segura
                Cliente user = new Cliente(dni, email, contrasenia);
                db.registrarUsuario(dni, email, contrasenia);
                CargaDatos(db,dni);
            }
        }
    }

    public void CargaDatos(Base_De_Datos db,String dni) {//Si se apaga la pc o no se quieren completar los datos, por eso otro metodo.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su Nombre \n\nIngrese su Apellido\n\nIngrese su sexo\n\nIngrese su edad\n\nIngrese su domicilio");
        String nombre = scanner.nextLine();
        String apellido = scanner.nextLine();
        char sexo = scanner.next().charAt(0);
        int edad = scanner.nextInt();
        String domicilio = scanner.nextLine();
        scanner.nextLine();
        db.registrarDatosUsuario(dni,nombre,apellido,sexo,edad,domicilio);
    }
}
