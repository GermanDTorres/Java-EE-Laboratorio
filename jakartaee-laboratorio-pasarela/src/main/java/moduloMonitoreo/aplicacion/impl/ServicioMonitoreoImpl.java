package moduloMonitoreo.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import moduloMonitoreo.aplicacion.ServicioMonitoreo;

@ApplicationScoped
public class ServicioMonitoreoImpl implements ServicioMonitoreo {

    @Override
    public void notificarPago(String idCompra, String idComercio, double monto) {
        System.out.println("Notificando pago realizado: Compra=" + idCompra + ", Comercio=" + idComercio + ", Monto=" + monto);
    }

    @Override
    public void notificarPagoOk(String idCompra, String idComercio, double monto) {
        System.out.println("Pago exitoso: Compra=" + idCompra + ", Comercio=" + idComercio + ", Monto=" + monto);
    }

    @Override
    public void notificarPagoError(String idCompra, String idComercio, double monto) {
        System.out.println("Pago rechazado: Compra=" + idCompra + ", Comercio=" + idComercio + ", Monto=" + monto);
    }

    // Método auxiliar privado
    private boolean esDatoInvalido(String idCompra, String idComercio, double monto) {
        return (idCompra == null || idCompra.isBlank()
                || idComercio == null || idComercio.isBlank()
                || monto <= 0);
    }
}
