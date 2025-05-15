package moduloComercio.infraestructura.persistencia;

import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.RepositorioComercio;

import java.util.*;

public class RepositorioComercioMemoria implements RepositorioComercio {
    private final Map<String, Comercio> comercios = new HashMap<>();

    @Override
    public void guardar(Comercio comercio) {
        comercios.put(comercio.getId(), comercio);
    }

    @Override
    public Comercio buscarPorId(String id) {
        return comercios.get(id);
    }

    @Override
    public List<Comercio> listarTodos() {
        return new ArrayList<>(comercios.values());
    }
}