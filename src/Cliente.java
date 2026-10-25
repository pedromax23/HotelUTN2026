public class Cliente extends Persona {



    public Cliente(String nombre, String apellido, char sexo, String dni, int edad, String email,String domicilio,String contrasenia) {
        super(nombre, apellido, sexo, dni, edad, email, domicilio,contrasenia);
    }
    public Cliente (String email,String contrasenia){
        super(email, contrasenia);
    }
}
