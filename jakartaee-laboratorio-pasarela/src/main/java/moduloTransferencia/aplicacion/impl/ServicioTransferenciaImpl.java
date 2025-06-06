package moduloTransferencia.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import moduloTransferencia.aplicacion.RepositorioDepositos;
import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.infraestructura.soap.ClienteSOAPBanco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class ServicioTransferenciaImpl implements ServicioTransferencia {

	@Inject
	@Named("repositorioJPA")
	private RepositorioDepositos repositorioDepositos;

    @Inject
    private ClienteSOAPBanco clienteSOAPBanco;

    private static final double COMISION = 0.02; // 2%

    @Override
    public void recibirNotificacionTransferenciaDesdeMedioPago(Map<String, Object> datosTransferencia) {
        try {
            String rutComercio = (String) datosTransferencia.get("rutComercio");
            Double monto = Double.parseDouble(datosTransferencia.get("monto").toString());

            String idTransferencia = UUID.randomUUID().toString();
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            boolean notificado = clienteSOAPBanco.notificarTransferencia(idTransferencia, rutComercio, monto, fecha);

            if (notificado) {
                double comision = monto * COMISION;
                double montoNeto = monto - comision;
                Deposito deposito = new Deposito(rutComercio, montoNeto, LocalDateTime.now(), comision);
                repositorioDepositos.guardar(deposito);
                System.out.println("[Transferencia] ✅ Notificación exitosa, depósito registrado.");
            } else {
                System.err.println("[Transferencia] ❌ Falló la notificación al banco.");
            }
        } catch (Exception e) {
            System.err.println("[Transferencia] ❌ Error al procesar transferencia: " + e.getMessage());
        }
    }

    @Override
    public List<Deposito> consultarDepositos(String rutComercio, LocalDateTime desde, LocalDateTime hasta) {
        return repositorioDepositos.consultarPorComercioYRangoFechas(rutComercio, desde, hasta);
    }

}
