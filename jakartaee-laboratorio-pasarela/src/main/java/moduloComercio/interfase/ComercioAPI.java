package moduloComercio.interfase;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

import moduloComercio.aplicacion.ServicioComercio;
import moduloComercio.dominio.Comercio;
import moduloComercio.util.HashUtil;

import java.util.List;

@Path("/comercios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComercioAPI {

    @Inject
    private ServicioComercio servicio;

    public static class ComercioDTO {
        public String rut;
        public String nombre;
        public String password;

        public boolean esValido() {
            return rut != null && !rut.isBlank()
                    && nombre != null && !nombre.isBlank()
                    && password != null && !password.isBlank();
        }
    }

    public static class ReclamoDTO {
        public String texto;

        public boolean esValido() {
            return texto != null && !texto.isBlank();
        }
    }

    @POST
    public Response registrar(ComercioDTO dto) {
        if (dto == null || !dto.esValido()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Datos incompletos").build();
        }

        Comercio comercio = new Comercio();
        comercio.setRut(dto.rut);
        comercio.setNombre(dto.nombre);
        comercio.setPasswordHash(HashUtil.sha256(dto.password));

        servicio.registrarComercio(comercio);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Comercio> listar() {
        return servicio.obtenerTodos();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") String id) {
        Comercio comercio = servicio.obtenerComercio(id);
        if (comercio == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(comercio).build();
    }

    @PUT
    @Path("/{id}/contrasena")
    public Response cambiarContrasena(@PathParam("id") String id, @QueryParam("nueva") String nueva) {
        servicio.cambioContrasena(id, nueva);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/pos/{posId}")
    @Transactional
    public Response altaPOS(@PathParam("id") String comercioId, @PathParam("posId") String posId) {
        servicio.altaPOS(comercioId, posId);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/pos/{posId}/estado")
    public Response cambiarEstadoPOS(@PathParam("id") String comercioId, @PathParam("posId") String posId, @QueryParam("activo") boolean activo) {
        servicio.cambiarEstadoPOS(comercioId, posId, activo);
        return Response.ok().build();
    }

    // NUEVO ENDPOINT para crear un reclamo
    @POST
    @Path("/{id}/reclamo")
    public Response crearReclamo(@PathParam("id") String comercioId, ReclamoDTO dto) {
        if (dto == null || !dto.esValido()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Texto de reclamo inválido").build();
        }

        servicio.realizarReclamo(dto.texto, comercioId);

        return Response.status(Response.Status.CREATED).build();
    }

}
