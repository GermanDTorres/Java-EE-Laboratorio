/*package moduloTransferencia.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.dominio.RepositorioTransferencia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class RepositorioTransferenciaMemoria implements RepositorioTransferencia {

    private final List<Deposito> depositos = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void registrarDeposito(Deposito deposito) {
        depositos.add(deposito);
        System.out.println("[RepositorioTransferenciaMemoria] Depósito registrado: " + deposito);
    }

    @Override
    public Collection<Deposito> obtenerDepositos() {
        return new ArrayList<>(depositos); // Devolvemos copia para evitar modificaciones externas
    }
}
*/