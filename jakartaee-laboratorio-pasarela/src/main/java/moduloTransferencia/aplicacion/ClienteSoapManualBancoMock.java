package moduloTransferencia.aplicacion;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteSoapManualBancoMock {

    public String notificarTransferencia(
        String idTransferencia, String rutCliente, double monto, String fecha) throws Exception {

        String xmlSoap = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                              "xmlns:ser=\"http://servicio.mock.cliente.banco.com/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                    "<ser:notificarTransferencia>" +
                        "<idTransferencia>" + idTransferencia + "</idTransferencia>" +
                        "<rutCliente>" + rutCliente + "</rutCliente>" +
                        "<monto>" + monto + "</monto>" +
                        "<fecha>" + fecha + "</fecha>" +
                    "</ser:notificarTransferencia>" +
                "</soapenv:Body>" +
            "</soapenv:Envelope>";

        URL url = new URL("http://localhost:8081/BancoClienteMock");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(xmlSoap.getBytes("UTF-8"));
        }

        // Leer la respuesta
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    public static void main(String[] args) throws Exception {
        ClienteSoapManualBancoMock cliente = new ClienteSoapManualBancoMock();
        String respuesta = cliente.notificarTransferencia("TX123", "11111111-1", 5000.0, "2025-06-01");
        System.out.println("Respuesta SOAP recibida:\n" + respuesta);
    }
}
