package moduloComercio.aplicacion;

import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.POS;

import java.util.List;

public interface ServicioComercio {
    void registrarComercio(Comercio comercio);
    Comercio obtenerComercio(String rut);
    List<Comercio> obtenerTodos();
    void modificarComercio(Comercio comercio);

    void altaPOS(String comercioId, String posId);
    void cambiarEstadoPOS(String comercioId, String posId, boolean estado);
    void cambioContrasena(String comercioId, String nuevaPass);

    boolean existeComercio(String rut);
    boolean autenticar(String idComercio, String contrasena);

    POS buscarPOS(String idPOS);
	void realizarReclamo(String textoReclamo, String comercioId);
	void procesarReclamo(String texto, String comercioId);
}
