package Logica;

import Base_de_datos.ClienteDAO;
import java.util.List;

public class ClienteService {

    public static List<String> listarClientesResumen() {
        return ClienteDAO.listarClientesResumen();
    }

    public static boolean existeCliente(String dni) {
        if (dni == null || dni.isBlank()) {
            return false;
        }
        return ClienteDAO.existePorDni(dni.trim());
    }

    public static String obtenerDni(String email) {
        return ClienteDAO.obtenerDniPorEmail(email);
    }

    public static void actualizarCliente(String dni, String nombre, String apellido, String email, String sexo, String domicilio, String origen, int edad) {
        if (existeCliente(dni)) {

            ClienteDAO.actualizarDatosCliente(dni, nombre, apellido, sexo, edad, domicilio, origen);

        }
    }

}