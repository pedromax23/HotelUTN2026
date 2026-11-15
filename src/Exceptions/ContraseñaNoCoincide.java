package Exceptions;

public class ContraseñaNoCoincide extends Exception {
    public ContraseñaNoCoincide() {
        super("Las contraseñas no coinciden");
    }
}
