package com.example.delivereat.model;

public class PagoTarjeta {
    private String titular;
    private long tarjeta;
    private int cvc;
    private int mesVto;
    private int yearVto;

    public PagoTarjeta(String titular, long tarjeta, int cvc, int mesVto, int yearVto) {
        this.titular = titular;
        this.tarjeta = tarjeta;
        this.cvc = cvc;
        this.mesVto = mesVto;
        this.yearVto = yearVto;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public long getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(long tarjeta) {
        this.tarjeta = tarjeta;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public int getMesVto() {
        return mesVto;
    }

    public void setMesVto(int mesVto) {
        this.mesVto = mesVto;
    }

    public int getYearVto() {
        return yearVto;
    }

    public void setYearVto(int yearVto) {
        this.yearVto = yearVto;
    }
}
