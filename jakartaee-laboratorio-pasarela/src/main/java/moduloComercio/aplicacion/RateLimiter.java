package moduloComercio.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class RateLimiter {

    private static final Duration INTERVALO = Duration.ofSeconds(5); // 1 request cada 5 segundos

    private final Map<String, Instant> ultimoAcceso = new ConcurrentHashMap<>();

    public boolean sePermite(String rutComercio) {
        Instant ahora = Instant.now();
        Instant anterior = ultimoAcceso.get(rutComercio);

        if (anterior == null || Duration.between(anterior, ahora).compareTo(INTERVALO) > 0) {
            ultimoAcceso.put(rutComercio, ahora);
            return true;
        }
        return false;
    }
}
