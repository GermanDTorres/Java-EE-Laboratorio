package moduloTransferencia.infraestructura.soap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.soap.*;
import java.net.URL;

import org.w3c.dom.NodeList;

@ApplicationScoped
public class ClienteSOAPBanco {

	private static final String NAMESPACE = "http://servicio.mock.cliente.banco.com/";
    private static final String SERVICE_URL = "http://localhost:8081/BancoClienteMock";

    public boolean notificarTransferencia(String idTransferencia, String rutComercio, Double monto, String fecha) {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = soapConnectionFactory.createConnection();

            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();

            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            envelope.addNamespaceDeclaration("ns1", NAMESPACE);

            SOAPBody body = envelope.getBody();
            SOAPElement notificar = body.addChildElement("notificarTransferencia", "ns1");

            notificar.addChildElement("idTransferencia").addTextNode(idTransferencia);
            notificar.addChildElement("rutComercio").addTextNode(rutComercio);
            notificar.addChildElement("monto").addTextNode(monto.toString());
            notificar.addChildElement("fecha").addTextNode(fecha);

            message.saveChanges();

            SOAPMessage response = connection.call(message, new URL(SERVICE_URL));
            connection.close();

            SOAPBody responseBody = response.getSOAPBody();

            if (responseBody.hasFault()) {
                System.err.println("[SOAP] ❌ Fault recibido: " + responseBody.getFault().getFaultString());
                return false;
            }

            // Extraer el valor <return>
            NodeList returnList = responseBody.getElementsByTagName("return");
            if (returnList.getLength() > 0) {
                String resultado = returnList.item(0).getTextContent();
                System.out.println("[SOAP] Respuesta del banco: " + resultado);
                return "OK".equalsIgnoreCase(resultado);
            }

            // Si no hay return, consideramos fallo
            System.err.println("[SOAP] ❌ No se recibió valor <return> en la respuesta");
            return false;

        } catch (Exception e) {
            System.err.println("[SOAP] ❌ Error al invocar servicio del banco: " + e.getMessage());
            return false;
        }
    }

}
