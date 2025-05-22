package moduloComercio.dominio;

import java.util.List;
import java.util.Optional;

public interface RepositorioComercio {
    void guardar(Comercio comercio);
    Optional<Comercio> buscarPorRut(String rut);
    List<Comercio> obtenerTodos();
    void actualizar(Comercio comercio);
    void eliminarPorRut(String rut);
    boolean existe(String rut);

    Comercio buscarPorId(String id);
    List<Comercio> listarTodos();
    void guardarComercio(Comercio comercio);
    void actualizarComercio(Comercio comercio);
    Comercio obtenerComercio(String rutComercio);
}
