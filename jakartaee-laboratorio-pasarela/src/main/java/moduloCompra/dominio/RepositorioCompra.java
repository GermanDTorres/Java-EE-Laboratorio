package moduloCompra.dominio;

import java.util.Collection;
import java.util.List;

public interface RepositorioCompra {
    void guardar(Compra compra);
    Compra buscarPorId(String id);
	Collection<Compra> obtenerCompras();
}
