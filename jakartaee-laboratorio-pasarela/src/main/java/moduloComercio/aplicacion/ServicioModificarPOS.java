package moduloComercio.aplicacion;

import moduloComercio.dominio.EstadoPOS;
import moduloComercio.infraestructura.persistencia.*;
import moduloComercio.dominio.POS;

import java.util.Optional;

public class ServicioModificarPOS {

    private final RepositorioPOS repositorioPOS;

    public ServicioModificarPOS(RepositorioPOS repositorioPOS) {
        this.repositorioPOS = repositorioPOS;
    }

    public void cambiarEstadoPOS(int idPOS, EstadoPOS nuevoEstado) {
        Optional<POS> posOpt = repositorioPOS.buscarPorId(idPOS);
        if (posOpt.isEmpty()) {
            throw new IllegalArgumentException("POS no encontrado con ID: " + idPOS);
        }
        POS pos = posOpt.get();
        pos.setEstado(nuevoEstado);
        repositorioPOS.guardar(pos); // Actualiza el POS
    }
}
