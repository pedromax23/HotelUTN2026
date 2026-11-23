
package Menues;

import Base_de_datos.EmpleadoDAO;
import Base_de_datos.HabitacionDAO;
import Base_de_datos.ClienteDAO;
import Session.Sesion;

import java.util.List;
import java.util.Scanner;

public class MenuAdministrador {

    private static final Scanner scanner = new Scanner(System.in);
    private final EmpleadoDAO empleadoDAO;
    private final HabitacionDAO habitacionDAO;
    private final ClienteDAO clienteDAO;

    public MenuAdministrador() {
        this.empleadoDAO = new EmpleadoDAO();
        this.habitacionDAO = new HabitacionDAO();
        this.clienteDAO = new ClienteDAO();
    }

    public void menu() {

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

    private void crearEmpleado() {

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

        if (dni.isEmpty() || nombre.isEmpty() ||
                apellido.isEmpty() || email.isEmpty() ||
                contrasenia.isEmpty()) {

            System.out.println("Todos los campos son obligatorios.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("Email invalido.");
            return;
        }

        if (empleadoDAO.existePorDniOEmail(dni, email)) {
            System.out.println("El DNI o email ya estan registrados.");
            return;
        }

        System.out.println("Seleccione el rol:");
        System.out.println("1. ADMINISTRADOR");
        System.out.println("2. RECEPCIONISTA");

        int opcionRol = leerOpcion();

        String rol;

        if (opcionRol == 1) {
            rol = "ADMINISTRADOR";
        } else if (opcionRol == 2) {
            rol = "RECEPCIONISTA";
        } else {
            System.out.println("Rol invalido.");
            return;
        }

        boolean creado = empleadoDAO.crearEmpleado(
                dni, email, contrasenia,
                nombre, apellido, rol
        );

        if (creado) {
            System.out.println("Empleado creado correctamente.");
        } else {
            System.out.println("No se pudo crear el empleado.");
        }
    }

    private void listarEmpleados() {

        System.out.println("\n--- EMPLEADOS ---");

        List<String> empleados = empleadoDAO.listarEmpleados();

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

        String nuevoRol;

        if (opcion == 1) {
            nuevoRol = "ADMINISTRADOR";
        } else if (opcion == 2) {
            nuevoRol = "RECEPCIONISTA";
        } else {
            System.out.println("Rol invalido.");
            return;
        }

        if (empleadoDAO.actualizarRol(dni, nuevoRol)) {
            System.out.println("Rol actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el rol.");
        }
    }

    private void listarHabitacionesNoDisponibles() {

        System.out.println("\n--- HABITACIONES NO DISPONIBLES ---");

        List<String> habitaciones =
                habitacionDAO.listarHabitacionesNoDisponibles();

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

        String estado;

        switch (opcion) {
            case 1:
                estado = "DISPONIBLE";
                break;
            case 2:
                estado = "LIMPIEZA";
                break;
            case 3:
                estado = "MANTENIMIENTO";
                break;
            default:
                System.out.println("Estado invalido.");
                return;
        }

        String motivo = null;

        if (!estado.equals("DISPONIBLE")) {
            System.out.print("Ingrese el motivo: ");
            motivo = scanner.nextLine().trim();

            if (motivo.isEmpty()) {
                System.out.println("Debe ingresar un motivo.");
                return;
            }
        }

        if (habitacionDAO.actualizarEstadoHabitacion(
                numero, estado, motivo)) {

            System.out.println("Estado actualizado correctamente.");

        } else {
            System.out.println("No se pudo actualizar la habitacion.");
        }
    }

    private void listarClientes() {

        System.out.println("\nCLIENTES REGISTRADOS");

        List<String> clientes = clienteDAO.listarClientesResumen();

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
}
