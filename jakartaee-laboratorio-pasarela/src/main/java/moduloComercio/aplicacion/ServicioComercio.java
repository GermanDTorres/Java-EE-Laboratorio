package moduloComercio.aplicacion;

import moduloComercio.dominio.Comercio;

import java.util.List;

public interface ServicioComercio {
    void registrarComercio(Comercio comercio);
    Comercio obtenerComercio(String id);
    List<Comercio> obtenerTodos();
}