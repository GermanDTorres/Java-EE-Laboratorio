package moduloCompra.infraestructura.limiter;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenBucketRateLimiter implements RateLimiter {
    private final int capacity = 10;
    private final int refillRate = 1; // tokens por segundo
    private int tokens = capacity;
    private long lastRefillTimestamp = System.nanoTime();

    @Override
    public synchronized boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTimestamp;
        long tokensToAdd = (elapsedTime / 1_000_000_000L) * refillRate;

        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, (int)(tokens + tokensToAdd));
            lastRefillTimestamp = now;
        }
    }

	@Override
    public boolean sePermite(String key) {
        // implementación aquí
        return true; // o la lógica real
    }
}
