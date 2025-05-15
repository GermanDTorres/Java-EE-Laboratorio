package moduloTransferencia.interfase;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import moduloTransferencia.aplicacion.impl.ServicioTransferenciaImpl;
import moduloTransferencia.dominio.Transferencia;
import moduloTransferencia.infraestructura.persistencia.RepositorioTransferenciaMemoria;

import java.time.LocalDate;
import java.util.List;

@Path("/transferencias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferenciaAPI {

    private static final RepositorioTransferenciaMemoria repositorio = new RepositorioTransferenciaMemoria();
    private final ServicioTransferenciaImpl servicio = new ServicioTransferenciaImpl(repositorio);

    @POST
    @Path("/notificar")
    public Response recibirNotificacion(String info) {
        servicio.recibirNotificacionTransferenciaDesdeMedioPago(info);
        return Response.ok().build();
    }

    @GET
    @Path("/consultar")
    public List<Transferencia> consultarDepositos(
            @QueryParam("comercioId") String comercioId,
            @QueryParam("desde") String desde,
            @QueryParam("hasta") String hasta
    ) {
        return servicio.consultarDepositos(comercioId, LocalDate.parse(desde), LocalDate.parse(hasta));
    }
}
