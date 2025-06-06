package moduloCompra.aplicacion.impl;

import moduloCompra.infraestructura.servicios.Medio_De_Pago.MedioDePagoService;
import moduloCompra.infraestructura.servicios.Medio_De_Pago.MedioDePago;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import moduloCompra.aplicacion.ServicioCompra;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.EstadoCompra;
import moduloCompra.dominio.RepositorioCompra;
import moduloMonitoreo.aplicacion.ServicioMonitoreo;
import moduloComercio.aplicacion.ServicioComercio;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.ws.WebServiceRef;

@ApplicationScoped
public class ServicioCompraImpl implements ServicioCompra {

    @PersistenceContext(unitName = "tallerjava")
    private EntityManager em;

    @Inject
    private RepositorioCompra repositorio;

    @Inject
    private ServicioMonitoreo servicioMonitoreo;

    @Inject
    private ServicioComercio servicioComercio;

   @WebServiceRef(wsdlLocation="http://localhost:8081/castlemock/mock/soap/project/hkG2Of/Medio_de_PagoPort?wsdl")
   static MedioDePagoService medio_de_pago_service;

    @Transactional
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

        if (compra.getIdCompra() == null || compra.getIdCompra().isEmpty()) {
            compra.setIdCompra(UUID.randomUUID().toString());
        }

        if (compra.getMonto() <= 0) {
            compra.setEstado(EstadoCompra.RECHAZADA);
            servicioMonitoreo.notificarPagoError(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());
            repositorio.guardar(compra);
            return compra;
        }

        servicioMonitoreo.notificarPago(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());

        compra.setEstado(EstadoCompra.APROBADA);
        servicioMonitoreo.notificarPagoOk(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());

        repositorio.guardar(compra);

	MedioDePago medio_de_pago_port = medio_de_pago_service.getMedioDePagoPort();
	medio_de_pago_port.realizarCompra(Integer.parseInt(compra.getIdCompra()), Integer.parseInt(compra.getIdComercio()), compra.getMonto(), compra.getFecha().toString());

        return compra;
    }

    @Override
    public Optional<Compra> obtenerCompra(String idCompra) {
        if (idCompra == null || idCompra.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(repositorio.buscarPorId(idCompra));
    }

    @Override
    public List<Compra> obtenerCompras() {
        return repositorio.obtenerCompras().stream().toList();
    }

    @Override
    public List<Compra> resumenVentasDiarias(String idComercio) {
        Date hoy = new Date();
        return repositorio.obtenerCompras().stream()
                .filter(c -> c.getIdComercio().equals(idComercio)
                        && c.getEstado() == EstadoCompra.APROBADA
                        && esMismaFecha(c.getFecha(), hoy))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compra> resumenVentasPorPeriodo(String idComercio, Date desde, Date hasta) {
        return repositorio.obtenerCompras().stream()
                .filter(c -> c.getIdComercio().equals(idComercio)
                        && c.getEstado() == EstadoCompra.APROBADA
                        && !c.getFecha().before(desde) && !c.getFecha().after(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public double montoActualVendido(String idComercio) {
        return repositorio.obtenerCompras().stream()
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
