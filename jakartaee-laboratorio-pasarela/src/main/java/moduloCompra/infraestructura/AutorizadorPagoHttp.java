package moduloCompra.infraestructura;

import com.medioPago.modelo.RespuestaPagoDTO;
import com.medioPago.modelo.SolicitudPagoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AutorizadorPagoHttp {

    private static final String URL_AUTORIZAR = "http://localhost:8082/ServicioMedioPagoMock/api/autorizarPago";

    private final ObjectMapper mapper = new ObjectMapper();

    public RespuestaPagoDTO autorizarPago(SolicitudPagoDTO solicitud) throws Exception {
        String jsonSolicitud = mapper.writeValueAsString(solicitud);

        // ✅ Log de verificación del JSON que se envía al mock
        System.out.println("📤 JSON enviado al mock:");
        System.out.println(jsonSolicitud);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_AUTORIZAR))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSolicitud))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), RespuestaPagoDTO.class);
    }

}
