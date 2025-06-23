package moduloComercio.infraestructura.messaging;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import moduloComercio.dominio.Reclamo;
import org.jboss.logging.Logger;
import org.json.JSONObject;import jakarta.enterprise.event.Event;
import moduloMonitoreo.interfase.evento.out.EventoReclamoClasificado;

import java.time.LocalDateTime;
import java.util.Random;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/reclamos"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
    }
)
public class ReclamoConsumer implements MessageListener {

    private static final Logger log = Logger.getLogger(ReclamoConsumer.class);

    @Inject
    Event<EventoReclamoClasificado> eventoReclamoClasificado;
    
    @PersistenceContext(unitName = "tallerjava")
    EntityManager em;

    private static final String[] RESPUESTAS = {"POSITIVO", "NEGATIVO", "NEUTRO"};
    private static final Random RANDOM = new Random();

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String json = ((TextMessage) message).getText();
                log.infof("📥 Mensaje recibido desde cola: %s", json);

                JSONObject obj = new JSONObject(json);
                String texto = obj.getString("texto");
                String comercioId = obj.getString("comercioId");

                // ⏳ Simular demora aleatoria entre 1 y 5 segundos
                int delay = RANDOM.nextInt(5) + 1;
                log.infof("⏱ Esperando %d segundos antes de procesar...", delay);
                Thread.sleep(delay * 1000L);

                // 🧠 Asignar respuesta aleatoria
                String respuesta = RESPUESTAS[RANDOM.nextInt(RESPUESTAS.length)];
                log.infof("💬 Respuesta asignada al reclamo: %s", respuesta);

                // 💾 Guardar en base de datos
                Reclamo reclamo = new Reclamo();
                reclamo.setTexto(texto);
                reclamo.setComercioId(comercioId);
                reclamo.setFecha(LocalDateTime.now());
                reclamo.setRespuesta(respuesta);

                em.persist(reclamo);
                eventoReclamoClasificado.fire(new EventoReclamoClasificado(respuesta.toLowerCase())); // positivo, negativo, neutro
                log.info("✅ Reclamo persistido con éxito.");
            }
        } catch (Exception e) {
            log.error("❌ Error al procesar mensaje JMS", e);
        }
    }
}
