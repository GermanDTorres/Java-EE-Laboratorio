package moduloTransferencia.aplicacion;

import java.util.List;
import moduloTransferencia.dominio.Deposito;

public interface RepositorioDepositos {
    void guardar(Deposito deposito);
    List<Deposito> obtenerTodos();
}
