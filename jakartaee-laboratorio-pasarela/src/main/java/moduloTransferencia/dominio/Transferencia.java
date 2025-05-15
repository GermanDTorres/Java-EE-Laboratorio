package moduloTransferencia.dominio;

import java.time.LocalDate;

public class Transferencia {
    public String id;
    public String comercioId;
    public double monto;
    public LocalDate fecha;

    public Transferencia(String id, String comercioId, double monto, LocalDate fecha) {
        this.id = id;
        this.comercioId = comercioId;
        this.monto = monto;
        this.fecha = fecha;
    }
}
