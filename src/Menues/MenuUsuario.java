package Menues;

import Base_de_datos.UsuariosDAO;
import Exceptions.ContraseñaNoCoincide;
import Exceptions.EmailNoValido;
import Interfaces.IPersonaDAO;
import Validaciones.ValidacionUsuario;

import java.util.Scanner;

public class MenuUsuario {

    public static final Scanner sc = new Scanner(System.in);

    public static void crearCuenta() {

        String email;
        do {

            System.out.println("Ingrese un email: ");
            email = sc.nextLine();
            sc.nextLine();

        } while (!ValidacionUsuario.validarEmail(email));

        String dni;
        do {

            System.out.println("Ingrese su DNI: ");
            dni = sc.nextLine();
            sc.nextLine();

        } while (!ValidacionUsuario.validarDni(dni));

        String password;
        String confirmPass;
        do {
            System.out.println("Ingrese la contraseña." +
                    ". Al menos una letra minúscula.\n" +
                    ". Al menos una letra mayúscula.\n" +
                    ". Al menos un número (dígito).\n" +
                    ". Al menos un carácter especial (por ejemplo: @$!%*?&).\n" +
                    ". Una longitud mínima (ej: 8 caracteres).\nAqui:");
            password = sc.nextLine();
            sc.nextLine();

            System.out.println("Repita la contraseña: ");
            confirmPass = sc.nextLine();
            sc.nextLine();

        }while(!ValidacionUsuario.validarPass(password, confirmPass));

        int passHash = password.hashCode();
        UsuariosDAO.registrarUsuario(dni, email, passHash);

        CargaDatos(dni);

    }

    public static void CargaDatos(String dni) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese su Nombre: ");
        String nombre = sc.nextLine();
        sc.nextLine();

        System.out.println("Ingrese su Apellido: ");
        String apellido = sc.nextLine();
        sc.nextLine();

        System.out.println("Ingrese su sexo: ");
        char sexo = sc.next().charAt(0);
        sc.nextLine();

        System.out.println("Ingrese su edad: ");
        int edad = sc.nextInt();
        sc.nextLine();

        System.out.println("Ingrese su domicilio: ");
        String domicilio = sc.nextLine();
        sc.nextLine();

        UsuariosDAO.registrarDatosUsuario(dni,nombre,apellido,sexo,edad,domicilio);
    }

    public static void iniciarSession() {

        boolean flag;
        String contrasenia;
        String email;

        do {

            int i = 0;
            System.out.println("Ingrese un email: ");
            email = sc.nextLine();
            sc.nextLine();

            flag =! (UsuariosDAO.NoExisteEmail(email));

            try {
                if (flag) {
                    throw new EmailNoValido();
                }
            } catch (EmailNoValido e) {
                System.out.println(e.getMessage());
            }

        } while (flag);

        do{
            System.out.println("Ingrese su contraseña:");
            contrasenia = sc.nextLine();
            sc.nextLine();

            int password = contrasenia.hashCode();

            flag=(IPersonaDAO.ExisteContraseña(password));

            try {
                if (flag){
                    throw new ContraseñaNoCoincide();
                }
            }catch (ContraseñaNoCoincide e){
                System.out.println(e.getMessage());
            }

        }while (flag);

        System.out.println("Iniciando session...");

    }

}
