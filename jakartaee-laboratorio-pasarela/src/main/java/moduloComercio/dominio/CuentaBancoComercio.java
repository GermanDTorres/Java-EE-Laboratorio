package moduloComercio.dominio;

public class CuentaBancoComercio {
    private Integer nroCuenta;

    public CuentaBancoComercio(Integer nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public Integer getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(int nroCuenta) {
        this.nroCuenta = nroCuenta;
    }
}
