package moduloComercio.infraestructura.persistencia;

import moduloComercio.dominio.CuentaBancoComercio;
import moduloComercio.infraestructura.persistencia.RepositorioCuentaBanco;

import java.util.*;

public class RepositorioCuentaBancoMemoria implements RepositorioCuentaBanco {

    private static final Map<Integer, CuentaBancoComercio> cuentas = new HashMap<>();

    @Override
    public void guardar(CuentaBancoComercio cuenta) {
        cuentas.put(cuenta.getNroCuenta(), cuenta);
    }

    @Override
    public Optional<CuentaBancoComercio> buscarPorNumero(int nroCuenta) {
        return Optional.ofNullable(cuentas.get(nroCuenta));
    }

    @Override
    public List<CuentaBancoComercio> obtenerTodas() {
        return new ArrayList<>(cuentas.values());
    }

    @Override
    public void actualizar(CuentaBancoComercio cuenta) {
        if (cuentas.containsKey(cuenta.getNroCuenta())) {
            cuentas.put(cuenta.getNroCuenta(), cuenta);
        }
    }

    @Override
    public void eliminarPorNumero(int nroCuenta) {
        cuentas.remove(nroCuenta);
    }
}
