package moduloComercio.aplicacion;

import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.POS;
import moduloComercio.dominio.RepositorioComercio;
import moduloComercio.infraestructura.persistencia.RepositorioPOS;

import java.util.Optional;

public class ServicioAltaPOS {

    private final RepositorioPOS repositorioPOS;
    private final RepositorioComercio repositorioComercio;

    public ServicioAltaPOS(RepositorioPOS repositorioPOS, RepositorioComercio repositorioComercio) {
        this.repositorioPOS = repositorioPOS;
        this.repositorioComercio = repositorioComercio;
    }

    public void registrarPOS(String rutComercio, POS nuevoPOS) {
        Optional<Comercio> comercioOpt = repositorioComercio.buscarPorRut(rutComercio);
        if (comercioOpt.isEmpty()) {
            throw new IllegalArgumentException("Comercio no encontrado con RUT: " + rutComercio);
        }

        if (repositorioPOS.existePOS(nuevoPOS.getId())) {
            throw new IllegalArgumentException("Ya existe un POS con el ID: " + nuevoPOS.getId());
        }

        Comercio comercio = comercioOpt.get();
        comercio.agregarPOS(nuevoPOS); // Asociación dentro del modelo
        repositorioPOS.guardar(nuevoPOS);
        repositorioComercio.actualizar(comercio); // Persistimos cambio en el comercio
    }
}
