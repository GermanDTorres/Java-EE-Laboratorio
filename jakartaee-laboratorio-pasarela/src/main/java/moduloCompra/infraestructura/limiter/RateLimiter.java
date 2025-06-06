package moduloCompra.infraestructura.limiter;

public interface RateLimiter {
    boolean sePermite(String key);

	boolean allowRequest();
}
