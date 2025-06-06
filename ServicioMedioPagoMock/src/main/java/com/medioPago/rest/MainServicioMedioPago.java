package com.medioPago.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medioPago.modelo.SolicitudPagoDTO;
import com.medioPago.modelo.RespuestaPagoDTO;
import com.medioPago.util.GeneradorRespuestas;
import io.javalin.Javalin;

public class MainServicioMedioPago {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8082);  

        app.post("/ServicioMedioPagoMock/api/autorizarPago", ctx -> {
            ObjectMapper mapper = new ObjectMapper();
            SolicitudPagoDTO solicitud = mapper.readValue(ctx.body(), SolicitudPagoDTO.class);

            RespuestaPagoDTO respuesta = GeneradorRespuestas.procesarPago(solicitud);
            ctx.json(respuesta);
        });

        System.out.println("✅ Servicio MedioPagoMock iniciado en:");
        System.out.println("👉 http://localhost:9090/ServicioMedioPagoMock/api/autorizarPago");
    }
}
