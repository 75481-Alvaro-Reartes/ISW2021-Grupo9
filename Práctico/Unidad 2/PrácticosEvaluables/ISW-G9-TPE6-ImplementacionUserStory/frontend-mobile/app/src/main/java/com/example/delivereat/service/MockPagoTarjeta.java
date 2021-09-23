package com.example.delivereat.service;

import android.os.Handler;

import com.example.delivereat.model.pedidos.Pago;
import com.example.delivereat.ui.activities.loquesea.IObserverPago;
import com.example.delivereat.util.Constantes;

/**
 * Clase que imita el comportamiento de pago con tarjeta y conexiones a servicios web de VISA
 */
public class MockPagoTarjeta implements IClientePagoPedido {

    private IObserverPago mObserver;

    @Override
    public IClientePagoPedido setObserver(IObserverPago observer) {
        mObserver = observer;
        return this;
    }

    @Override
    public void validar(Pago pagoTarjeta) {
        // hago como que valido la tarjeta
        // VisaInterface.validateTarjetacion(pagoTarjeta); xdd
        new Handler().postDelayed(() -> {
            // wow, salió bien! :D
            mObserver.tarjetaValida(true);

        }, Constantes.MILIS_ESPERA_TARJETA);
    }
}