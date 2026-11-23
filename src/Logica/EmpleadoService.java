package Logica;

import Base_de_datos.EmpleadoDAO;
import Enums.RolEnum;
import Exceptions.DniExiste;
import Exceptions.EmailNoValido;

import java.util.List;

public class EmpleadoService {

    public static boolean registrarEmpleado(String dni,
                                     String nombre,
                                     String apellido,
                                     String email,
                                     String contrasenia,
                                     RolEnum rol) throws DniExiste, EmailNoValido {

        if (dni == null || dni.isBlank()
                || nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || email == null || email.isBlank()
                || contrasenia == null || contrasenia.isBlank()
                || rol == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        String dniLimpio = dni.trim();
        String nombreLimpio = nombre.trim();
        String apellidoLimpio = apellido.trim();
        String emailLimpio = email.trim();

        if (!emailLimpio.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new EmailNoValido("El email no tiene un formato valido.");
        }

        if (EmpleadoDAO.existePorDniOEmail(dniLimpio, emailLimpio)) {
            throw new DniExiste("El DNI o el email ya se encuentran registrados.");
        }

        return EmpleadoDAO.crearEmpleado(
                dniLimpio,
                emailLimpio,
                contrasenia,
                nombreLimpio,
                apellidoLimpio,
                rol.name()
        );
    }

    public static List<String> listarEmpleados() {
        return EmpleadoDAO.listarEmpleados();
    }

    public static boolean cambiarRol(String dni, RolEnum nuevoRol) {
        if (dni == null || dni.isBlank() || nuevoRol == null) {
            return false;
        }
        return EmpleadoDAO.actualizarRol(dni.trim(), nuevoRol.name());
    }

}
