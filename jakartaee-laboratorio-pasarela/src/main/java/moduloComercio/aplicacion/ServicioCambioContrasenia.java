package moduloComercio.aplicacion;

import moduloComercio.dominio.RepositorioComercio;

public class ServicioCambioContrasenia {

    private final RepositorioComercio repositorio;

    public ServicioCambioContrasenia(RepositorioComercio repositorio) {
        this.repositorio = repositorio;
    }

    public void ejecutar(String rutComercio, String nuevaContrasenia) {
        var comercio = repositorio.buscarPorRut(rutComercio);
        if (comercio != null) {
            comercio.get().cambiarContrasena(nuevaContrasenia);
        } else {
            throw new RuntimeException("Comercio no encontrado");
        }
    }

}
