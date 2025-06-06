package moduloCompra.aplicacion;

import moduloCompra.dominio.Compra;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ServicioCompra {
    List<Compra> resumenVentasDiarias(String idComercio);
    List<Compra> resumenVentasPorPeriodo(String idComercio, Date desde, Date hasta);
    
    double montoActualVendido(String idComercio);
	Optional<Compra> obtenerCompra(String idCompra);
	Compra procesarCompra(Compra compra);
	List<Compra> obtenerCompras();
}
