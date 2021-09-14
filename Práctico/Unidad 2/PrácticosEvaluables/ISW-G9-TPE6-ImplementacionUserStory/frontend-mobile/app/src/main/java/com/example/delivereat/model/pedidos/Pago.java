package com.example.delivereat.model.pedidos;

import androidx.annotation.NonNull;

import com.example.delivereat.model.otros.ErrorManager;
import com.example.delivereat.util.Constantes;

import java.util.Calendar;

public class Pago {
    private double monto;
    private String titular;
    private long tarjeta;
    private int cvc;
    private int mesVto = -1;
    private int yearVto;
    private int montoPedido;

    private MetodoPago mMetodoPago = MetodoPago.Tarjeta;

    public Pago() {
    }

    @NonNull
    @Override
    public String toString() {
        return  "Costo:    $ " + montoPedido + "\n" +
                (
                        esTarjeta()
                                ? "Tarjeta Visa **** " + String.valueOf(tarjeta).substring(12)
                                : "Efectivo:  $" + (int) monto
                );
    }

    /**
     * Retorna el método pago del pedido
     * @return el método de pago
     */
    public MetodoPago getMetodoPago() {
        return mMetodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        mMetodoPago = metodoPago;
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

    public int getMontoPedido() {
        return montoPedido;
    }

    public void setMontoPedido(int montoPedido) {
        this.montoPedido = montoPedido;
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public boolean esTarjeta() {
        return mMetodoPago == MetodoPago.Tarjeta;
    }

    public void calcularMonto(int distancia) {
        montoPedido = (int) (Constantes.MONTO_POR_500_METROS *  Math.ceil(distancia/ 500d));
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
            return !esTarjeta() && monto < montoPedido;
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
