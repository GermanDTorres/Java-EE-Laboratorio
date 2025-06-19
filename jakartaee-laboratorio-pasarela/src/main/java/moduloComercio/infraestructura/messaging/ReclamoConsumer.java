package moduloComercio.infraestructura.messaging;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.JMSException;
import org.jboss.logging.Logger;
import moduloComercio.aplicacion.ServicioComercio;
import io.micrometer.core.instrument.MeterRegistry;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/reclamos"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
    }
)
public class ReclamoConsumer implements MessageListener {

    private static final Logger log = Logger.getLogger(ReclamoConsumer.class);

    @Inject
    ServicioComercio servicioComercio;

    @Inject
    MeterRegistry meterRegistry;

    @Override
    public void onMessage(Message message) {
        try {
            String body = message.getBody(String.class);
            log.infof("Reclamo recibido: %s", body);

            ReclamoRealizadoMessage reclamo = ReclamoRealizadoMessage.fromJson(body);

            servicioComercio.procesarReclamo(reclamo.texto(), reclamo.comercioId());

            meterRegistry.counter("reclamos_total").increment();

        } catch (JMSException e) {
            log.errorf("Error al procesar reclamo JMS: %s", e.getMessage());
        }
    }
}
