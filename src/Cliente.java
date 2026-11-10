public class Cliente extends Persona {



    public Cliente(String nombre, String apellido, char sexo, String dni, int edad, String email, String domicilio, int password) {
        super(nombre, apellido, sexo, dni, edad, email, domicilio, password);
    }
    public Cliente (String dni, String email, int password){
        super(dni,email, password);
    }
}
