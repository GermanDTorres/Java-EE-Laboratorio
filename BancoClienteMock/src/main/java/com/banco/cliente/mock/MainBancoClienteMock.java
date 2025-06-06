package com.banco.cliente.mock;

import com.banco.cliente.mock.servicio.ServicioBancoClienteImpl;
import jakarta.xml.ws.Endpoint;

public class MainBancoClienteMock {
    public static void main(String[] args) {
        String url = "http://localhost:8081/BancoClienteMock";
        Endpoint.publish(url, new ServicioBancoClienteImpl());
        System.out.println("✅ Servicio SOAP BancoClienteMock iniciado:");
        System.out.println("👉 URL: " + url + "?wsdl");
    }
}

