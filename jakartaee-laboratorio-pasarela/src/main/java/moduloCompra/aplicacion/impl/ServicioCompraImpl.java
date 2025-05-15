package moduloCompra.aplicacion.impl;


import moduloCompra.aplicacion.ServicioCompra;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.List;

public class ServicioCompraImpl implements ServicioCompra {
    private final RepositorioCompra repositorio;

    public ServicioCompraImpl(RepositorioCompra repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void procesarCompra(Compra compra) {
        repositorio.guardar(compra);
    }

    @Override
    public List<Compra> obtenerCompras() {
        return repositorio.listarTodas();
    }

    @Override
    public Compra obtenerCompra(String id) {
        return repositorio.buscarPorId(id);
    }

	@Override
	public void registrarCompra(Compra compra) {
		// TODO Auto-generated method stub
		
	}
}
