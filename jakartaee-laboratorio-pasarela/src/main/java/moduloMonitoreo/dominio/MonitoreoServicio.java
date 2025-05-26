package moduloMonitoreo.dominio;

public interface MonitoreoServicio {
    void notificarPago(String infoPago);
    void notificarPagoOk(String infoPago);
    void notificarPagoError(String infoPago);
    void notificarTransferencia(String infoTransferencia);
    void notificarReclamoComercio(String infoReclamo);
}
