package com.example.delivereat.service;

import com.example.delivereat.control.PagosControl;
import com.example.delivereat.model.PagoTarjeta;

public interface ClientePagoVisa {

    ClientePagoVisa setObserver(PagosControl control);
    void validar(PagoTarjeta pagoTarjeta);
}
