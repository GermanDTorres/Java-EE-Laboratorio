package moduloComercio.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import moduloComercio.dominio.RepositorioComercio;

@ApplicationScoped
public class ServicioCambioContrasenia {

    @Inject
    private RepositorioComercio repositorio;

    public void ejecutar(String rutComercio, String nuevaContrasenia) {
        var comercioOpt = repositorio.buscarPorRut(rutComercio);
        if (comercioOpt.isPresent()) {
            comercioOpt.get().cambiarContrasena(nuevaContrasenia);
        } else {
            throw new RuntimeException("Comercio no encontrado");
        }
    }
}
