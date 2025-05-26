package moduloTransferencia.aplicacion;

import moduloTransferencia.dominio.Deposito;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioTransferencias {
    void guardarDeposito(Deposito deposito);
    List<Deposito> obtenerDepositosPorComercioYFecha(String idComercio, LocalDate desde, LocalDate hasta);
}
