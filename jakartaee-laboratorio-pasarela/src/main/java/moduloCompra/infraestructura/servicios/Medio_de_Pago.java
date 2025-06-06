package moduloCompra.infraestructura.servicios;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class Medio_de_Pago {
	@WebMethod
	public int Realizar_Compra(int id_compra, int id_comercio, double monto, String fecha){
		return 0;
	}
}
