package moduloCompra.aplicacion.impl;

import moduloCompra.aplicacion.ServicioCompra;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.EstadoCompra;
import moduloCompra.infraestructura.persistencia.RepositorioCompraMemoria;
import moduloComercio.aplicacion.ServicioComercio;
import moduloMonitoreo.aplicacion.ServicioMonitoreo;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicioCompraImpl implements ServicioCompra {

    private final RepositorioCompraMemoria repositorio;
    private final ServicioMonitoreo servicioMonitoreo;
    private final ServicioComercio servicioComercio;

    public ServicioCompraImpl(RepositorioCompraMemoria repositorio, ServicioMonitoreo servicioMonitoreo, ServicioComercio servicioComercio) {
        this.repositorio = repositorio;
        this.servicioMonitoreo = servicioMonitoreo;
        this.servicioComercio = servicioComercio;
    }

    @Override
    public Compra procesarCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("Compra no puede ser nula");
        }
        if (compra.getIdComercio() == null || compra.getIdComercio().trim().isEmpty()) {
            throw new IllegalArgumentException("Id de comercio no puede ser vacío");
        }

        if (!servicioComercio.existeComercio(compra.getIdComercio())) {
            throw new IllegalArgumentException("Comercio con id " + compra.getIdComercio() + " no existe");
        }

        if (compra.getMonto() <= 0) {
            compra.setEstado(EstadoCompra.RECHAZADA);
            servicioMonitoreo.notificarPagoError(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());
            repositorio.agregarCompra(compra);
            return compra;
        }

        servicioMonitoreo.notificarPago(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());

        compra.setEstado(EstadoCompra.APROBADA);
        servicioMonitoreo.notificarPagoOk(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());

        repositorio.agregarCompra(compra);
        return compra;
    }

    @Override
    public Optional<Compra> obtenerCompra(String idCompra) {
        if (idCompra == null || idCompra.trim().isEmpty()) {
            return Optional.empty();
        }
        return repositorio.obtenerCompra(idCompra);
    }

    @Override
    public List<Compra> obtenerCompras() {
        return repositorio.obtenerTodas();
    }

    @Override
    public List<Compra> resumenVentasDiarias(String idComercio) {
        Date hoy = new Date();
        return repositorio.obtenerTodas().stream()
                .filter(c -> c.getIdComercio().equals(idComercio)
                        && c.getEstado() == EstadoCompra.APROBADA
                        && esMismaFecha(c.getFecha(), hoy))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compra> resumenVentasPorPeriodo(String idComercio, Date desde, Date hasta) {
        return repositorio.obtenerTodas().stream()
                .filter(c -> c.getIdComercio().equals(idComercio)
                        && c.getEstado() == EstadoCompra.APROBADA
                        && !c.getFecha().before(desde) && !c.getFecha().after(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public double montoActualVendido(String idComercio) {
        return repositorio.obtenerTodas().stream()
                .filter(c -> c.getIdComercio().equals(idComercio)
                        && c.getEstado() == EstadoCompra.APROBADA)
                .mapToDouble(Compra::getMonto)
                .sum();
    }

    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        return fecha1.getYear() == fecha2.getYear() &&
               fecha1.getMonth() == fecha2.getMonth() &&
               fecha1.getDate() == fecha2.getDate();
    }
}
