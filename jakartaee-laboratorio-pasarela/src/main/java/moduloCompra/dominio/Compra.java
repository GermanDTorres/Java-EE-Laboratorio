package moduloCompra.dominio;

import java.time.LocalDate;

public class Compra {
    private String id;
    private String idComercio;
    private double monto;
    private EstadoCompra estado;
    private LocalDate fecha;

    public Compra(String id, String idComercio, double monto) {
        this.id = id;
        this.idComercio = idComercio;
        this.monto = monto;
        this.estado = EstadoCompra.PENDIENTE;
        this.fecha = LocalDate.now();
    }

    public String getId() { return id; }
    public String getIdComercio() { return idComercio; }
    public double getMonto() { return monto; }
    public EstadoCompra getEstado() { return estado; }
    public LocalDate getFecha() { return fecha; }

    public void aprobar() {
        this.estado = EstadoCompra.APROBADA;
    }

    public void rechazar() {
        this.estado = EstadoCompra.RECHAZADA;
    }
}
