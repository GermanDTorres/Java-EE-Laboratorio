package moduloCompra.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import moduloCompra.aplicacion.ServicioCompra;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.EstadoCompra;
import moduloCompra.dominio.RepositorioCompra;
import moduloCompra.infraestructura.AutorizadorPagoHttp;
import moduloCompra.infraestructura.limiter.RateLimiter;
import moduloComercio.aplicacion.ServicioComercio;
import moduloComercio.dominio.POS;
import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.DatosTransferencia;

import com.medioPago.modelo.SolicitudPagoDTO;
import com.medioPago.modelo.RespuestaPagoDTO;

import moduloMonitoreo.interfase.evento.out.EventoPagoConfirmado;
import moduloMonitoreo.interfase.evento.out.EventoPagoRechazado;
import moduloMonitoreo.interfase.evento.out.EventoReporteVentasRealizado;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServicioCompraImpl implements ServicioCompra {

    @Inject
    private RepositorioCompra repositorio;

    @Inject
    private ServicioComercio servicioComercio;

    @Inject
    private AutorizadorPagoHttp clienteMedioPagoMock;

    @Inject
    private RateLimiter rateLimiter;

    @Inject
    private ServicioTransferencia servicioTransferencia;

    // Eventos CDI para monitoreo
    @Inject
    private Event<EventoPagoConfirmado> eventoPagoConfirmado;

    @Inject
    private Event<EventoPagoRechazado> eventoPagoRechazado;

    @Inject
    private Event<EventoReporteVentasRealizado> eventoReporteVentas;
    
    @Transactional
    @Override
    public Compra procesarCompra(Compra compra) {
        try {
            if (!rateLimiter.allowRequest()) {
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado("N/A", "N/A"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
                throw new WebApplicationException("Límite de solicitudes excedido", 429);
            }

            if (compra == null || compra.getIdComercio() == null || compra.getIdComercio().trim().isEmpty()) {
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado("N/A", "N/A"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
                throw new WebApplicationException("Compra inválida o comercio no especificado", 400);
            }

            if (!servicioComercio.existeComercio(compra.getIdComercio())) {
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado("N/A", compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
                throw new WebApplicationException("Comercio con id " + compra.getIdComercio() + " no existe", 400);
            }

            if (compra.getIdPOS() == null || compra.getIdPOS().trim().isEmpty()) {
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado("N/A", compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
                throw new WebApplicationException("Id de POS es obligatorio", 400);
            }

            POS pos = servicioComercio.buscarPOS(compra.getIdPOS());
            if (pos == null || !pos.isActivo()) {
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado("N/A", compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
                throw new WebApplicationException("POS inválido o inactivo", 409);
            }

            if (!pos.getComercio().getRut().equals(compra.getIdComercio())) {
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado("N/A", compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
                throw new WebApplicationException("POS no pertenece al comercio indicado", 400);
            }

            if (compra.getNumeroTarjeta() == null || compra.getNumeroTarjeta().trim().isEmpty()) {
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado("N/A", compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
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
                repositorio.guardar(compra);

                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado(compra.getIdCompra(), compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                notificarError(compra);
                return compra;
            }

            SolicitudPagoDTO solicitud = new SolicitudPagoDTO();
            solicitud.setNumeroTarjeta(compra.getNumeroTarjeta());
            solicitud.setMonto(compra.getMonto());
            solicitud.setIdComercio(compra.getIdComercio());

            RespuestaPagoDTO respuestaPago;
            try {
                respuestaPago = clienteMedioPagoMock.autorizarPago(solicitud);
            } catch (Exception e) {
                compra.setEstado(EstadoCompra.RECHAZADA);
                repositorio.guardar(compra);

                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado(compra.getIdCompra(), compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                notificarError(compra);
                return compra;
            }

            compra.setCodigoAutorizacion(respuestaPago.getCodigoAutorizacion());

            if (respuestaPago.isAutorizado()) {
                compra.setEstado(EstadoCompra.APROBADA);

                try {
                    eventoPagoConfirmado.fire(new EventoPagoConfirmado(compra.getIdCompra(), compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                DatosTransferencia datos = new DatosTransferencia(
                    UUID.randomUUID().toString(),
                    compra.getIdComercio(),
                    compra.getMonto(),
                    new java.sql.Timestamp(compra.getFecha().getTime()).toLocalDateTime()
                );

                try {
                    servicioTransferencia.notificarTransferencia(datos);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    try {
                        eventoPagoRechazado.fire(new EventoPagoRechazado(compra.getIdCompra(), compra.getIdComercio()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    notificarError(compra);
                    throw new WebApplicationException("Error al notificar transferencia", 500);
                }

            } else {
                compra.setEstado(EstadoCompra.RECHAZADA);

                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado(compra.getIdCompra(), compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                notificarError(compra);
            }

            repositorio.guardar(compra);
            return compra;

        } catch (WebApplicationException we) {
            throw we;
        } catch (Exception e) {
            e.printStackTrace();

            if (compra != null) {
                compra.setEstado(EstadoCompra.RECHAZADA);
                try {
                    eventoPagoRechazado.fire(new EventoPagoRechazado(compra.getIdCompra(), compra.getIdComercio()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                notificarError(compra);
            }

            throw new WebApplicationException("Error interno al procesar compra", 500);
        }
    }



    private void notificarError(Compra compra) {
        try {
            // Aquí podrías enviar logs o alertas
        } catch (Exception ex) {
            ex.printStackTrace();
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

        // Emitir evento para monitoreo
        eventoReporteVentas.fire(new EventoReporteVentasRealizado("diario"));

        return repositorio.obtenerCompras().stream()
                .filter(c -> c.getIdComercio().equals(idComercio)
                        && c.getEstado() == EstadoCompra.APROBADA
                        && esMismaFecha(c.getFecha(), hoy))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compra> resumenVentasPorPeriodo(String idComercio, Date desde, Date hasta) {
        // Emitir evento para monitoreo
        eventoReporteVentas.fire(new EventoReporteVentasRealizado("periodo"));

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
