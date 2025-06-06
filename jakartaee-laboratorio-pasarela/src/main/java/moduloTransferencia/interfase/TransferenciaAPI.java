package moduloTransferencia.interfase;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import moduloTransferencia.aplicacion.ServicioTransferencia;
import moduloTransferencia.dominio.Deposito;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/transferencias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferenciaAPI {

	@Inject

    private ServicioTransferencia servicio;

    @POST
    @Path("/notificacion")
    public void recibirNotificacionTransferencia(TransferenciaDTO dto) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("rutComercio", dto.getRutComercio());
        datos.put("monto", dto.getMonto());
        servicio.recibirNotificacionTransferenciaDesdeMedioPago(datos);
    }

    @GET
    @Path("/depositos")
    public List<Deposito> consultarDepositos(
            @QueryParam("rutComercio") String rutComercio,
            @QueryParam("desde") String desde,
            @QueryParam("hasta") String hasta) {

        LocalDateTime desdeDate = LocalDateTime.parse(desde);
        LocalDateTime hastaDate = LocalDateTime.parse(hasta);
        return servicio.consultarDepositos(rutComercio, desdeDate, hastaDate);
    }
}
