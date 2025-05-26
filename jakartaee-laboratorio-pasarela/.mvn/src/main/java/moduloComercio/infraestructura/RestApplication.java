package moduloComercio.infraestructura;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // vacía, solo para activar JAX-RS con /api
}