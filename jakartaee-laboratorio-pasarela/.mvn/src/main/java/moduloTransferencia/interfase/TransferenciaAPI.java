package moduloTransferencia.interfase;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.Deposito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Path("/transferencias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class TransferenciaAPI {

    @Inject
    private ServicioTransferencia servicio;

    @POST
    @Path("/notificacion")
    public Response recibirNotificacion(Map<String, Object> datosTransferencia) {
        try {
            servicio.recibirNotificacionTransferenciaDesdeMedioPago(datosTransferencia);
            return Response.ok().entity("Notificación recibida correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al procesar notificación: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/depositos")
    public Response consultarDepositos(
            @QueryParam("rutComercio") String rutComercio,
            @QueryParam("fechaDesde") String fechaDesdeStr,
            @QueryParam("fechaHasta") String fechaHastaStr) {

        try {
            if (rutComercio == null || rutComercio.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El parámetro 'rutComercio' es obligatorio").build();
            }
            if (fechaDesdeStr == null || fechaHastaStr == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los parámetros 'fechaDesde' y 'fechaHasta' son obligatorios").build();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime fechaDesde = LocalDateTime.parse(fechaDesdeStr, formatter);
            LocalDateTime fechaHasta = LocalDateTime.parse(fechaHastaStr, formatter);

            List<Deposito> depositos = servicio.consultarDepositos(rutComercio, fechaDesde, fechaHasta);
            return Response.ok(depositos).build();

        } catch (DateTimeParseException dtpe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Formato de fecha inválido. Use formato ISO_LOCAL_DATE_TIME, por ejemplo: 2025-05-24T15:30:00")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error en parámetros de consulta: " + e.getMessage())
                    .build();
        }
    }
}
