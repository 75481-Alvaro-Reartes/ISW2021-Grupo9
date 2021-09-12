package com.example.delivereat.service;

import com.example.delivereat.model.Pago;
import com.example.delivereat.ui.activities.loquesea.IObserverPago;

public interface IClientePagoPedido {

    IClientePagoPedido setObserver(IObserverPago observer);
    void validar(Pago pago);
}
