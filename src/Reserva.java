public class Reserva {

    private Habitacion habitacion;
    private Cliente cliente;
    private int diasEstadia;
    private int valorEstadia;

    public Reserva(Habitacion habitacion, Cliente cliente, int diasEstadia) {
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.diasEstadia = diasEstadia;
        this.valorEstadia = habitacion.getTipo().getValorDia() * diasEstadia;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getDiasEstadia() {
        return diasEstadia;
    }

    public void setDiasEstadia(int diasEstadia) {
        this.diasEstadia = diasEstadia;
    }

    public int getValorEstadia() {
        return valorEstadia;
    }

    public void setValorEstadia(int valorEstadia) {
        this.valorEstadia = valorEstadia;
    }
}