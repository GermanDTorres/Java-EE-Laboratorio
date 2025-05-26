package moduloComercio.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import moduloComercio.dominio.EstadoPOS;
import moduloComercio.dominio.POS;
import moduloComercio.infraestructura.persistencia.RepositorioPOS;

import java.util.Optional;

@ApplicationScoped
public class ServicioModificarPOS {

    @Inject
    private RepositorioPOS repositorioPOS;

    public void cambiarEstadoPOS(int idPOS, EstadoPOS nuevoEstado) {
        Optional<POS> posOpt = repositorioPOS.buscarPorId(idPOS);
        if (posOpt.isEmpty()) {
            throw new IllegalArgumentException("POS no encontrado con ID: " + idPOS);
        }
        POS pos = posOpt.get();
        pos.setEstado(nuevoEstado);
        repositorioPOS.guardar(pos);
    }
}
