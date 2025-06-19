package com.medioPago.modelo;

public class SolicitudPagoDTO {
    private String numeroTarjeta;
    private double monto;
    private String idComercio;

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getIdComercio() { return idComercio; }
    public void setIdComercio(String idComercio) { this.idComercio = idComercio; }
}