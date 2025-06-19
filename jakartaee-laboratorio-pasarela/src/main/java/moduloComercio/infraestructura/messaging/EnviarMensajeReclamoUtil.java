package moduloComercio.infraestructura.messaging;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EnviarMensajeReclamoUtil {
    private static final Logger log = Logger.getLogger(EnviarMensajeReclamoUtil.class);

    @Inject
    JMSContext context;

    @Resource(lookup = "java:jboss/exported/jms/queue/reclamos")
    Queue queue;

    public void enviarMensaje(String mensaje) {
        log.infof("Enviando mensaje JMS: %s", mensaje);
        context.createProducer().send(queue, mensaje);
    }
}
