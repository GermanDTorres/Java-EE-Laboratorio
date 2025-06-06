package moduloTransferencia.infraestructura.servicios;

import java.time.LocalDateTime;


import javax.jws.WebParam;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface BancoAPI {
	@WebMethod
	public int Realizar_Transferencia(@WebParam(name="cuenta_banco") String cuenta_banco, @WebParam(name="monto") double monto, @WebParam(name="fecha") String fecha);
}
