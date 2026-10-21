public enum TipoHabitacion {

    STANDARD("Standar", 50, 2),
    PREMIUN("Premiun", 70, 4),
    CONTINENTAL("Continental", 150, 5),
    PRESIDENCIAL("Presidencial", 300, 6);

    private final String tipo;
    private final int valorDia;
    private final int capacidad;

    TipoHabitacion(String tipo, int valorDia, int capacidad) {
        this.tipo = tipo;
        this.valorDia = valorDia;
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public int getValorDia() {
        return valorDia;
    }

    public int getCapacidad() {
        return capacidad;
    }
}
