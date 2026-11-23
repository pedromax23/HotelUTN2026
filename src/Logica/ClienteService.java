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

}