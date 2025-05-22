package moduloComercio.dominio;

public class POS {
    private int id;
    private EstadoPOS estado;

    public POS(int id) {
        this.id = id;
        this.estado = EstadoPOS.ACTIVO; // Por defecto activo
    }

    public int getId() {
        return id;
    }

    public EstadoPOS getEstado() {
        return estado;
    }

    public void setEstado(EstadoPOS estado) {
        this.estado = estado;
    }

    public boolean isActivo() {
        return this.estado == EstadoPOS.ACTIVO;
    }
}
