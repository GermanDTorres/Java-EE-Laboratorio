package moduloTransferencia.infraestructura.persistencia;

import moduloTransferencia.dominio.Transferencia;
import moduloTransferencia.dominio.RepositorioTransferencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTransferenciaMemoria implements RepositorioTransferencia {

    private static final List<Transferencia> BD = new ArrayList<>();

    @Override
    public void guardar(Transferencia transferencia) {
        BD.add(transferencia);
    }

    @Override
    public List<Transferencia> obtenerPorComercioYRango(String comercioId, LocalDate desde, LocalDate hasta) {
        List<Transferencia> resultado = new ArrayList<>();
        for (Transferencia t : BD) {
            if (t.comercioId.equals(comercioId) &&
                (t.fecha.isEqual(desde) || t.fecha.isAfter(desde)) &&
                (t.fecha.isEqual(hasta) || t.fecha.isBefore(hasta))) {
                resultado.add(t);
            }
        }
        return resultado;
    }
}
