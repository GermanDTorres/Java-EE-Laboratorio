package moduloComercio.infraestructura.persistencia;

import moduloComercio.dominio.CuentaBancoComercio;

import java.util.List;
import java.util.Optional;

public interface RepositorioCuentaBanco {

    void guardar(CuentaBancoComercio cuenta);

    Optional<CuentaBancoComercio> buscarPorNumero(int nroCuenta);

    List<CuentaBancoComercio> obtenerTodas();

    void actualizar(CuentaBancoComercio cuenta);

    void eliminarPorNumero(int nroCuenta);
}
