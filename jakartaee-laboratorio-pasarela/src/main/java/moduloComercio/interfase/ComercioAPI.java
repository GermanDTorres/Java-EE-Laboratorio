package moduloComercio.interfase;

import moduloComercio.aplicacion.ServicioComercio;
import moduloComercio.aplicacion.impl.ServicioComercioImpl;
import moduloComercio.dominio.Comercio;
import moduloComercio.infraestructura.persistencia.RepositorioComercioMemoria;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/comercios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComercioAPI {
    
    // Instancia estática y compartida del repositorio
    private static final RepositorioComercioMemoria repositorio = new RepositorioComercioMemoria();
    private final ServicioComercio servicio = new ServicioComercioImpl(repositorio);

    @POST
    public Response registrar(ComercioDTO dto) {
        Comercio comercio = new Comercio(dto.id, dto.nombre, dto.categoria);
        servicio.registrarComercio(comercio);
        return Response.status(Response.Status.CREATED).entity(comercio).build();
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
}

