package Logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BuscadorGenerico<T> {

    public List<T> filtrar(List<T> elementos, Predicate<T> criterio) {
        List<T> encontrados = new ArrayList<>();
        if (elementos == null || criterio == null) {
            return encontrados;
        }
        for (T elemento : elementos) {
            if (criterio.test(elemento)) {
                encontrados.add(elemento);
            }
        }
        return encontrados;
    }

    public Optional<T> buscarPrimero(List<T> elementos, Predicate<T> criterio) {
        if (elementos == null || criterio == null) {
            return Optional.empty();
        }
        for (T elemento : elementos) {
            if (criterio.test(elemento)) {
                return Optional.of(elemento);
            }
        }
        return Optional.empty();
    }
}