package moduloTransferencia.dominio;

import java.time.LocalDateTime;

public class DatosTransferencia {
    private String idTransferencia;
    private String idComercio;
    private double monto;
    private LocalDateTime fecha;

    public DatosTransferencia() {
        // Constructor vacío necesario para frameworks de serialización
    }

    public DatosTransferencia(String idTransferencia, String idComercio, double monto, LocalDateTime fecha) {
        this.idTransferencia = idTransferencia;
        this.idComercio = idComercio;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(String idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public String getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(String idComercio) {
        this.idComercio = idComercio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
