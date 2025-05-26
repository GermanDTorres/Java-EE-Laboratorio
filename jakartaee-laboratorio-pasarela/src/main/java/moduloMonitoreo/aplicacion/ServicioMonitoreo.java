package moduloMonitoreo.aplicacion;

public interface ServicioMonitoreo {
    void notificarPago(String idCompra, String idComercio, double monto);
    void notificarPagoOk(String idCompra, String idComercio, double monto);
    void notificarPagoError(String idCompra, String idComercio, double monto);
}
