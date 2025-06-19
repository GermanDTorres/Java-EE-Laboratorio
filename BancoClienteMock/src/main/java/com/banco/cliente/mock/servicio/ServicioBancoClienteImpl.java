package com.banco.cliente.mock.servicio;

import jakarta.jws.WebService;

@WebService(
    serviceName = "ServicioBancoClienteService",
    portName = "ServicioBancoClientePort",
    endpointInterface = "com.banco.cliente.mock.servicio.ServicioBancoCliente",
    targetNamespace = "http://servicio.mock.cliente.banco.com/"
)
public class ServicioBancoClienteImpl implements ServicioBancoCliente {

    @Override
    public String notificarTransferencia(String idTransferencia, String rutComercio, double monto, String fecha) {
    	System.out.println("📥 [BancoClienteMock] Transferencia recibida:");
    	System.out.println("   🆔 ID Transferencia: " + idTransferencia);
    	System.out.println("   🧾 RUT Comercio    : " + rutComercio);
    	System.out.println("   💰 Monto           : $" + monto);
    	System.out.println("   📅 Fecha           : " + fecha);
        return "OK";
    }
}
