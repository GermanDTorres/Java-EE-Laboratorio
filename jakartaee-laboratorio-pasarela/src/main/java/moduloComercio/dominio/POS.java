package moduloComercio.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "pos")
public class POS {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private EstadoPOS estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comercio_rut", nullable = false)
    private Comercio comercio;

    public POS() {
    }

    // Constructor más seguro
    public POS(String id, Comercio comercio) {
        this.id = id;
        this.estado = EstadoPOS.INACTIVO;
        this.comercio = comercio;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public EstadoPOS getEstado() { return estado; }
    public void setEstado(EstadoPOS estado) { this.estado = estado; }

    public Comercio getComercio() { return comercio; }
    public void setComercio(Comercio comercio) { this.comercio = comercio; }

    public boolean isActivo() {
        return this.estado == EstadoPOS.ACTIVO;
    }
}
