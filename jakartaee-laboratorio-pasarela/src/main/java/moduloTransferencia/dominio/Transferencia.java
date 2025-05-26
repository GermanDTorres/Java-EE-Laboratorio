package moduloTransferencia.dominio;

import java.time.LocalDateTime;

public class Transferencia {
    private String id;
    private String idCompra;
    private String rutComercio;
    private double monto;
    private LocalDateTime fecha;

    public Transferencia(String id, String idCompra, String rutComercio, double monto, LocalDateTime fecha) {
        this.id = id;
        this.idCompra = idCompra;
        this.rutComercio = rutComercio;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getId() { return id; }
    public String getIdCompra() { return idCompra; }
    public String getRutComercio() { return rutComercio; }
    public double getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }

    @Override
    public String toString() {
        return "Transferencia{" +
                "id='" + id + '\'' +
                ", idCompra='" + idCompra + '\'' +
                ", rutComercio='" + rutComercio + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                '}';
    }
}
