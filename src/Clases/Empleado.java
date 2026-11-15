package Clases;

public abstract class Empleado extends Persona {

    private double sueldo;

    public Empleado(String nombre, String apellido, char sexo, String dni, int edad, String email, String domicilio, int password, double sueldo) {
        super(nombre, apellido, sexo, dni, edad, email, domicilio, password);
        this.sueldo = sueldo;
    }

    public double getSueldo() { return sueldo; }

    public void setSueldo(float sueldo) { this.sueldo = sueldo; }

    public Empleado(String dni, String email, int contrasenia, float sueldo) {
        super(dni, email, contrasenia);
        this.sueldo = sueldo;
    }

    public Empleado(String nombre, String apellido, char sexo, String dni, int edad, String email, String domicilio, int password, float sueldo) {
        super(nombre, apellido, sexo, dni, edad, email, domicilio, password);
        this.sueldo = sueldo;
    }
}
