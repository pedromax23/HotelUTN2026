package Exceptions;

public class EmailNoValido extends Exception {

    public EmailNoValido() {
        super("El Email no existe");
    }
}
