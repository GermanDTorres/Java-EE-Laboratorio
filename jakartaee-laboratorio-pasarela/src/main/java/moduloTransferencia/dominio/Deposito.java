package moduloTransferencia.dominio;

import java.time.LocalDateTime;

public class Deposito {
    private String id;
    private String rutComercio;
    private String cuentaBanco;
    private double montoBruto;
    private double comisionDescontada;
    private double montoNeto;
    private LocalDateTime fechaDeposito;

    public Deposito(String id, String rutComercio, String cuentaBanco, double montoBruto, double comisionDescontada, LocalDateTime fechaDeposito) {
        this.id = id;
        this.rutComercio = rutComercio;
        this.cuentaBanco = cuentaBanco;
        this.montoBruto = montoBruto;
        this.comisionDescontada = comisionDescontada;
        this.montoNeto = montoBruto - comisionDescontada;
        this.fechaDeposito = fechaDeposito;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public String getRutComercio() {
        return rutComercio;
    }

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public double getMontoBruto() {
        return montoBruto;
    }

    public double getComisionDescontada() {
        return comisionDescontada;
    }

    public double getMontoNeto() {
        return montoNeto;
    }

    public LocalDateTime getFechaDeposito() {
        return fechaDeposito;
    }

    @Override
    public String toString() {
        return "Deposito{" +
                "id='" + id + '\'' +
                ", rutComercio='" + rutComercio + '\'' +
                ", cuentaBanco='" + cuentaBanco + '\'' +
                ", montoBruto=" + montoBruto +
                ", comisionDescontada=" + comisionDescontada +
                ", montoNeto=" + montoNeto +
                ", fechaDeposito=" + fechaDeposito +
                '}';
    }
}
