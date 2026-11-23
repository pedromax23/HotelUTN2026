package Clases;

import Enums.EstadoReserva;

import java.util.Date;

public class Reserva {

    private String dni_cliente;
    private int numero_habitacion;
    private Date fecha_inicio;
    private Date fecha_final;
    private EstadoReserva estado_reserva;

    public Reserva(String dni_cliente, int numero_habitacion, Date fecha_inicio, Date fecha_final, EstadoReserva estado_reserva) {
        this.dni_cliente = dni_cliente;
        this.numero_habitacion = numero_habitacion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.estado_reserva = estado_reserva;
    }

    public String getDni_cliente() { return dni_cliente; }

    public void setDni_cliente(String dni_cliente) { this.dni_cliente = dni_cliente; }

    public int getNumero_habitacion() { return numero_habitacion; }

    public void setNumero_habitacion(int numero_habitacion) { this.numero_habitacion = numero_habitacion; }

    public Date getFecha_inicio() { return fecha_inicio; }

    public void setFecha_inicio(Date fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public Date getFecha_final() { return fecha_final; }

    public void setFecha_final(Date fecha_final) { this.fecha_final = fecha_final; }

    public EstadoReserva getEstado_reserva() { return estado_reserva; }

    public void setEstado_reserva(EstadoReserva estado_reserva) { this.estado_reserva = estado_reserva; }


}