package moduloComercio.aplicacion;

import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.dominio.RepositorioTransferencia;

public class ServicioRegistrarDeposito {

    private final RepositorioTransferencia repositorio;

    public ServicioRegistrarDeposito(RepositorioTransferencia repositorio) {
        this.repositorio = repositorio;
    }

    public void ejecutar(Deposito deposito) {
        repositorio.registrarDeposito(deposito);
    }
}
