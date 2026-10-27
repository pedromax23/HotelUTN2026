import java.util.Scanner;

public class Gestor_Usuario {


    public void InterfazDeUsuario(Base_De_Datos db) {
        Scanner scanner = new Scanner(System.in);
        boolean flag;
        boolean flag2;
        System.out.println("1. Crear Cuenta. \n\n2. Iniciar Sesion. ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1: {
                String contrasenia;
                String email;
                String dni;

                do {//Controla que el Email no se repita, y tenga arroba
                    flag = false;
                    int i = 0;
                    System.out.println("Ingrese un email:");
                    email = scanner.nextLine();

                        while (i < email.length() && !flag) {
                            if (email.charAt(i) == '@')
                                flag = true;
                            i++;
                        }

                        flag2=db.NoExisteEmail(email);
                        try {
                            if ((!flag) || (!flag2)) {
                                throw new EmailNoValido("El email no es valido");
                            }
                        } catch (EmailNoValido e) {
                            System.out.println(e.getMessage());
                        }
                } while ((!flag) || (!flag2));

                do {//Controla que no se repita el DNI
                    System.out.println("Ingrese su DNI");
                    dni = scanner.nextLine();
                    flag=db.NoExisteDni(dni);
                    try {
                        if (!flag) {
                            throw new DniExiste("Ese DNI ya se registro");
                        }
                    }catch (DniExiste e) {
                        System.out.println(e.getMessage());
                    }
                }while(!flag);

                do {//Pide dos veces la contraseña
                    System.out.println("Ingrese la contraseña");
                    flag=true;
                    contrasenia = scanner.nextLine();
                    System.out.println("Repita la contraseña");
                    String contrasenianueva=scanner.nextLine();
                    flag=contrasenia.equals(contrasenianueva);
                    try {
                        if (!flag){
                            throw new ContraseñaNoCoincide("Las contraseñas no coinciden");
                        }
                    }catch (ContraseñaNoCoincide e){
                        System.out.println(e.getMessage());
                    }
                }while(!flag);

                int passsword=contrasenia.hashCode();
                Cliente user = new Cliente(dni, email, contrasenia);
                db.registrarUsuario(dni, email, passsword);
                CargaDatos(db,dni);
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

    public void CargaDatos(Base_De_Datos db,String dni) {//Si se apaga la pc o no se quieren completar los datos, por eso otro metodo.
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
