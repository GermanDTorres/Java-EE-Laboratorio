package moduloMonitoreo.interfase;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import moduloMonitoreo.aplicacion.ServicioMonitoreo;
import moduloMonitoreo.aplicacion.impl.ServicioMonitoreoImpl;

@Path("/monitoreo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MonitoreoAPI {

    private static final ServicioMonitoreo servicio = new ServicioMonitoreoImpl();

    @POST
    @Path("/pago")
    public Response notificarPago(String mensaje) {
        servicio.notificarPago(mensaje, 0);
        return Response.ok().build();
    }

    @POST
    @Path("/pago-ok")
    public Response notificarPagoOk(String mensaje) {
        servicio.notificarPagoOk(mensaje, 0);
        return Response.ok().build();
    }

    @POST
    @Path("/pago-error")
    public Response notificarPagoError(String mensaje) {
        servicio.notificarPagoError(mensaje, mensaje);
        return Response.ok().build();
    }

    @POST
    @Path("/transferencia")
    public Response notificarTransferencia(String mensaje) {
        servicio.notificarTransferencia(mensaje, mensaje, 0);
        return Response.ok().build();
    }

    @POST
    @Path("/reclamo")
    public Response notificarReclamoComercio(String mensaje) {
        servicio.notificarReclamoComercio(mensaje, mensaje);
        return Response.ok().build();
    }
}
