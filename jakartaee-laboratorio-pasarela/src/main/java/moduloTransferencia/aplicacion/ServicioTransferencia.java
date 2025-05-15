package moduloTransferencia.aplicacion;

import java.time.LocalDate;
import java.util.List;
import moduloTransferencia.dominio.Transferencia;

public interface ServicioTransferencia {
    void recibirNotificacionTransferenciaDesdeMedioPago(String info);
    List<Transferencia> consultarDepositos(String comercioId, LocalDate desde, LocalDate hasta);
}
