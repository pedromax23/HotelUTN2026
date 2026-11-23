import Base_de_datos.ConexionDB;
import Base_de_datos.Iniciar_DB;
import Menues.MenuPrincipal;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {

        //MenuPrincipal.menu();

        // 1. Inicializar la estructura de las tablas si no existen
        Iniciar_DB.inicializar();

        // 2. Iniciar el flujo normal de la aplicación (Menú de Login, etc.)
        System.out.println("\n--- BIENVENIDO AL SISTEMA DE GESTIÓN HOTELERA ---");
        // ... llamado a tus menús o controladores
        MenuPrincipal.menu();

    }

}