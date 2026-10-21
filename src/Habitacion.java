public class Habitacion {

    private int numeroHabitacion;
    private TipoHabitacion tipo;
    private boolean disponible;
    private boolean enUso;

    public Habitacion(int numeroHabitacion, boolean disponible, TipoHabitacion tipo, boolean enUso) {
        this.numeroHabitacion = numeroHabitacion;
        this.disponible = disponible;
        this.tipo = tipo;
        this.enUso = enUso;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public boolean isEnUso() {
        return enUso;
    }

    public void setEnUso(boolean enUso) {
        this.enUso = enUso;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }
}
