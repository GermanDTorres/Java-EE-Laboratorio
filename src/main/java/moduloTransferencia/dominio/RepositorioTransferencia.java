package moduloTransferencia.dominio;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface RepositorioTransferencia {
    void registrarDeposito(Deposito deposito);
    Collection<Deposito> obtenerDepositos();
	List<Deposito> obtenerDepositosPorComercioYRango(String rutComercio, LocalDate inicio, LocalDate fin);
}
