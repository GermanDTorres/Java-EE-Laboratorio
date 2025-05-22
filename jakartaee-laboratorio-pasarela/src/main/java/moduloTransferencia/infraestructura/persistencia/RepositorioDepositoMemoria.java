package moduloTransferencia.infraestructura.persistencia;

import moduloTransferencia.dominio.Deposito;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDepositoMemoria implements RepositorioDeposito {
    private static final List<Deposito> depositoDB = new ArrayList<>();

    @Override
    public void guardar(Deposito deposito) {
        depositoDB.add(deposito);
        System.out.println("[RepositorioDepositoEnMemoria] Depósito guardado: " + deposito);
    }

    @Override
    public List<Deposito> consultarPorComercioYRangoFechas(String rutComercio, LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return depositoDB.stream()
                .filter(d -> d.getRutComercio().equals(rutComercio)
                        && !d.getFechaDeposito().isBefore(fechaDesde)
                        && !d.getFechaDeposito().isAfter(fechaHasta))
                .collect(Collectors.toList());
    }
}
