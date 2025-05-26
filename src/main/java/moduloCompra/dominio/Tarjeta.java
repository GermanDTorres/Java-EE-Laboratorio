package moduloCompra.dominio;

import java.util.Date;

public class Tarjeta {
    private int nro;
    private String marca;
    private Date fechaVto;

    public Tarjeta(int nro, String marca, Date fechaVto) {
        this.nro = nro;
        this.marca = marca;
        this.fechaVto = fechaVto;
    }

    public int getNro() {
        return nro;
    }

    public String getMarca() {
        return marca;
    }

    public Date getFechaVto() {
        return fechaVto;
    }
}
