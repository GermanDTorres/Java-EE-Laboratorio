package moduloCompra.dominio;

import java.util.Date;

public class Compra {
    private String idCompra;
    private String idComercio;
    private double monto;
    private EstadoCompra estado;
    private Date fecha;

    public Compra() {}

    public Compra(String idCompra, String idComercio, double monto, EstadoCompra estado, Date fecha) {
        this.idCompra = idCompra;
        this.idComercio = idComercio;
        this.monto = monto;
        this.estado = estado;
        this.fecha = fecha;
    }

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

	public Object getRutComercio() {
		// TODO Auto-generated method stub
		return null;
	}
}
