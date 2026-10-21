public class Main {

    public static void main(String[] args) {

        Habitacion habitacion1 = new Habitacion(1, true, TipoHabitacion.STANDARD, false);
        Cliente cliente1 = new Cliente("Pedro", "Morgade", 'M', "45423447", 22, "pedro@gmail.com", "Av.independencia");
        Reserva reserva1 = new Reserva(habitacion1, cliente1, 4);


        System.out.println(reserva1.getValorEstadia());

    }

}