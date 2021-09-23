package com.example.delivereat.model.pedidos;

import androidx.annotation.NonNull;

import com.example.delivereat.model.otros.ErrorManager;
import com.example.delivereat.util.Constantes;

import java.util.Calendar;

/**
 * Clase del modelo sobre el pago.
 */
public class Pago {
    private double mMonto;
    private String mTitular;
    private long mTarjeta;
    private int mCvc;
    private int mMesVto = -1;
    private int mYearVto;
    private int mMontoPedido;

    private MetodoPago mMetodoPago = MetodoPago.Tarjeta;

    public Pago() {
    }

    @NonNull
    @Override
    public String toString() {
        return  "Costo:    $ " + mMontoPedido + "\n" +
                (
                        esTarjeta()
                                ? "Tarjeta Visa **** " + String.valueOf(mTarjeta).substring(12)
                                : "Efectivo:  $" + (int) mMonto
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
        return mMonto;
    }

    public void setMonto(double monto) {
        mMonto = monto;
    }

    public String getTitular() {
        return mTitular;
    }

    public void setTitular(String titular) {
        mTitular = titular;
    }

    public long getTarjeta() {
        return mTarjeta;
    }

    public void setTarjeta(long tarjeta) {
        mTarjeta = tarjeta;
    }

    public int getCvc() {
        return mCvc;
    }

    public void setCvc(int cvc) {
        mCvc = cvc;
    }

    public int getMesVto() {
        return mMesVto;
    }

    public void setMesVto(int mesVto) {
        mMesVto = mesVto;
    }

    public int getYearVto() {
        return mYearVto;
    }

    public void setYearVto(int yearVto) {
        mYearVto = yearVto;
    }

    public int getMontoPedido() {
        return mMontoPedido;
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public boolean esTarjeta() {
        return mMetodoPago == MetodoPago.Tarjeta;
    }

    public void calcularMonto(int distancia) {
        mMontoPedido = (int) (Constantes.MONTO_POR_500_METROS *  Math.ceil(distancia/ 500d));
        // En caso de ingresar la ubicación del origen o destino como texto y no a través de Maps,
        // distancia será igual a 0, provocando que monto pedido lo sea a su vez. Por el momento
        // se decide mockear el monto en $350.
        if (mMontoPedido == 0) mMontoPedido = 350;
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return eLargoTarjeta() || eCVC() || eTitular() || eMes() || eYear()
                    || eMonto() || eTarjetaNoVisa() || eTarjetaVencida();
        }

        public boolean eLargoTarjeta() {
            return esTarjeta() && String.valueOf(mTarjeta).length() != 16;
        }

        public boolean eCVC() {
            return esTarjeta() && String.valueOf(mCvc).length() != 3;
        }

        public boolean eTitular() {
            return esTarjeta() && mTitular.length() < Constantes.MIN_CARACTERES;
        }

        public boolean eMes() {
            return esTarjeta() && mMesVto == -1;
        }

        public boolean eYear() {
            return esTarjeta() && mYearVto == 0;
        }

        public boolean eMonto() {
            return !esTarjeta() && mMonto < mMontoPedido;
        }

        public boolean eTarjetaNoVisa() {
            return esTarjeta() && !eLargoTarjeta() && String.valueOf(mTarjeta).charAt(0) != '4';
        }

        public boolean eTarjetaVencida() {
            int mesActual, yearActual;
            Calendar calendar = Calendar.getInstance();
            mesActual = calendar.get(Calendar.MONTH);
            yearActual = calendar.get(Calendar.YEAR);
            return esTarjeta()
                    && !eMes() && !eYear()
                    && yearActual == mYearVto
                    && mesActual > mMesVto;
        }
    }
}
