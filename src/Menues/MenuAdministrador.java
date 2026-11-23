
package Menues;

import Base_de_datos.EmpleadoDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ClienteDAO;
import Enums.EstadoHabitacion;
import Enums.RolEnum;
import Exceptions.DniExiste;
import Exceptions.EmailNoValido;
import Logica.ClienteService;
import Logica.EmpleadoService;
import Logica.HabitacionService;
import Session.Sesion;

import java.util.List;
import java.util.Scanner;

public class MenuAdministrador {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private static final HabitacionDAO habitacionDAO = new HabitacionDAO();
    private static final ClienteDAO clienteDAO = new ClienteDAO();

    public void menu() throws EmailNoValido {

        int opcion;

        do {
            System.out.println("\nMENU ADMINISTRADOR");
            System.out.println("1. Crear empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Modificar rol de empleado");
            System.out.println("4. Consultar habitaciones no disponibles");
            System.out.println("5. Modificar estado de habitacion");
            System.out.println("6. Listar clientes");
            System.out.println("0. Cerrar sesion");
            System.out.print("Seleccione una opcion: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    crearEmpleado();
                    break;
                case 2:
                    listarEmpleados();
                    break;
                case 3:
                    modificarRol();
                    break;
                case 4:
                    listarHabitacionesNoDisponibles();
                    break;
                case 5:
                    modificarEstadoHabitacion();
                    break;
                case 6:
                    listarClientes();
                    break;
                case 0:
                    Sesion.cerrarSesion();
                    System.out.println("Sesion cerrada.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private void crearEmpleado() throws EmailNoValido {
        System.out.println("\n--- CREAR EMPLEADO ---");

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Contrasena: ");
        String contrasenia = scanner.nextLine();

        RolEnum rol = leerRolEmpleado();
        if (rol == null) {
            System.out.println("Rol invalido.");
            return;
        }

        try {
            boolean creado = EmpleadoService.registrarEmpleado(
                    dni, nombre, apellido, email, contrasenia, rol
            );
            System.out.println(creado
                    ? "Empleado creado correctamente."
                    : "No se pudo crear el empleado.");
        } catch (EmailNoValido | DniExiste | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarEmpleados() {

        System.out.println("\n--- EMPLEADOS ---");

        List<String> empleados = EmpleadoService.listarEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        for (String empleado : empleados) {
            System.out.println(empleado);
        }
    }

    private void modificarRol() {

        System.out.println("\n--- MODIFICAR ROL ---");

        System.out.print("DNI del empleado: ");
        String dni = scanner.nextLine().trim();

        System.out.println("1. ADMINISTRADOR");
        System.out.println("2. RECEPCIONISTA");

        int opcion = leerOpcion();

        RolEnum nuevoRol;

        if (opcion == 1) {
            nuevoRol = RolEnum.ADMINISTRADOR;
        } else if (opcion == 2) {
            nuevoRol = RolEnum.RECEPCIONISTA;
        } else {
            System.out.println("Rol invalido.");
            return;
        }

        EmpleadoService.cambiarRol(dni, nuevoRol);
    }

    private void listarHabitacionesNoDisponibles() {

        System.out.println("\n--- HABITACIONES NO DISPONIBLES ---");

        List<String> habitaciones = HabitacionService.listarHabitacionesNoDisponibles();

        if (habitaciones.isEmpty()) {
            System.out.println("Todas las habitaciones estan disponibles.");
            return;
        }

        for (String habitacion : habitaciones) {
            System.out.println(habitacion);
        }
    }

    private void modificarEstadoHabitacion() {

        System.out.println("\nMODIFICAR ESTADO DE HABITACION");

        System.out.print("Numero de habitacion: ");
        int numero;
        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Numero invalido.");
            return;
        }

        System.out.println("Seleccione el nuevo estado:");
        System.out.println("1. DISPONIBLE");
        System.out.println("2. LIMPIEZA");
        System.out.println("3. MANTENIMIENTO");

        int opcion = leerOpcion();

        EstadoHabitacion estado;

        switch (opcion) {
            case 1:
                estado = EstadoHabitacion.DISPONIBLE;
                break;
            case 2:
                estado = EstadoHabitacion.LIMPIEZA;
                break;
            case 3:
                estado = EstadoHabitacion.MANTENIMIENTO;
                break;
            default:
                System.out.println("Estado invalido.");
                return;
        }

        String motivo = null;
        if (estado != EstadoHabitacion.DISPONIBLE) {
            System.out.print("Ingrese el motivo: ");
            motivo = scanner.nextLine().trim();

            if (motivo.isEmpty()) {
                System.out.println("Debe ingresar un motivo.");
                return;
            }
        }

        boolean actualizado = HabitacionService.cambiarEstadoHabitacion(numero, estado, motivo);
        if(actualizado) {
            System.out.println("Habitacion modificada correctamente.");
        } else {
            System.out.println("No se pudo modificar el estado habitacion.");
        }
    }

    private void listarClientes() {

        System.out.println("\nCLIENTES REGISTRADOS");

        List<String> clientes = ClienteService.listarClientesResumen();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (String cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private int leerOpcion() {

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private RolEnum leerRolEmpleado() {
        System.out.println("Seleccione el rol:");
        System.out.println("1. ADMINISTRADOR");
        System.out.println("2. RECEPCIONISTA");

        int opcionRol = leerOpcion();
        if (opcionRol == 1) {
            return RolEnum.ADMINISTRADOR;
        }
        if (opcionRol == 2) {
            return RolEnum.RECEPCIONISTA;
        }
        return null;
    }
}
