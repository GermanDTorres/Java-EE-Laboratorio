package moduloCompra.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import moduloCompra.aplicacion.ServicioResumenVentas;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServicioResumenVentasImpl implements ServicioResumenVentas {

    @Inject
    private RepositorioCompra repositorio;

    @Override
    public List<Compra> resumenVentasDiarias(String idComercio) {
        if (idComercio == null || idComercio.isBlank()) {
            return List.of();
        }
        Date hoy = new Date();
        return repositorio.obtenerCompras().stream()
                .filter(c -> idComercio.equals(c.getIdComercio())
                        && esMismoDia(c.getFecha(), hoy))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compra> resumenVentasPorPeriodo(String idComercio, Date desde, Date hasta) {
        if (idComercio == null || idComercio.isBlank() || desde == null || hasta == null) {
            return List.of();
        }
        return repositorio.obtenerCompras().stream()
                .filter(c -> idComercio.equals(c.getIdComercio())
                        && !c.getFecha().before(desde)
                        && !c.getFecha().after(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public double montoActualVendido(String idComercio) {
        if (idComercio == null || idComercio.isBlank()) {
            return 0.0;
        }
        Date hoy = new Date();
        return repositorio.obtenerCompras().stream()
                .filter(c -> idComercio.equals(c.getIdComercio())
                        && esMismoDia(c.getFecha(), hoy))
                .mapToDouble(Compra::getMonto)
                .sum();
    }

    private boolean esMismoDia(Date fecha1, Date fecha2) {
        if (fecha1 == null || fecha2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fecha1);
        cal2.setTime(fecha2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
