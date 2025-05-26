package moduloTransferencia.dominio;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "depositos")
public class Deposito {

    @Id
    private String id; // UUID

    @Column(name = "rut_comercio", nullable = false)
    private String rutComercio;

    @Column(name = "cuenta_banco", nullable = false)
    private String cuentaBanco;

    @Column(name = "monto_bruto", nullable = false)
    private double montoBruto;

    @Column(name = "comision", nullable = false)
    private double comision;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    public Deposito() {}

    public Deposito(String id, String rutComercio, String cuentaBanco, double montoBruto, double comision, LocalDateTime fecha) {
        this.id = id;
        this.rutComercio = rutComercio;
        this.cuentaBanco = cuentaBanco;
        this.montoBruto = montoBruto;
        this.comision = comision;
        this.fecha = fecha;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRutComercio() { return rutComercio; }
    public void setRutComercio(String rutComercio) { this.rutComercio = rutComercio; }

    public String getCuentaBanco() { return cuentaBanco; }
    public void setCuentaBanco(String cuentaBanco) { this.cuentaBanco = cuentaBanco; }

    public double getMontoBruto() { return montoBruto; }
    public void setMontoBruto(double montoBruto) { this.montoBruto = montoBruto; }

    public double getComision() { return comision; }
    public void setComision(double comision) { this.comision = comision; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Deposito{" +
                "id='" + id + '\'' +
                ", rutComercio='" + rutComercio + '\'' +
                ", cuentaBanco='" + cuentaBanco + '\'' +
                ", montoBruto=" + montoBruto +
                ", comision=" + comision +
                ", fecha=" + fecha +
                '}';
    }
}
