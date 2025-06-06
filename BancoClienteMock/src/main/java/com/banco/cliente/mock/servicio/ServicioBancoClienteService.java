package com.banco.cliente.mock.servicio;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;

import javax.xml.namespace.QName;
import java.net.URL;

@WebServiceClient(
    name = "ServicioBancoClienteService",
    targetNamespace = "http://servicio.mock.cliente.banco.com/",
    wsdlLocation = "http://localhost:8081/BancoClienteMock?wsdl"
)
public class ServicioBancoClienteService extends Service {

    private static final QName SERVICE_NAME = new QName(
        "http://servicio.mock.cliente.banco.com/",
        "ServicioBancoClienteService"
    );

    public ServicioBancoClienteService() {
        super(getWsdlLocation(), SERVICE_NAME);
    }

    private static URL getWsdlLocation() {
        try {
            return new URL("http://localhost:8081/BancoClienteMock?wsdl");
        } catch (Exception e) {
            throw new RuntimeException("❌ Error obteniendo el WSDL", e);
        }
    }

    @WebEndpoint(name = "ServicioBancoClientePort")
    public ServicioBancoCliente getServicioBancoClientePort() {
        return super.getPort(
            new QName("http://servicio.mock.cliente.banco.com/", "ServicioBancoClientePort"),
            ServicioBancoCliente.class
        );
    }
}
