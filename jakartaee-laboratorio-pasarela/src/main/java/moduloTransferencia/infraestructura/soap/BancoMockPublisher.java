package moduloTransferencia.infraestructura.soap;

import jakarta.xml.ws.Endpoint;
import com.banco.cliente.mock.servicio.ServicioBancoClienteImpl;

public class BancoMockPublisher {

    public static void main(String[] args) {
        String url = "http://localhost:8081/BancoClienteMock";
        Endpoint.publish(url, new ServicioBancoClienteImpl());
        System.out.println("✅ Banco Mock publicado en: " + url);
        // Mantener vivo el proceso para que siga escuchando
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}