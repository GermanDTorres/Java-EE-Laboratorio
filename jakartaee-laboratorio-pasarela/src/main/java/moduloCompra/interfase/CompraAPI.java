package moduloCompra.interfase;

import jakarta.inject.Inject;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import moduloCompra.aplicacion.ServicioCompra;
import moduloCompra.aplicacion.ServicioResumenVentas;
import moduloCompra.dominio.Compra;
import moduloComercio.aplicacion.ServicioComercio;
import moduloMonitoreo.aplicacion.ServicioMonitoreo;
import moduloMonitoreo.aplicacion.impl.ServicioMonitoreoImpl;
//import moduloMonitoreo.aplicacion.ServicioMonitoreoQualifier;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/compra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompraAPI {

	/*
    private static final RepositorioCompraMemoria repo = new RepositorioCompraMemoria();
    private static final RepositorioComercioMemoria repoComercio = new RepositorioComercioMemoria();
    private static final ServicioMonitoreo servicioMonitoreo = new ServicioMonitoreoImpl();
 */   
 


    @Inject
    private ServicioCompra servicio;

    @Inject
    private ServicioResumenVentas servicioResumen;

    @Inject
    private ServicioComercio servicioComercio;

    @Inject
    private ServicioMonitoreo servicioMonitoreo;

    @POST
    @Path("/procesar")
    public Response procesarPago(Compra compra) {
        try {
            compra.setFecha(new Date());
            Compra resultado = servicio.procesarCompra(compra);
            if (resultado.getEstado() == moduloCompra.dominio.EstadoCompra.APROBADA) {
                return Response.ok(Map.of("mensaje", "Pago aprobado y procesado correctamente")).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Pago rechazado: monto inválido o saldo insuficiente"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Error al procesar el pago: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/listar")
    public List<Compra> listarCompras() {
        return servicio.obtenerCompras();
    }

    @GET
    @Path("/resumenDiario/{idComercio}")
    public Response resumenDiario(
            @PathParam("idComercio") String idComercio,
            @HeaderParam("Authorization") String authHeader) {

        if (!autenticar(idComercio, authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", "Acceso no autorizado")).build();
        }

        List<Compra> resumen = servicioResumen.resumenVentasDiarias(idComercio);
        return Response.ok(resumen).build();
    }

    @GET
    @Path("/resumenPeriodo")
    public Response resumenPorPeriodo(
            @QueryParam("idComercio") String idComercio,
            @QueryParam("desde") String desde,
            @QueryParam("hasta") String hasta,
            @HeaderParam("Authorization") String authHeader) {

        if (!autenticar(idComercio, authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", "Acceso no autorizado")).build();
        }

        if (desde == null || hasta == null || desde.isBlank() || hasta.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Parámetros 'desde' y 'hasta' son obligatorios"))
                    .build();
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fDesde = sdf.parse(desde);
            Date fHasta = sdf.parse(hasta);
            List<Compra> resumen = servicioResumen.resumenVentasPorPeriodo(idComercio, fDesde, fHasta);
            return Response.ok(resumen).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Formato de fecha incorrecto. Use yyyy-MM-dd"))
                    .build();
        }
    }

    @GET
    @Path("/montoActual/{idComercio}")
    public Response montoActual(
            @PathParam("idComercio") String idComercio,
            @HeaderParam("Authorization") String authHeader) {

        if (!autenticar(idComercio, authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", "Acceso no autorizado")).build();
        }

        double monto = servicioResumen.montoActualVendido(idComercio);
        return Response.ok(Map.of("montoActual", monto)).build();
    }

    private boolean autenticar(String idComercio, String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return false;
        }

        try {
            String base64Credentials = authHeader.substring("Basic ".length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);
            if (values.length != 2) return false;
            String username = values[0];
            String password = values[1];

            return username.equals(idComercio) && servicioComercio.autenticar(idComercio, password);
        } catch (Exception e) {
            return false;
        }
    }
}
