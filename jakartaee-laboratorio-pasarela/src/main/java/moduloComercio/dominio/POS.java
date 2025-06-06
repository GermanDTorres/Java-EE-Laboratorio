package moduloComercio.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "pos")
public class POS {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private EstadoPOS estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comercio_rut")
    private Comercio comercio;

    public POS() {
    }

    public POS(String id) {
        this.id = id;
        this.estado = EstadoPOS.INACTIVO;
    }

    // getters y setters
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
