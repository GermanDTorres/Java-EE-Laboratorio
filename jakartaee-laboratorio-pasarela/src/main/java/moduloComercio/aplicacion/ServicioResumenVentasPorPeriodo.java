package moduloComercio.aplicacion;

import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioResumenVentasPorPeriodo {

    private final RepositorioCompra repositorio;

    public ServicioResumenVentasPorPeriodo(RepositorioCompra repositorio) {
        this.repositorio = repositorio;
    }

    public List<Compra> ejecutar(String rutComercio, Date desde, Date hasta) {
        return repositorio.obtenerCompras()
                .stream()
                .filter(c -> c.getRutComercio().equals(rutComercio) &&
                             !c.getFecha().before(desde) &&
                             !c.getFecha().after(hasta))
                .collect(Collectors.toList());
    }
}
