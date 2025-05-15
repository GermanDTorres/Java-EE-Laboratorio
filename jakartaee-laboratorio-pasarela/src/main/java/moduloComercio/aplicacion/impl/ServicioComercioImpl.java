package moduloComercio.aplicacion.impl;

import moduloComercio.aplicacion.ServicioComercio;
import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.RepositorioComercio;

import java.util.List;

public class ServicioComercioImpl implements ServicioComercio {
    private final RepositorioComercio repositorio;

    public ServicioComercioImpl(RepositorioComercio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void registrarComercio(Comercio comercio) {
        repositorio.guardar(comercio);
    }

    @Override
    public Comercio obtenerComercio(String id) {
        return repositorio.buscarPorId(id);
    }

    @Override
    public List<Comercio> obtenerTodos() {
        return repositorio.listarTodos();
    }
}
