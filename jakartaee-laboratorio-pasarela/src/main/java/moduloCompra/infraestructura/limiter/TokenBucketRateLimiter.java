package moduloCompra.infraestructura.limiter;

import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
public class TokenBucketRateLimiter implements RateLimiter {

    private BigDecimal tokens;
    private final BigDecimal capacity = new BigDecimal("50");
    private final BigDecimal refillRate = new BigDecimal("10");
    private long lastRefillTimestamp;

    public TokenBucketRateLimiter() {
        this.tokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    @SuppressWarnings("deprecation")
	@Override
    public synchronized boolean allowRequest() {
        long now = System.nanoTime();
        BigDecimal elapsedSeconds = new BigDecimal(now - lastRefillTimestamp)
                .divide(new BigDecimal(1_000_000_000), BigDecimal.ROUND_HALF_UP);

        BigDecimal refillTokens = elapsedSeconds.multiply(refillRate);
        if (refillTokens.compareTo(BigDecimal.ZERO) > 0) {
            tokens = tokens.add(refillTokens);
            if (tokens.compareTo(capacity) > 0) {
                tokens = capacity;
            }
            lastRefillTimestamp = now;
            System.out.printf("[RateLimiter] ♻️ Tokens recargados: %.9f (+%.9f)%n", tokens, refillTokens);
        }

        if (tokens.compareTo(BigDecimal.ONE) >= 0) {
            tokens = tokens.subtract(BigDecimal.ONE);
            System.out.printf("[RateLimiter] ✅ Token consumido, tokens restantes: %.9f%n", tokens);
            return true;
        } else {
            System.out.printf("[RateLimiter] ❌ No quedan tokens, solicitud rechazada%n");
            return false;
        }
    }

    @Override
    public boolean sePermite(String key) {
        // Ignorar key, o implementar lógica si quieres diferenciación por key
        return allowRequest();
    }
}
