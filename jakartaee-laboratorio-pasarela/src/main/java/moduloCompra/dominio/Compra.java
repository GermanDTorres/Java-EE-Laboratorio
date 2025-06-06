package moduloCompra.dominio;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @Column(name = "id_compra")
    private String idCompra;

    @Column(name = "id_comercio", nullable = false)
    private String idComercio;

    @Column(name = "id_pos", nullable = false)
    private String idPOS;

    @Column(nullable = false)
    private double monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCompra estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    @Transient
    private String numeroTarjeta;

    @Transient
    private String codigoAutorizacion;

    public Compra() {
        this.fecha = new Date();
    }

    // Getters y setters

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(String idComercio) {
        this.idComercio = idComercio;
    }

    public String getIdPOS() {
        return idPOS;
    }

    public void setIdPOS(String idPOS) {
        this.idPOS = idPOS;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }
}
