/*package moduloCompra.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class RepositorioCompraMemoria implements RepositorioCompra {

    private static final List<Compra> compras = new ArrayList<>();

    @Override
    public void guardar(Compra compra) {
        if (compra != null) {
            compras.add(compra);
        }
    }

    @Override
    public Compra buscarPorId(String id) {
        return compras.stream()
                .filter(c -> c.getIdCompra().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Compra> obtenerCompras() {
        return new ArrayList<>(compras); // devuelve copia defensiva
    }
}
*/