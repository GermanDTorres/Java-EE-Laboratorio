package moduloTransferencia.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import moduloTransferencia.aplicacion.RepositorioDepositos;
import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.DatosTransferencia;
import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.infraestructura.soap.ClienteSOAPBanco;
import moduloMonitoreo.interfase.evento.out.EventoDepositoBancoNotificado;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class ServicioTransferenciaImpl implements ServicioTransferencia {

    private static final Logger logger = Logger.getLogger(ServicioTransferenciaImpl.class.getName());

    @Inject
    @Named("repositorioJPA")
    private RepositorioDepositos repositorioDepositos;

    @Inject
    private ClienteSOAPBanco clienteSOAPBanco;

    @Inject
    Event<EventoDepositoBancoNotificado> eventoDepositoBancoNotificado;

    private static final double COMISION = 0.02;
    private static final int THREAD_POOL_SIZE = 10;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    private final Random random = new Random();

    @Override
    public void recibirNotificacionTransferenciaDesdeMedioPago(Map<String, Object> datosTransferencia) {
        try {
            String rutComercio = (String) datosTransferencia.get("rutComercio");
            if (rutComercio == null || rutComercio.trim().isEmpty()) {
                logger.warning("[Transferencia] ❌ rutComercio es nulo o vacío");
                return;
            }

            Object montoObj = datosTransferencia.get("monto");
            if (montoObj == null) {
                logger.warning("[Transferencia] ❌ monto es nulo");
                return;
            }

            double monto;
            try {
                monto = Double.parseDouble(montoObj.toString());
                if (monto <= 0) {
                    logger.warning("[Transferencia] ❌ monto debe ser mayor a 0");
                    return;
                }
            } catch (NumberFormatException nfe) {
                logger.log(Level.WARNING, "[Transferencia] ❌ monto no es un número válido: " + montoObj, nfe);
                return;
            }

            String idTransferencia = UUID.randomUUID().toString();

            int delayMinutos = 1 + random.nextInt(5);
            logger.info(String.format("[Transferencia] 🕓 Programando notificación al banco en %d minutos para ID: %s, Comercio: %s, Monto: %.2f",
                    delayMinutos, idTransferencia, rutComercio, monto));

            scheduler.schedule(() -> {
                try {
                    String fecha = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    logger.info("[Transferencia] ⏰ Enviando notificación al banco para transferencia " + idTransferencia);

                    boolean notificado = clienteSOAPBanco.notificarTransferencia(idTransferencia, rutComercio, monto, fecha);

                    if (notificado) {
                        double comision = monto * COMISION;
                        double montoNeto = monto - comision;

                        Deposito deposito = new Deposito(rutComercio, montoNeto, LocalDateTime.now(), comision);
                        repositorioDepositos.guardar(deposito);

                        logger.info("[Transferencia] ✅ Notificación exitosa, depósito registrado: " + deposito);

                        // Emitir evento para monitoreo
                        eventoDepositoBancoNotificado.fire(new EventoDepositoBancoNotificado(rutComercio, montoNeto));
                    } else {
                        logger.warning("[Transferencia] ❌ Falló la notificación al banco para transferencia " + idTransferencia);
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "[Transferencia] ❌ Error en tarea diferida: " + e.getMessage(), e);
                }
            }, delayMinutos, TimeUnit.MINUTES);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "[Transferencia] ❌ Error al programar la transferencia: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Deposito> consultarDepositos(String rutComercio, LocalDateTime desde, LocalDateTime hasta) {
        if (rutComercio == null || rutComercio.trim().isEmpty()) {
            logger.warning("[Transferencia] consultarDepositos: rutComercio es nulo o vacío");
            return List.of();
        }
        if (desde == null || hasta == null) {
            logger.warning("[Transferencia] consultarDepositos: rango de fechas inválido");
            return List.of();
        }
        return repositorioDepositos.consultarPorComercioYRangoFechas(rutComercio, desde, hasta);
    }

    @Override
    public void notificarTransferencia(DatosTransferencia datos) {
        logger.info(String.format("[Transferencia] 📥 Notificación recibida para transferencia ID: %s, Comercio: %s, Monto: %.2f",
                datos.getIdTransferencia(), datos.getIdComercio(), datos.getMonto()));

        Map<String, Object> datosMap = Map.of(
                "rutComercio", datos.getIdComercio(),
                "monto", datos.getMonto()
        );

        recibirNotificacionTransferenciaDesdeMedioPago(datosMap);
    }
}
