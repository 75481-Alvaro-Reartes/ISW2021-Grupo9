package com.example.delivereat.model;

import com.example.delivereat.util.Constantes;

import java.util.Calendar;

public class Pago {
    private double monto;

    private String titular;
    private long tarjeta;
    private int cvc;
    private int mesVto = -1;
    private int yearVto;

    private MetodoPago metodoPago = MetodoPago.Tarjeta;

    public Pago() {
    }

    @Override
    public String toString() {
        return  esTarjeta()
                ? "Tarjeta Visa **** " + String.valueOf(tarjeta).substring(12)
                : "Efectivo: $" + monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
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

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public boolean esTarjeta() {
        return metodoPago == MetodoPago.Tarjeta;
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return eLargoTarjeta() || eCVC() || eTitular() || eMes() || eYear() || eMonto() || eTarjetaNoVisa() || eTarjetaVencida();
        }

        public boolean eLargoTarjeta() {
            return esTarjeta() && String.valueOf(tarjeta).length() != 16;
        }

        public boolean eCVC() {
            return esTarjeta() && String.valueOf(cvc).length() != 3;
        }

        public boolean eTitular() {
            return esTarjeta() && titular.length() < Constantes.MIN_CARACTERES;
        }

        public boolean eMes() {
            return esTarjeta() && mesVto == -1;
        }

        public boolean eYear() {
            return esTarjeta() && yearVto == 0;
        }

        public boolean eMonto() {
            return !esTarjeta() && monto < Constantes.MIN_MONTO;
        }

        public boolean eTarjetaNoVisa() {
            return esTarjeta() && !eLargoTarjeta() && String.valueOf(tarjeta).charAt(0) != '4';
        }

        public boolean eTarjetaVencida() {
            int mesActual, yearActual;
            Calendar calendar = Calendar.getInstance();
            mesActual = calendar.get(Calendar.MONTH);
            yearActual = calendar.get(Calendar.YEAR);
            return esTarjeta() && !eMes() && !eYear() && yearActual == yearVto && mesActual > mesVto;
        }
    }
}
