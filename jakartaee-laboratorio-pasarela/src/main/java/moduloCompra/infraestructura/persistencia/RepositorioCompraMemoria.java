package moduloCompra.infraestructura.persistencia;

import moduloCompra.dominio.Compra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioCompraMemoria {

    // Lista estática compartida por todas las instancias
    private static final List<Compra> compras = new ArrayList<>();

    public void agregarCompra(Compra compra) {
        compras.add(compra);
    }

    public Optional<Compra> obtenerCompra(String idCompra) {
        return compras.stream()
            .filter(c -> c.getIdCompra().equals(idCompra))
            .findFirst();
    }

    public List<Compra> obtenerTodas() {
        return new ArrayList<>(compras); // devuelve una copia para evitar modificaciones externas
    }
}
