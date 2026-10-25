public class Main {

    public static void main(String[] args) {

        Habitacion habitacion1 = new Habitacion(1, true, TipoHabitacion.STANDARD, false);
        Cliente cliente1 = new Cliente("juan","perez",'H',"32333222",22,"perro@gmail.com","maipu3432","43562523");

        Reserva reserva1 = new Reserva(habitacion1, cliente1, 4);


       System.out.println(reserva1.getValorEstadia());
        Gestor_Usuario user = new Gestor_Usuario();

        Base_De_Datos db = new Base_De_Datos();
        db.CreaConexion();
        db.crearTablasHotel();
        user.InterfazDeUsuario();
    }

}