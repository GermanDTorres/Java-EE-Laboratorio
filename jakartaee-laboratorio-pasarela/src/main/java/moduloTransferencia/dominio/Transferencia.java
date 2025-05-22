package moduloTransferencia.dominio;

import java.time.LocalDate;

public class Transferencia {
    private String id;
    private String idCompra;
    private String idComercio;
    private double monto;
    private LocalDate fecha;

    public Transferencia(String id, String idCompra, String idComercio, double monto, LocalDate fecha) {
        this.id = id;
        this.idCompra = idCompra;
        this.idComercio = idComercio;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public String getIdComercio() {
        return idComercio;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

	public double getRutComercio() {
		// TODO Auto-generated method stub
		return 0;
	}
}
