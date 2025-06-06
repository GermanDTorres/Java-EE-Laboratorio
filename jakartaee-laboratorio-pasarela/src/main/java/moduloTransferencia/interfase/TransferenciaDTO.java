package moduloTransferencia.interfase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferenciaDTO {

    private String rutComercio;
    private double monto;
    private String fechaInicio;
    private String fechaFin;

    public String getRutComercio() {
        return rutComercio;
    }

    public void setRutComercio(String rutComercio) {
        this.rutComercio = rutComercio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getFechaInicioComoLocalDateTime() {
        return LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public LocalDateTime getFechaFinComoLocalDateTime() {
        return LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
