package moduloTransferencia.interfase;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.aplicacion.impl.ServicioTransferenciaImpl;
import moduloTransferencia.dominio.Deposito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Path("/transferencias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferenciaAPI {

    private final ServicioTransferencia servicio = new ServicioTransferenciaImpl();

    @POST
    @Path("/notificacion")
    public Response recibirNotificacion(Map<String, Object> datosTransferencia) {
        try {
            servicio.recibirNotificacionTransferenciaDesdeMedioPago(datosTransferencia);
            return Response.ok().build();
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
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime fechaDesde = LocalDateTime.parse(fechaDesdeStr, formatter);
            LocalDateTime fechaHasta = LocalDateTime.parse(fechaHastaStr, formatter);

            List<Deposito> depositos = servicio.consultarDepositos(rutComercio, fechaDesde, fechaHasta);
            return Response.ok(depositos).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error en parámetros de consulta: " + e.getMessage())
                    .build();
        }
    }
}
