package moduloCompra.aplicacion;

import jakarta.inject.Inject;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServicioResumenVentasPorPeriodo {

    @Inject
    private RepositorioCompra repositorio;

    public List<Compra> ejecutar(String rutComercio, Date desde, Date hasta) {
        if (rutComercio == null || rutComercio.isBlank() || desde == null || hasta == null) {
            return List.of(); // Retorna lista vacía si hay datos inválidos
        }

        return repositorio.obtenerCompras().stream()
                .filter(c -> Objects.equals(c.getIdComercio(), rutComercio)
                        && !c.getFecha().before(desde)
                        && !c.getFecha().after(hasta))
                .collect(Collectors.toList());
    }
}
