package moduloComercio.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.POS;
import moduloComercio.dominio.RepositorioComercio;
import moduloComercio.infraestructura.persistencia.RepositorioPOS;

import java.util.Optional;

@ApplicationScoped
public class ServicioAltaPOS {

    @Inject
    private RepositorioPOS repositorioPOS;

    @Inject
    private RepositorioComercio repositorioComercio;

    public void registrarPOS(String rutComercio, POS nuevoPOS) {
        Optional<Comercio> comercioOpt = repositorioComercio.buscarPorRut(rutComercio);
        if (comercioOpt.isEmpty()) {
            throw new IllegalArgumentException("Comercio no encontrado con RUT: " + rutComercio);
        }

        if (repositorioPOS.existePOS(nuevoPOS.getId())) {
            throw new IllegalArgumentException("Ya existe un POS con el ID: " + nuevoPOS.getId());
        }

        Comercio comercio = comercioOpt.get();
        comercio.agregarPOS(nuevoPOS);
        repositorioPOS.guardar(nuevoPOS);
        repositorioComercio.actualizar(comercio);
    }
}
