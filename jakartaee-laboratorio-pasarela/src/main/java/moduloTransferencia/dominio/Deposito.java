package moduloTransferencia.dominio;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "deposito")
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_comercio", nullable = false)
    private String rutComercio;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private double comision;

    public Deposito() {}

    public Deposito(String rutComercio, double monto, LocalDateTime fecha, double comision) {
        this.rutComercio = rutComercio;
        this.monto = monto;
        this.fechaHora = fecha;
        this.comision = comision;
    }

    public Long getId() {
        return id;
    }

    public String getRutComercio() {
        return rutComercio;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getComision() {
        return comision;
    }
}
