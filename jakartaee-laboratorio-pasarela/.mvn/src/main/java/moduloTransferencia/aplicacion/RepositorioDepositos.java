package moduloTransferencia.aplicacion;

import moduloTransferencia.dominio.Deposito;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioDepositos {
    void guardar(Deposito deposito);
    List<Deposito> consultarPorComercioYRangoFechas(String rutComercio, LocalDateTime desde, LocalDateTime hasta);
}
