package moduloCompra.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import moduloCompra.aplicacion.ServicioCompra;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.EstadoCompra;
import moduloCompra.dominio.RepositorioCompra;
import moduloMonitoreo.aplicacion.ServicioMonitoreo;
import moduloComercio.aplicacion.ServicioComercio;
import moduloComercio.dominio.POS;
import moduloCompra.infraestructura.AutorizadorPagoHttp;
import moduloCompra.infraestructura.limiter.RateLimiter;
import moduloTransferencia.infraestructura.soap.ClienteSOAPBanco;

import com.medioPago.modelo.SolicitudPagoDTO;
import com.medioPago.modelo.RespuestaPagoDTO;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServicioCompraImpl implements ServicioCompra {

    @Inject
    private RepositorioCompra repositorio;

    @Inject
    private ServicioMonitoreo servicioMonitoreo;

    @Inject
    private ServicioComercio servicioComercio;

    @Inject
    private AutorizadorPagoHttp clienteMedioPagoMock;

    @Inject
    private ClienteSOAPBanco clienteSOAPBanco;

    @Inject
    private RateLimiter rateLimiter;

    @Transactional
    @Override
    public Compra procesarCompra(Compra compra) {
        try {
            if (!rateLimiter.allowRequest()) {
                throw new WebApplicationException("Límite de solicitudes excedido", 429);
            }

            if (compra == null) {
                throw new WebApplicationException("Compra no puede ser nula", 400);
            }

            if (compra.getIdComercio() == null || compra.getIdComercio().trim().isEmpty()) {
                throw new WebApplicationException("Id de comercio no puede ser vacío", 400);
            }

            if (!servicioComercio.existeComercio(compra.getIdComercio())) {
                throw new WebApplicationException("Comercio con id " + compra.getIdComercio() + " no existe", 400);
            }

            // Validar POS
            if (compra.getIdPOS() == null || compra.getIdPOS().trim().isEmpty()) {
                throw new WebApplicationException("Id de POS es obligatorio", 400);
            }

            POS pos = servicioComercio.buscarPOS(compra.getIdPOS());
            if (pos == null) {
                throw new WebApplicationException("POS con id " + compra.getIdPOS() + " no existe", 400);
            }

            if (!pos.isActivo()) {
                throw new WebApplicationException("POS con id " + compra.getIdPOS() + " está inactivo", 400);
            }

            if (!pos.getComercio().getRut().equals(compra.getIdComercio())) {
                throw new WebApplicationException("POS no pertenece al comercio indicado", 400);
            }

            if (compra.getNumeroTarjeta() == null || compra.getNumeroTarjeta().trim().isEmpty()) {
                throw new WebApplicationException("Número de tarjeta es obligatorio", 400);
            }

            if (compra.getIdCompra() == null || compra.getIdCompra().isEmpty()) {
                compra.setIdCompra(UUID.randomUUID().toString());
            }

            if (compra.getFecha() == null) {
                compra.setFecha(new Date());
            }

            if (compra.getMonto() <= 0) {
                compra.setEstado(EstadoCompra.RECHAZADA);
                try {
                    servicioMonitoreo.notificarPagoError(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                repositorio.guardar(compra);
                return compra;
            }

            try {
                servicioMonitoreo.notificarPago(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SolicitudPagoDTO solicitud = new SolicitudPagoDTO();
            solicitud.setNumeroTarjeta(compra.getNumeroTarjeta());
            solicitud.setMonto(compra.getMonto());
            solicitud.setIdComercio(compra.getIdComercio());

            RespuestaPagoDTO respuestaPago;
            try {
                respuestaPago = clienteMedioPagoMock.autorizarPago(solicitud);
            } catch (Exception e) {
                e.printStackTrace();
                compra.setEstado(EstadoCompra.RECHAZADA);
                try {
                    servicioMonitoreo.notificarPagoError(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                repositorio.guardar(compra);
                return compra;
            }

            compra.setCodigoAutorizacion(respuestaPago.getCodigoAutorizacion());

            if (respuestaPago.isAutorizado()) {
                compra.setEstado(EstadoCompra.APROBADA);
                try {
                    servicioMonitoreo.notificarPagoOk(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    String fechaFormateada = new SimpleDateFormat("yyyy-MM-dd").format(compra.getFecha());
                    clienteSOAPBanco.notificarTransferencia(
                        compra.getIdCompra(),
                        compra.getIdComercio(),
                        compra.getMonto(),
                        fechaFormateada
                    );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                compra.setEstado(EstadoCompra.RECHAZADA);
                try {
                    servicioMonitoreo.notificarPagoError(compra.getIdCompra(), compra.getIdComercio(), compra.getMonto());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            repositorio.guardar(compra);
            return compra;

        } catch (WebApplicationException we) {
            throw we;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Error interno al procesar compra", 500);
        }
    }

    @Override
    public Optional<Compra> obtenerCompra(String idCompra) {
        if (idCompra == null || idCompra.trim().isEmpty()) return Optional.empty();
        return Optional.ofNullable(repositorio.buscarPorId(idCompra));
    }

    @Override
    public List<Compra> obtenerCompras() {
        return (List<Compra>) repositorio.obtenerCompras();
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
                        && !c.getFecha().before(desde)
                        && !c.getFecha().after(hasta))
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
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fecha1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fecha2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
            && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
            && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
