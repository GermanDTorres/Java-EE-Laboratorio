package moduloTransferencia.infraestructura.persistencia;

import java.time.LocalDateTime;
import java.util.List;

import moduloTransferencia.dominio.Deposito;

public interface RepositorioDeposito {
    void guardar(Deposito deposito);
    List<Deposito> consultarPorComercioYRangoFechas(String rutComercio, LocalDateTime fechaDesde, LocalDateTime fechaHasta);
}
