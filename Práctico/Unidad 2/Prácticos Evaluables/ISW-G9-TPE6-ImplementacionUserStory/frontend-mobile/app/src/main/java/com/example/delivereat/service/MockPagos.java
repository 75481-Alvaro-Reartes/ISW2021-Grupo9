package com.example.delivereat.service;

import android.os.Handler;

import com.example.delivereat.control.PagosControl;
import com.example.delivereat.model.PagoTarjeta;

public class MockPagos  implements ClientePagoVisa{

    private static final int MILIS_ESPERA = 5000;

    private PagosControl control;

    @Override
    public ClientePagoVisa setObserver(PagosControl control) {
        this.control = control;
        return this;
    }

    @Override
    public void validar(PagoTarjeta pagoTarjeta) {
        // hago como que valido la tarjeta
        // VisaInterface.validateTarjetacion(pagoTarjeta); xdd
        new Handler().postDelayed(() -> {
            // wow, salió bien! :D
            control.tarjetaValida(true);

        }, MILIS_ESPERA);
    }
}