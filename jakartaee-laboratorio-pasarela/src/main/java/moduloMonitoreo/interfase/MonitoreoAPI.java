package moduloMonitoreo.interfase;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import moduloMonitoreo.aplicacion.ServicioMonitoreo;

@Path("/monitoreo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class MonitoreoAPI {

    @Inject
    private ServicioMonitoreo servicio;

    public MonitoreoAPI() {
        // Ya no se instancia manualmente
    }

    @POST
    @Path("/notificarPago")
    public Response notificarPago(@QueryParam("idCompra") String idCompra,
                                  @QueryParam("idComercio") String idComercio,
                                  @QueryParam("monto") double monto) {
        servicio.notificarPago(idCompra, idComercio, monto);
        return Response.ok("Notificación de pago recibida").build();
    }

    @POST
    @Path("/notificarPagoOk")
    public Response notificarPagoOk(@QueryParam("idCompra") String idCompra,
                                    @QueryParam("idComercio") String idComercio,
                                    @QueryParam("monto") double monto) {
        servicio.notificarPagoOk(idCompra, idComercio, monto);
        return Response.ok("Notificación de pago OK recibida").build();
    }

    @POST
    @Path("/notificarPagoError")
    public Response notificarPagoError(@QueryParam("idCompra") String idCompra,
                                       @QueryParam("idComercio") String idComercio,
                                       @QueryParam("monto") double monto) {
        servicio.notificarPagoError(idCompra, idComercio, monto);
        return Response.ok("Notificación de pago error recibida").build();
    }

    @POST
    @Path("/notificarTransferencia")
    public Response notificarTransferencia(@QueryParam("idTransferencia") String idTransferencia,
                                           @QueryParam("idComercio") String idComercio,
                                           @QueryParam("monto") double monto) {
        String info = "Transferencia ID=" + idTransferencia + ", Comercio=" + idComercio + ", Monto=" + monto;
        servicio.notificarTransferencia(info);
        return Response.ok("Notificación de transferencia recibida").build();
    }
}
