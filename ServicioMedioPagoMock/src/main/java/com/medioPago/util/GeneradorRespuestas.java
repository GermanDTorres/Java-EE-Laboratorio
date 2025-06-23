package com.medioPago.util;

import com.medioPago.modelo.SolicitudPagoDTO;
import com.medioPago.modelo.RespuestaPagoDTO;

import java.util.Random;

public class GeneradorRespuestas {

    private static final Random random = new Random();

    public static RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) {
        String tarjeta = solicitud.getNumeroTarjeta();
        RespuestaPagoDTO respuesta = new RespuestaPagoDTO();

        System.out.println("📨 Recibida solicitud de pago:");
        System.out.println(" - Comercio: " + solicitud.getIdComercio());
        System.out.println(" - Monto: " + solicitud.getMonto());
        System.out.println(" - Tarjeta: " + tarjeta);
        System.out.println("----------------------------------------");
        // Rechazar siempre si termina en 9999
        if (tarjeta != null && tarjeta.endsWith("9999")) {
            respuesta.setAutorizado(false);
            respuesta.setCodigoAutorizacion("ERR-RECHAZADA-" + System.currentTimeMillis());
            respuesta.setMensaje("Pago rechazado: tarjeta bloqueada (termina en 9999)");
            System.out.println("❌ Pago rechazado por terminación 9999");
        } else {
            // Probabilidad de aprobar 5/6 y rechazar 1/6
            int prob = random.nextInt(6); // 0..5
            if (prob < 5) {  // 5 casos de 6: aprobar
                respuesta.setAutorizado(true);
                respuesta.setCodigoAutorizacion("OK-" + System.currentTimeMillis());
                respuesta.setMensaje("Pago aprobado");
                System.out.println("✅ Pago aprobado (probabilístico)");
            } else {  // 1 caso de 6: rechazar
                respuesta.setAutorizado(false);
                respuesta.setCodigoAutorizacion("ERR-RECHAZADA-PROB-" + System.currentTimeMillis());
                respuesta.setMensaje("Pago rechazado (probabilístico)");
                System.out.println("❌ Pago rechazado (probabilístico)");
            }
        }
        System.out.println("----------------------------------------");
        return respuesta;
    }
}
