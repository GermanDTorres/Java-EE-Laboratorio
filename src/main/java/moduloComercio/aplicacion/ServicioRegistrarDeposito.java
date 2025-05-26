package moduloComercio.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.dominio.RepositorioTransferencia;

@ApplicationScoped
public class ServicioRegistrarDeposito {

    @Inject
    private RepositorioTransferencia repositorio;

    public void ejecutar(Deposito deposito) {
        repositorio.registrarDeposito(deposito);
    }
}
