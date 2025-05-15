package moduloMonitoreo.aplicacion;

public interface ServicioMonitoreo {
    void notificarPago(String idCompra, double monto);
    void notificarPagoOk(String idCompra, double monto);
    void notificarPagoError(String idCompra, String mensaje);
    void notificarTransferencia(String idCompra, String idComercio, double monto);
    void notificarReclamoComercio(String idComercio, String mensaje);
}
