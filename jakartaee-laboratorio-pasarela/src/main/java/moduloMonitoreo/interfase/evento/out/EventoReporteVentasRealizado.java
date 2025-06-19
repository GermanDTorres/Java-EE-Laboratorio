package moduloMonitoreo.interfase.evento.out;

public class EventoReporteVentasRealizado {
    private final String tipoReporte;

    public EventoReporteVentasRealizado(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }
}
