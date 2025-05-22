package moduloTransferencia.dominio;

import java.util.Collection;

public interface RepositorioTransferencia {
    void registrarDeposito(Deposito deposito);
    Collection<Deposito> obtenerDepositos();
}
