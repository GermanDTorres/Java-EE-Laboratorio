package com.medioPago.util;

import com.medioPago.modelo.SolicitudPagoDTO;
import com.medioPago.modelo.RespuestaPagoDTO;

public class GeneradorRespuestas {

    public static RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) {
        RespuestaPagoDTO respuesta = new RespuestaPagoDTO();

        System.out.println("Recibida solicitud de pago:");
        System.out.println(" - Comercio: " + solicitud.getIdComercio());
        System.out.println(" - Monto: " + solicitud.getMonto());
        System.out.println(" - Tarjeta: " + solicitud.getNumeroTarjeta());
        System.out.println("----------------------------------------");

        
        respuesta.setAutorizado(true);
        respuesta.setCodigoAutorizacion("OK-" + System.currentTimeMillis());
        respuesta.setMensaje("Pago aprobado para todas las tarjetas");

        System.out.println("Pago aprobado");

        return respuesta;
    }
}


/*package com.medioPago.util;

import com.medioPago.modelo.SolicitudPagoDTO;
import com.medioPago.modelo.RespuestaPagoDTO;

import java.util.Random;

public class GeneradorRespuestas {

    public static RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) {
        String tarjeta = solicitud.getNumeroTarjeta();
        RespuestaPagoDTO respuesta = new RespuestaPagoDTO();

        System.out.println("Recibida solicitud de pago:");
        System.out.println(" - Comercio: " + solicitud.getIdComercio());
        System.out.println(" - Monto: " + solicitud.getMonto());
        System.out.println(" - Tarjeta: " + tarjeta);
        System.out.println("----------------------------------------");

        if ("0000-0000-0000-0000".equals(tarjeta)) {
            respuesta.setAutorizado(true);
            respuesta.setCodigoAutorizacion("APROBADO-FIJO");
            respuesta.setMensaje("Pago aprobado con tarjeta especial 0000");

            System.out.println("Pago aprobado con tarjeta 0000");
        } else if ("9999-9999-9999-9999".equals(tarjeta)) {
            respuesta.setAutorizado(false);
            respuesta.setCodigoAutorizacion("RECHAZADO-FIJO");
            respuesta.setMensaje("Pago rechazado con tarjeta especial 9999");

            System.out.println("Pago rechazado con tarjeta 9999");
        } else {
            boolean aprobado = new Random().nextInt(6) != 0;  // ~5:1 aprobado
            respuesta.setAutorizado(aprobado);
            if (aprobado) {
                respuesta.setCodigoAutorizacion("OK-" + System.currentTimeMillis());
                respuesta.setMensaje("Pago aprobado");

                System.out.println("Pago aprobado aleatorio");
            } else {
                respuesta.setCodigoAutorizacion("ERR-" + System.currentTimeMillis());
                respuesta.setMensaje("Pago rechazado por reglas internas");

                System.out.println("Pago rechazado aleatorio");
            }
        }

        return respuesta;
    }
}
*/