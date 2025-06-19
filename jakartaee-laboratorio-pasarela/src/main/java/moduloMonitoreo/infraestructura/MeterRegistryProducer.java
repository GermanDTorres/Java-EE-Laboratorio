package moduloMonitoreo.infraestructura;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import java.time.Duration;

@ApplicationScoped
public class MeterRegistryProducer {

    @Produces
    @Singleton
    public MeterRegistry meterRegistry() {
        InfluxConfig config = new InfluxConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public String uri() {
                return "http://localhost:8086";
            }

            @Override
            public String db() {
                return "pasarela_db";
            }

            @Override
            public String userName() {
                return "root";
            }

            @Override
            public String password() {
                return "root";
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }
        };

        return new InfluxMeterRegistry(config, Clock.SYSTEM);
    }
}
