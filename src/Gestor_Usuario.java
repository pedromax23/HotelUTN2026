import java.util.Scanner;

public class Gestor_Usuario {

    Base_De_Datos db;

    public void InterfazDeUsuario() {

        // Validaciones
        ValidacionUsuario validacionUsuario = new ValidacionUsuario(db);

        Scanner scanner = new Scanner(System.in);

        boolean flag;
        System.out.println("1. Crear Cuenta. \n\n2. Iniciar Sesion. ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1: {

                // Validacion email
                String email;
                do {

                    System.out.println("Ingrese un email:");
                    email = scanner.nextLine();

                } while (!validacionUsuario.validarEmail(email));

                // Validacion dni
                String dni;
                do {

                    System.out.println("Ingrese su DNI");
                    dni = scanner.nextLine();

                } while (!validacionUsuario.validarDni(dni));

                // Validar contraseña
                String password;
                String confirmPass;
                do {
                    System.out.println("Ingrese la contraseña." +
                            ". Al menos una letra minúscula.\n" +
                            ". Al menos una letra mayúscula.\n" +
                            ". Al menos un número (dígito).\n" +
                            ". Al menos un carácter especial (por ejemplo: @$!%*?&).\n" +
                            ". Una longitud mínima (ej: 8 caracteres).\nAqui:");
                    password = scanner.nextLine();

                    System.out.println("Repita la contraseña: ");
                    confirmPass = scanner.nextLine();

                }while(!validacionUsuario.validarPass(password, confirmPass));

                int passHash = password.hashCode();
                db.registrarUsuario(dni, email, passHash);

                CargaDatos(dni);
            }
            case 2:{
                String contrasenia;
                String email;
                do {
                    int i = 0;
                    System.out.println("Ingrese un email:");
                    email = scanner.nextLine();
                    flag=!(db.NoExisteEmail(email));
                    try {
                        if (flag) {
                            throw new EmailNoValido("El Email no existe");
                        }
                    } catch (EmailNoValido e) {
                        System.out.println(e.getMessage());
                    }
                } while (flag);
                do{
                    System.out.println("Ingrese su contraseña");
                    String contraseña=scanner.nextLine();
                    int password = contraseña.hashCode();
                    flag=(db.ExisteContraseña(password));
                    try {
                        if (flag){
                            throw new ContraseñaNoCoincide("Las contraseñas no coinciden");
                        }
                    }catch (ContraseñaNoCoincide e){
                        System.out.println(e.getMessage());
                    }
                }while (flag);
                System.out.println("se inicio sesion");
            }
        }
    }

    // Si se apaga la pc o no se quieren completar los datos, por eso otro metodo.
    public void CargaDatos(String dni) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su Nombre \n\nIngrese su Apellido\n\nIngrese su sexo\n\nIngrese su edad\n\nIngrese su domicilio");
        String nombre = scanner.nextLine();


        String apellido = scanner.nextLine();
        char sexo = scanner.next().charAt(0);//Mas verificaciones!!!
        int edad = scanner.nextInt();
        String domicilio = scanner.nextLine();
        scanner.nextLine();
        db.registrarDatosUsuario(dni,nombre,apellido,sexo,edad,domicilio);
    }
}
