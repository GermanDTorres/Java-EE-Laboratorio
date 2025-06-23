package moduloMonitoreo.interfase.evento.out;

public class EventoReclamoClasificado {
    private String tipo; // positivo, neutro, negativo

    public EventoReclamoClasificado(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
