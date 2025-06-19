package moduloCompra.interfase.evento.out;

public class CompraRechazada {
    private final String idCompra;
    private final String idComercio;
    private final double monto;

    public CompraRechazada(String idCompra, String idComercio, double monto) {
        this.idCompra = idCompra;
        this.idComercio = idComercio;
        this.monto = monto;
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
}
