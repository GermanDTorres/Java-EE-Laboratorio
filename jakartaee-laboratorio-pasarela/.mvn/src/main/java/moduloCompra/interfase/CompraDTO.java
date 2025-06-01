package moduloCompra.interfase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CompraDTO {
	 @NotBlank(message = "El ID de la compra no puede estar vacío")
	    public String idCompra;

	    @NotBlank(message = "El ID del comercio no puede estar vacío")
	    public String idComercio;

	    @Positive(message = "El monto debe ser mayor a cero")
	    public double monto;}
