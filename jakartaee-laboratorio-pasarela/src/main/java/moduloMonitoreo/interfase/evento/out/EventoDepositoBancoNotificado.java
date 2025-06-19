package moduloMonitoreo.interfase.evento.out;

public class EventoDepositoBancoNotificado {
    private final String rutComercio;
    private final double monto;

    public EventoDepositoBancoNotificado(String rutComercio, double monto) {
        this.rutComercio = rutComercio;
        this.monto = monto;
    }

    public String getRutComercio() {
        return rutComercio;
    }

    public double getMonto() {
        return monto;
    }
}
