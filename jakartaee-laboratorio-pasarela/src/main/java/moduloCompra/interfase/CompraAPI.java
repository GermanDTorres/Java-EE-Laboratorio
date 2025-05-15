package moduloCompra.interfase;


import moduloCompra.aplicacion.ServicioCompra;
import moduloCompra.aplicacion.impl.ServicioCompraImpl;
import moduloCompra.dominio.Compra;
import moduloCompra.infraestructura.persistencia.RepositorioCompraMemoria;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/compras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompraAPI {
    private final ServicioCompra servicio = new ServicioCompraImpl(new RepositorioCompraMemoria());

    @POST
    public void registrarCompra(CompraDTO dto) {
        Compra compra = new Compra(dto.id, dto.idComercio, dto.monto);
        servicio.procesarCompra(compra);
    }

    @GET
    public List<Compra> obtenerCompras() {
        return servicio.obtenerCompras();
    }

    @GET
    @Path("/{id}")
    public Compra obtenerCompra(@PathParam("id") String id) {
        return servicio.obtenerCompra(id);
    }
}
