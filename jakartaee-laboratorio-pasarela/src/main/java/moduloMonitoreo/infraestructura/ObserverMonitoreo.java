package moduloMonitoreo.infraestructura;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import moduloMonitoreo.interfase.evento.out.*;
import moduloMonitoreo.aplicacion.impl.ServicioMonitoreoImpl;

@ApplicationScoped
public class ObserverMonitoreo {

    private static final Logger log = Logger.getLogger(ObserverMonitoreo.class);

    @Inject
    ServicioMonitoreoImpl servicioMonitoreo;

    @PostConstruct
    void init() {
        log.info("✅ ObserverMonitoreo inicializado correctamente.");
    }

    public void onPagoConfirmado(@Observes EventoPagoConfirmado event) {
        log.infof("🔔 Pago confirmado recibido: idCompra=%s", event.getIdCompra());
        servicioMonitoreo.registrarPagoConfirmado();
    }

    public void onPagoRechazado(@Observes EventoPagoRechazado event) {
        log.infof("🔔 Pago rechazado recibido: idCompra=%s", event.getIdCompra());
        servicioMonitoreo.registrarPagoRechazado();
    }

    public void onReporteVentas(@Observes EventoReporteVentasRealizado event) {
        log.infof("📊 Reporte de ventas realizado: tipo=%s", event.getTipoReporte());
        servicioMonitoreo.registrarReporteVentas();
    }

    public void onDepositoBanco(@Observes EventoDepositoBancoNotificado event) {
        log.infof("🏦 Depósito notificado: comercio=%s, monto=%.2f", event.getRutComercio(), event.getMonto());
        servicioMonitoreo.registrarDepositoBanco();
    }
}
