package moduloMonitoreo.dominio;

import java.time.LocalDateTime;

public class EventoMonitoreo {
    private String id;
    private String tipo;
    private String descripcion;
    private LocalDateTime fechaHora;

    public EventoMonitoreo(String id, String tipo, String descripcion, LocalDateTime fechaHora) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}
