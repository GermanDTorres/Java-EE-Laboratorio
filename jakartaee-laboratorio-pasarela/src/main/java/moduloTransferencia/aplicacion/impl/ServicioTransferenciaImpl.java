package moduloTransferencia.aplicacion.impl;

import moduloTransferencia.infraestructura.servicios.impl.BancoAPIimplService;
import moduloTransferencia.infraestructura.servicios.impl.BancoAPIimpl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import moduloTransferencia.aplicacion.RepositorioDepositos;
import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.Deposito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.xml.ws.WebServiceRef;

@ApplicationScoped
public class ServicioTransferenciaImpl implements ServicioTransferencia {

    private static final Logger LOGGER = Logger.getLogger(ServicioTransferenciaImpl.class.getName());
    private static final double COMISION_PORCENTAJE = 0.03;

    @Inject
    private RepositorioDepositos repositorioDepositos;

	@WebServiceRef(wsdlLocation="http://localhost:8081/castlemock/mock/soap/project/cL0oVz/BancoAPIimplPort?wsdl")
	static BancoAPIimplService banco_service;


    @Override
    public void recibirNotificacionTransferenciaDesdeMedioPago(Map<String, Object> datosTransferencia) {
        if (datosTransferencia == null || datosTransferencia.isEmpty()) {
            LOGGER.severe("[Transferencia] Datos de transferencia vacíos o nulos.");
            return;
        }

            // Validación y extracción de datos
            String rutComercio = getString(datosTransferencia, "rutComercio");
            String cuentaBanco = getString(datosTransferencia, "cuentaBanco");
            double montoBruto = getDouble(datosTransferencia, "monto");
            LocalDateTime fecha = getFecha(datosTransferencia.get("fecha"));

            if (rutComercio == null || cuentaBanco == null || montoBruto <= 0 || fecha == null) {
                LOGGER.severe("[Transferencia] Datos inválidos. No se registrará la transferencia.");
                return;
            }

            double comision = montoBruto * COMISION_PORCENTAJE;

        try {
            Deposito deposito = new Deposito(
                UUID.randomUUID().toString(),
                rutComercio,
                cuentaBanco,
                montoBruto,
                comision,
                fecha
            );

            repositorioDepositos.guardar(deposito);
            LOGGER.info("[Transferencia] Depósito registrado correctamente: " + deposito);

        } catch (Exception e) {
            LOGGER.severe("[Transferencia] Error inesperado al registrar la transferencia: " + e.getMessage());
        }
	int codigo_respuesta_banco = -1;
	try{
		BancoAPIimpl banco_puerto= banco_service.getBancoAPIimplPort();
		codigo_respuesta_banco=banco_puerto.realizarTransferencia(cuentaBanco, montoBruto, fecha.toString());
	}catch(Exception e){
		LOGGER.severe("[Transferencia] Error inesperado al realizar la transferencia: " + e.getMessage());
	}
	switch(codigo_respuesta_banco){
		case 0:
			LOGGER.info("[Transferencia] Se realizó  la transferencia con exito.");
			break;
		default:
			LOGGER.severe("[Transferencia] Error insesperado al realizar la transferencia, proveniente del banco");
	
	}
    }

    @Override
    public List<Deposito> consultarDepositos(String rutComercio, LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return repositorioDepositos.consultarPorComercioYRangoFechas(rutComercio, fechaDesde, fechaHasta);
    }

    // --- Métodos auxiliares ---

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof String && !((String) value).isBlank()) {
            return ((String) value).trim();
        }
        LOGGER.warning("[Transferencia] Valor inválido para '" + key + "'");
        return null;
    }

    private double getDouble(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            LOGGER.warning("[Transferencia] Valor no numérico para '" + key + "'");
            return -1;
        }
    }

    private LocalDateTime getFecha(Object fechaObj) {
        if (fechaObj instanceof LocalDateTime) {
            return (LocalDateTime) fechaObj;
        }
        if (fechaObj instanceof String) {
            String fechaStr = ((String) fechaObj).trim();
            try {
                return LocalDateTime.parse(fechaStr);
            } catch (DateTimeParseException e1) {
                try {
                    return LocalDate.parse(fechaStr).atStartOfDay();
                } catch (DateTimeParseException e2) {
                    LOGGER.warning("[Transferencia] Fecha inválida: " + fechaStr);
                }
            }
        }
        LOGGER.warning("[Transferencia] No se pudo interpretar la fecha.");
        return null;
    }
}
