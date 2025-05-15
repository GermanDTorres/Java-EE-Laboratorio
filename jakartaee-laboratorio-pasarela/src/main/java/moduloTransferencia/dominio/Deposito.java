package moduloTransferencia.dominio;

import java.time.LocalDate;

public class Deposito {
    private String id;
    private String idComercio;
    private double monto;
    private LocalDate fecha;

    public Deposito(String id, String idComercio, double monto, LocalDate fecha) {
        this.id = id;
        this.idComercio = idComercio;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getIdComercio() {
        return idComercio;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Deposito{" +
                "id='" + id + '\'' +
                ", idComercio='" + idComercio + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                '}';
    }
}
