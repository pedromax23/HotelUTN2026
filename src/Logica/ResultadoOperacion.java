package Logica; // o Util

public class ResultadoOperacion<T> {
    private final boolean exito;
    private final String mensaje;
    private final T dato;

    public ResultadoOperacion(boolean exito, String mensaje, T dato) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.dato = dato;
    }

    public static <T> ResultadoOperacion<T> ok(String mensaje, T dato) {
        return new ResultadoOperacion<>(true, mensaje, dato);
    }

    public static <T> ResultadoOperacion<T> ok(String mensaje) {

        // Creamos un objeto con la informacion de la operacion. exito, mensaje, y dato en caso de devolverlo
        return new ResultadoOperacion<>(true, mensaje, null);
    }

    public static <T> ResultadoOperacion<T> error(String mensaje) {
        return new ResultadoOperacion<>(false, mensaje, null);
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public T getDato() {
        return dato;
    }
}