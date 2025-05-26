package moduloComercio.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "pos")
public class POS {

    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    private EstadoPOS estado;

    public POS() {
    }

    public POS(int id) {
        this.id = id;
        this.estado = EstadoPOS.INACTIVO;
    }

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public EstadoPOS getEstado() { return estado; }
    public void setEstado(EstadoPOS estado) { this.estado = estado; }
}
