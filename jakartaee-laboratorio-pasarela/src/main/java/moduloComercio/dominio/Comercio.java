package moduloComercio.dominio;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "comercio")
public class Comercio {

    @Id
    private String rut;

    private String nombre;

    @Column(name = "password_hash")
    private String passwordHash;

    @OneToMany(mappedBy = "comercio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<POS> listaPOS = new ArrayList<>();

    public Comercio() {
    }

    public Comercio(String rut, String nombre, String passwordHash) {
        this.rut = rut;
        this.nombre = nombre;
        this.passwordHash = passwordHash;
    }

    // Getters y Setters
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public List<POS> getListaPOS() { return listaPOS; }
    public void setListaPOS(List<POS> listaPOS) { this.listaPOS = listaPOS; }

    // Métodos funcionales
    public void cambiarContrasena(String nuevoHash) {
        this.passwordHash = nuevoHash;
    }

    public void agregarPOS(POS pos) {
        pos.setComercio(this); // IMPORTANTE para mantener la relación bidireccional
        this.listaPOS.add(pos);
    }

    public void cambiarEstadoPOS(String idPOS, EstadoPOS nuevoEstado) {
        for (POS pos : listaPOS) {
            if (pos.getId().equals(idPOS)) {
                pos.setEstado(nuevoEstado);
                return;
            }
        }
    }


    public POS buscarPOS(String idPOS) {
        for (POS pos : listaPOS) {
            if (pos.getId().equals(idPOS)) {
                return pos;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comercio)) return false;
        Comercio comercio = (Comercio) o;
        return Objects.equals(rut, comercio.rut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rut);
    }
}
