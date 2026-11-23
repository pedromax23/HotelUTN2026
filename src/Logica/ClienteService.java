package Logica;

import Base_de_datos.ClienteDAO;
import java.util.List;

public class ClienteService {

    public static List<String> listarClientesResumen() {
        return ClienteDAO.listarClientesResumen();
    }
}