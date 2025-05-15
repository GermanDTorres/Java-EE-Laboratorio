package moduloTransferencia.dominio;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioTransferencia {
    void guardar(Transferencia transferencia);
    List<Transferencia> obtenerPorComercioYRango(String comercioId, LocalDate desde, LocalDate hasta);
}
