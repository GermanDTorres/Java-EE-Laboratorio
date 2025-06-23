package moduloMonitoreo.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.micrometer.core.instrument.MeterRegistry;

@ApplicationScoped
public class ServicioMonitoreoImpl {

    @Inject
    MeterRegistry meterRegistry;

    public void registrarPagoConfirmado() {
        meterRegistry.counter("pagos_confirmados_total").increment();
    }

    public void registrarPagoRechazado() {
        meterRegistry.counter("pagos_rechazados_total").increment();
    }

    public void registrarReporteVentas() {
        meterRegistry.counter("reportes_ventas_total").increment();
    }

    public void registrarDepositoBanco() {
        meterRegistry.counter("depositos_banco_notificados_total").increment();
    }

    public void registrarReclamo(String tipo) {
        meterRegistry.counter("reclamos_clasificados_total", "tipo", tipo).increment();
    }
}
