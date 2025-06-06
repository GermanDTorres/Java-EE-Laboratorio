package moduloTransferencia.aplicacion;

import moduloTransferencia.dominio.Deposito;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ServicioTransferencia {
    void recibirNotificacionTransferenciaDesdeMedioPago(Map<String, Object> datosTransferencia);
    List<Deposito> consultarDepositos(String rutComercio, LocalDateTime desde, LocalDateTime hasta);
}
