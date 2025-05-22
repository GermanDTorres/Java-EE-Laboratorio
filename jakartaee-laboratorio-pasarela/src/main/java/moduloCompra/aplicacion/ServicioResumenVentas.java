package moduloCompra.aplicacion;

import moduloCompra.dominio.Compra;

import java.util.Date;
import java.util.List;

public interface ServicioResumenVentas {
    List<Compra> resumenVentasDiarias(String idComercio);
    List<Compra> resumenVentasPorPeriodo(String idComercio, Date desde, Date hasta);
    double montoActualVendido(String idComercio);
}
