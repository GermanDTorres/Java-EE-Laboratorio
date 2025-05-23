package moduloComercio.aplicacion;

import moduloComercio.dominio.Comercio;
import java.util.List;

public interface ServicioComercio {
    void registrarComercio(Comercio comercio);
    Comercio obtenerComercio(String rut);
    List<Comercio> obtenerTodos();
    void modificarComercio(Comercio comercio);

    void altaPOS(String comercioId, int posId);
    void cambiarEstadoPOS(String comercioId, int posId, boolean estado);
    void cambioContrasena(String comercioId, String nuevaPass);

    boolean existeComercio(String rut);
    boolean autenticar(String idComercio, String contrasena);

}
