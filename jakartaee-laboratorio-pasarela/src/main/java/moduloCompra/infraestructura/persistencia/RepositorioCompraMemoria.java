package moduloCompra.infraestructura.persistencia;


import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.*;

public class RepositorioCompraMemoria implements RepositorioCompra {
    private final Map<String, Compra> compras = new HashMap<>();

    @Override
    public void guardar(Compra compra) {
        compras.put(compra.getId(), compra);
    }

    @Override
    public Compra buscarPorId(String id) {
        return compras.get(id);
    }

    @Override
    public List<Compra> listarTodas() {
        return new ArrayList<>(compras.values());
    }
}
