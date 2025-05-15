package moduloCompra.aplicacion;


import moduloCompra.dominio.Compra;

import java.util.List;

public interface ServicioCompra {
    void procesarCompra(Compra compra);
    List<Compra> obtenerCompras();
    Compra obtenerCompra(String id);
    
    //nuevo?
	void registrarCompra(Compra compra);
}
