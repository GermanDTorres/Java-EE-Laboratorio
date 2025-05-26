package moduloTransferencia.dominio;

import java.time.LocalDateTime;

public class DatosTransferencia {
    private String idTransferencia;
    private String idComercio;
    private double monto;
    private LocalDateTime fecha;

    public DatosTransferencia(String idTransferencia, String idComercio, double monto, LocalDateTime fecha) {
        this.idTransferencia = idTransferencia;
        this.idComercio = idComercio;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getIdTransferencia() {
        return idTransferencia;
    }

    public String getIdComercio() {
        return idComercio;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
