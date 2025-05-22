package moduloTransferencia.infraestructura.persistencia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.dominio.RepositorioTransferencia;

public class RepositorioTransferenciaMemoria implements RepositorioTransferencia {

    private static final List<Deposito> DEPOSITOS = new ArrayList<>();

    @Override
    public void registrarDeposito(Deposito deposito) {
        DEPOSITOS.add(deposito);
    }

    @Override
    public Collection<Deposito> obtenerDepositos() {
        return DEPOSITOS;
    }
}
