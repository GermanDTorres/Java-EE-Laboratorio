package moduloMonitoreo.aplicacion.impl;

import moduloMonitoreo.aplicacion.ServicioMonitoreo;

public class ServicioMonitoreoImpl implements ServicioMonitoreo {

    @Override
    public void notificarPago(String idCompra, String idComercio, double monto) {
        if (esDatoInvalido(idCompra, idComercio, monto)) {
            System.err.println("[ERROR] Datos inválidos para notificarPago: idCompra=" + idCompra + ", idComercio=" + idComercio + ", monto=" + monto);
            return;
        }
        System.out.println("Notificando pago realizado: Compra=" + idCompra + ", Comercio=" + idComercio + ", Monto=" + monto);
    }

    @Override
    public void notificarPagoOk(String idCompra, String idComercio, double monto) {
        if (esDatoInvalido(idCompra, idComercio, monto)) {
            System.err.println("[ERROR] Datos inválidos para notificarPagoOk: idCompra=" + idCompra + ", idComercio=" + idComercio + ", monto=" + monto);
            return;
        }
        System.out.println("Pago exitoso: Compra=" + idCompra + ", Comercio=" + idComercio + ", Monto=" + monto);
    }

    @Override
    public void notificarPagoError(String idCompra, String idComercio, double monto) {
        if (esDatoInvalido(idCompra, idComercio, monto)) {
            System.err.println("[ERROR] Datos inválidos para notificarPagoError: idCompra=" + idCompra + ", idComercio=" + idComercio + ", monto=" + monto);
            return;
        }
        System.out.println("Pago rechazado: Compra=" + idCompra + ", Comercio=" + idComercio + ", Monto=" + monto);
    }

    private boolean esDatoInvalido(String idCompra, String idComercio, double monto) {
        return (idCompra == null || idCompra.isBlank()
                || idComercio == null || idComercio.isBlank()
                || monto <= 0);
    }
}
