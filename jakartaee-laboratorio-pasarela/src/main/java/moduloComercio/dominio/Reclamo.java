package moduloComercio.dominio;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reclamo {
    @Id
    @GeneratedValue
    private Long id;

    private String texto;
    private LocalDateTime fecha;
    private String comercioId;

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setComercioId(String comercioId) {
        this.comercioId = comercioId;
    }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getComercioId() {
        return comercioId;
    }
}
