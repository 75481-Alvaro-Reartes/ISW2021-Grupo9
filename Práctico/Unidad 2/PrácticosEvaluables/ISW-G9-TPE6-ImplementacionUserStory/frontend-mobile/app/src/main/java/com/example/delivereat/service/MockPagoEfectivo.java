package com.example.delivereat.service;

import android.os.Handler;

import com.example.delivereat.model.Pago;
import com.example.delivereat.ui.activities.loquesea.IObserverPago;
import com.example.delivereat.util.Constantes;

public class MockPagoEfectivo implements IClientePagoPedido {
    private IObserverPago observer;

    @Override
    public IClientePagoPedido setObserver(IObserverPago observer) {
        this.observer = observer;
        return this;
    }

    @Override
    public void validar(Pago pago) {
        // hago como que valido la tarjeta
        // VisaInterface.validateTarjetacion(pagoTarjeta); xdd
        new Handler().postDelayed(() -> {
            // wow, salió bien! :D
            observer.tarjetaValida(true);

        }, Constantes.MILIS_ESPERA_EFECTIVO);
    }
}
