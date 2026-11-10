import Base_de_datos.Base_De_Datos;
import Clases.*;
import Enums.TipoHabitacion;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        Habitacion habitacion1 = new Habitacion(1, true, TipoHabitacion.STANDARD, false);
        Cliente cliente1 = new Cliente("juan","perez",'H',"32333222",22,"perro@gmail.com","maipu3432",43562523);

        Reserva reserva1 = new Reserva(habitacion1, cliente1, 4);


       System.out.println(reserva1.getValorEstadia());
        Gestor_Usuario user = new Gestor_Usuario();

        Connection conexion = Base_De_Datos.CreaConexion();
        user.InterfazDeUsuario();

    }

}