package moduloComercio.infraestructura.persistencia;

import moduloComercio.dominio.POS;

import java.util.*;

public class RepositorioPOSMemoria implements RepositorioPOS {

    private static final Map<Integer, POS> posMap = new HashMap<>();

    @Override
    public void guardar(POS pos) {
        posMap.put(pos.getId(), pos);
    }

    @Override
    public Optional<POS> buscarPorId(int id) {
        return Optional.ofNullable(posMap.get(id));
    }

    @Override
    public List<POS> obtenerTodos() {
        return new ArrayList<>(posMap.values());
    }

    @Override
    public void actualizar(POS pos) {
        if (posMap.containsKey(pos.getId())) {
            posMap.put(pos.getId(), pos);
        }
    }

    @Override
    public void eliminarPorId(int id) {
        posMap.remove(id);
    }

    @Override
    public boolean existePOS(int id) {
        return posMap.containsKey(id);
    }
}
