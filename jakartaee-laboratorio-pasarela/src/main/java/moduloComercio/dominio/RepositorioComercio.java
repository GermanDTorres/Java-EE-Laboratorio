package moduloComercio.dominio;

import java.util.List;

public interface RepositorioComercio {
    void guardar(Comercio comercio);
    Comercio buscarPorId(String id);
    List<Comercio> listarTodos();
}
