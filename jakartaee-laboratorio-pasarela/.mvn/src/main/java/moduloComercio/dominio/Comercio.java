package moduloComercio.dominio;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comercios")
public class Comercio {

    @Id
    private String rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String passwordHash;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "comercio_rut")
    private List<POS> posList = new ArrayList<>();

    public Comercio() {
    }

    // getters y setters

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public List<POS> getPosList() { return posList; }
    public void setPosList(List<POS> posList) { this.posList = posList; }

    public void agregarPOS(POS pos) {
        this.posList.add(pos);
    }

    public void cambiarEstadoPOS(int posId, EstadoPOS nuevoEstado) {
        for (POS pos : posList) {
            if (pos.getId() == posId) {
                pos.setEstado(nuevoEstado);
                break;
            }
        }
    }

    public void cambiarContrasena(String nuevoHash) {
        this.passwordHash = nuevoHash;
    }
}
