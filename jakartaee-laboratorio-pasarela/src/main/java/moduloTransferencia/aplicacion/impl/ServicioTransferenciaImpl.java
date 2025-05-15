package moduloTransferencia.aplicacion.impl;

import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.RepositorioTransferencia;
import moduloTransferencia.dominio.Transferencia;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ServicioTransferenciaImpl implements ServicioTransferencia {

    private final RepositorioTransferencia repositorio;

    public ServicioTransferenciaImpl(RepositorioTransferencia repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void recibirNotificacionTransferenciaDesdeMedioPago(String info) {
        // Supongamos que info tiene formato: comercioId,monto,yyyy-MM-dd
        String[] partes = info.split(",");
        String comercioId = partes[0];
        double monto = Double.parseDouble(partes[1]);
        LocalDate fecha = LocalDate.parse(partes[2]);

        // Aplicar comisión del 10%
        double montoFinal = monto * 0.9;

        Transferencia t = new Transferencia(UUID.randomUUID().toString(), comercioId, montoFinal, fecha);
        repositorio.guardar(t);

        System.out.println("TRANSFERENCIA: Recibida y registrada. Monto después de comisión: " + montoFinal);
    }

    @Override
    public List<Transferencia> consultarDepositos(String comercioId, LocalDate desde, LocalDate hasta) {
        return repositorio.obtenerPorComercioYRango(comercioId, desde, hasta);
    }
}
