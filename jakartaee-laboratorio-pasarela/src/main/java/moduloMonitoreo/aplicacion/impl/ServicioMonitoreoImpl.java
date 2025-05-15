package moduloMonitoreo.aplicacion.impl;

import moduloMonitoreo.aplicacion.ServicioMonitoreo;

public class ServicioMonitoreoImpl implements ServicioMonitoreo {

    @Override
    public void notificarPago(String idCompra, double monto) {
        System.out.println("[MONITOREO] Iniciando pago. ID Compra: " + idCompra + ", Monto: $" + monto);
    }

    @Override
    public void notificarPagoOk(String idCompra, double monto) {
        System.out.println("[MONITOREO] Pago exitoso. ID Compra: " + idCompra + ", Monto: $" + monto);
    }

    @Override
    public void notificarPagoError(String idCompra, String mensaje) {
        System.out.println("[MONITOREO] ERROR en pago. ID Compra: " + idCompra + ", Motivo: " + mensaje);
    }

    @Override
    public void notificarTransferencia(String idCompra, String idComercio, double monto) {
        System.out.println("[MONITOREO] Transferencia recibida. ID Compra: " + idCompra + ", Comercio: " + idComercio + ", Monto: $" + monto);
    }

    @Override
    public void notificarReclamoComercio(String idComercio, String mensaje) {
        System.out.println("[MONITOREO] Reclamo del comercio " + idComercio + ": " + mensaje);
    }
}
