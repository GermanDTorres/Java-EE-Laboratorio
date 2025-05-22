package moduloComercio.infraestructura.persistencia;

import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.RepositorioComercio;

import java.util.*;

public class RepositorioComercioMemoria implements RepositorioComercio {

    private static final Map<String, Comercio> comercios = new HashMap<>();

    @Override
    public void guardar(Comercio comercio) {
        comercios.put(comercio.getRut(), comercio);
    }

    @Override
    public Optional<Comercio> buscarPorRut(String rut) {
        return Optional.ofNullable(comercios.get(rut));
    }

    @Override
    public List<Comercio> obtenerTodos() {
        return new ArrayList<>(comercios.values());
    }

    @Override
    public void actualizar(Comercio comercio) {
        if (comercios.containsKey(comercio.getRut())) {
            comercios.put(comercio.getRut(), comercio);
        }
    }

    @Override
    public void eliminarPorRut(String rut) {
        comercios.remove(rut);
    }

    @Override
    public boolean existe(String rut) {
        return comercios.containsKey(rut);
    }

    @Override
    public Comercio buscarPorId(String id) {
        return comercios.get(id);
    }

    @Override
    public List<Comercio> listarTodos() {
        return new ArrayList<>(comercios.values());
    }

    @Override
    public void guardarComercio(Comercio comercio) {
        guardar(comercio);
    }

    @Override
    public void actualizarComercio(Comercio comercio) {
        actualizar(comercio);
    }

    @Override
    public Comercio obtenerComercio(String rutComercio) {
        return comercios.get(rutComercio);
    }
}
