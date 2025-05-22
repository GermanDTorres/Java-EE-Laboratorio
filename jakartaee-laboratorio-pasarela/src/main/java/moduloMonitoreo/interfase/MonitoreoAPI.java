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

    private final ServicioMonitoreo servicio;

    public MonitoreoAPI() {
        this.servicio = new ServicioMonitoreoImpl();
    }

    @POST
    @Path("/notificarPago")
    public Response notificarPago(@QueryParam("idCompra") String idCompra,
                                  @QueryParam("idComercio") String idComercio,
                                  @QueryParam("monto") double monto) {
        servicio.notificarPago(idCompra, idComercio, monto);
        return Response.ok().entity("Notificación de pago recibida").build();
    }

    @POST
    @Path("/notificarPagoOk")
    public Response notificarPagoOk(@QueryParam("idCompra") String idCompra,
                                    @QueryParam("idComercio") String idComercio,
                                    @QueryParam("monto") double monto) {
        servicio.notificarPagoOk(idCompra, idComercio, monto);
        return Response.ok().entity("Notificación de pago OK recibida").build();
    }

    @POST
    @Path("/notificarPagoError")
    public Response notificarPagoError(@QueryParam("idCompra") String idCompra,
                                       @QueryParam("idComercio") String idComercio,
                                       @QueryParam("monto") double monto) {
        servicio.notificarPagoError(idCompra, idComercio, monto);
        return Response.ok().entity("Notificación de pago error recibida").build();
    }
}
