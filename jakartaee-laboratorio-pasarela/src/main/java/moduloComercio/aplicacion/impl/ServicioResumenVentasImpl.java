package moduloComercio.aplicacion.impl;

import moduloCompra.aplicacion.ServicioResumenVentas;
import moduloCompra.dominio.Compra;
import moduloCompra.infraestructura.persistencia.RepositorioCompraMemoria;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServicioResumenVentasImpl implements ServicioResumenVentas {

    private final RepositorioCompraMemoria repositorio;

    public ServicioResumenVentasImpl(RepositorioCompraMemoria repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio);
    }

    @Override
    public List<Compra> resumenVentasDiarias(String idComercio) {
        if (idComercio == null || idComercio.isBlank()) {
            return List.of();
        }
        LocalDate hoy = LocalDate.now();
        return repositorio.obtenerTodas().stream()
                .filter(c -> idComercio.equals(c.getIdComercio())
                        && esMismoDia(c.getFecha(), hoy))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compra> resumenVentasPorPeriodo(String idComercio, Date desde, Date hasta) {
        if (idComercio == null || idComercio.isBlank() || desde == null || hasta == null) {
            return List.of();
        }
        LocalDate desdeLD = convertirADateLocal(desde);
        LocalDate hastaLD = convertirADateLocal(hasta);

        return repositorio.obtenerTodas().stream()
                .filter(c -> idComercio.equals(c.getIdComercio())
                        && !convertirADateLocal(c.getFecha()).isBefore(desdeLD)
                        && !convertirADateLocal(c.getFecha()).isAfter(hastaLD))
                .collect(Collectors.toList());
    }

    @Override
    public double montoActualVendido(String idComercio) {
        if (idComercio == null || idComercio.isBlank()) {
            return 0.0;
        }
        LocalDate hoy = LocalDate.now();
        return repositorio.obtenerTodas().stream()
                .filter(c -> idComercio.equals(c.getIdComercio())
                        && esMismoDia(c.getFecha(), hoy))
                .mapToDouble(Compra::getMonto)
                .sum();
    }

    private boolean esMismoDia(Date fecha, LocalDate hoy) {
        return convertirADateLocal(fecha).isEqual(hoy);
    }

    private LocalDate convertirADateLocal(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
