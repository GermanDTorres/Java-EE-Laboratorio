package com.banco.cliente.mock.servicio;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface ServicioBancoCliente {
    @WebMethod
    String notificarTransferencia(
        @WebParam(name = "idTransferencia") String idTransferencia,
        @WebParam(name = "rutComercio") String rutComercio,
        @WebParam(name = "monto") double monto,
        @WebParam(name = "fecha") String fecha
    );
}
