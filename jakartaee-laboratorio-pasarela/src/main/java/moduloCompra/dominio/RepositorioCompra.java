package moduloCompra.dominio;

import java.util.List;

public interface RepositorioCompra {
    void guardar(Compra compra);
    Compra buscarPorId(String id);
    List<Compra> listarTodas();
}
