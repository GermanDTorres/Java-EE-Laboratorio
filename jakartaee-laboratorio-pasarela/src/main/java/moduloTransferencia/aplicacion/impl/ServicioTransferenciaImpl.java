package moduloTransferencia.aplicacion.impl;

import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.infraestructura.persistencia.RepositorioDeposito;
import moduloTransferencia.infraestructura.persistencia.RepositorioDepositoMemoria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServicioTransferenciaImpl implements ServicioTransferencia {

    private static final double COMISION_PORCENTAJE = 0.03; // 3% de comisión

    private final RepositorioDeposito repositorioDeposito;

    public ServicioTransferenciaImpl() {
        this.repositorioDeposito = new RepositorioDepositoMemoria();
    }

    @Override
    public void recibirNotificacionTransferenciaDesdeMedioPago(Map<String, Object> datosTransferencia) {
        try {
            if (datosTransferencia == null || datosTransferencia.isEmpty()) {
                throw new IllegalArgumentException("Datos de transferencia no pueden ser vacíos");
            }

            String rutComercio = (String) datosTransferencia.get("rutComercio");
            if (rutComercio == null || rutComercio.trim().isEmpty()) {
                throw new IllegalArgumentException("rutComercio no puede ser nulo o vacío");
            }

            String cuentaBanco = (String) datosTransferencia.get("cuentaBanco");
            if (cuentaBanco == null || cuentaBanco.trim().isEmpty()) {
                throw new IllegalArgumentException("cuentaBanco no puede ser nulo o vacío");
            }

            Object montoObj = datosTransferencia.get("monto");
            double montoBruto;
            if (montoObj instanceof Number) {
                montoBruto = ((Number) montoObj).doubleValue();
                if (montoBruto <= 0) {
                    throw new IllegalArgumentException("Monto debe ser mayor a cero");
                }
            } else {
                throw new IllegalArgumentException("El campo monto no es un número válido");
            }

            Object fechaObj = datosTransferencia.get("fecha");
            LocalDateTime fecha;
            if (fechaObj instanceof String) {
                String fechaStr = (String) fechaObj;
                if (fechaStr.length() == 10) {  // yyyy-MM-dd
                    LocalDate localDate = LocalDate.parse(fechaStr);
                    fecha = localDate.atStartOfDay();
                } else {
                    fecha = LocalDateTime.parse(fechaStr);
                }
            } else if (fechaObj instanceof LocalDateTime) {
                fecha = (LocalDateTime) fechaObj;
            } else {
                fecha = LocalDateTime.now();
            }

            double comision = montoBruto * COMISION_PORCENTAJE;

            Deposito deposito = new Deposito(
                    UUID.randomUUID().toString(),
                    rutComercio,
                    cuentaBanco,
                    montoBruto,
                    comision,
                    fecha
            );

            repositorioDeposito.guardar(deposito);

            System.out.println("[ServicioTransferencia] Notificación recibida y depósito registrado: " + deposito);

        } catch (Exception e) {
            System.err.println("Error al procesar notificación: " + e.getMessage());
            // Podrías re-lanzar o manejar según convenga
        }
    }


    @Override
    public List<Deposito> consultarDepositos(String rutComercio, LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        if (rutComercio == null || rutComercio.trim().isEmpty()) {
            throw new IllegalArgumentException("rutComercio no puede ser nulo o vacío");
        }
        if (fechaDesde == null || fechaHasta == null) {
            throw new IllegalArgumentException("Fechas no pueden ser nulas");
        }
        if (fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException("fechaDesde no puede ser posterior a fechaHasta");
        }

        List<Deposito> resultados = repositorioDeposito.consultarPorComercioYRangoFechas(rutComercio, fechaDesde, fechaHasta);
        System.out.println("[ServicioTransferencia] Consulta depósitos para comercio " + rutComercio
                + " entre " + fechaDesde + " y " + fechaHasta + ": " + resultados.size() + " encontrados.");
        return resultados;
    }
}
