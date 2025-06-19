package moduloMonitoreo.interfase.evento.out;

public class EventoPagoConfirmado {
    private final String idCompra;
    private final String rutComercio;

    public EventoPagoConfirmado(String idCompra, String rutComercio) {
        this.idCompra = idCompra;
        this.rutComercio = rutComercio;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public String getRutComercio() {
        return rutComercio;
    }
}