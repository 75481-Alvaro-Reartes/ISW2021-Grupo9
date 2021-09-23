package com.example.delivereat.service;

import android.os.Handler;

import com.example.delivereat.model.pedidos.Pago;
import com.example.delivereat.ui.activities.loquesea.IObserverPago;
import com.example.delivereat.util.Constantes;

/**
 * Clase que imita el comportamiento de forma controlada de un pago en efectivo
 */
public class MockPagoEfectivo implements IClientePagoPedido {
    private IObserverPago mObserver;

    @Override
    public IClientePagoPedido setObserver(IObserverPago observer) {
        mObserver = observer;
        return this;
    }

    @Override
    public void validar(Pago pago) {
        // hago como que valido la tarjeta
        // VisaInterface.validateTarjetacion(pagoTarjeta); xdd
        new Handler().postDelayed(() -> {
            // wow, salió bien! :D
            mObserver.tarjetaValida(true);

        }, Constantes.MILIS_ESPERA_EFECTIVO);
    }
}
