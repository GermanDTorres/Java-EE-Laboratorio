package moduloComercio.infraestructura.persistencia;

import moduloComercio.dominio.POS;

import java.util.List;
import java.util.Optional;

public interface RepositorioPOS {

    void guardar(POS pos);

    Optional<POS> buscarPorId(String idPOS);

    List<POS> obtenerTodos();

    void actualizar(POS pos);

    void eliminarPorId(String id);
    
    boolean existePOS(String id);
}
