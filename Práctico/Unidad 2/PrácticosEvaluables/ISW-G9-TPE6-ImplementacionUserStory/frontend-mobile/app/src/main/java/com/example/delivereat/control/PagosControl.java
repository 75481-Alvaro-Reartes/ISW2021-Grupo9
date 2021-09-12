package com.example.delivereat.control;

import com.example.delivereat.model.Pago;
import com.example.delivereat.model.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.ui.activities.loquesea.PagosActivity;

public class PagosControl implements IControl {

    private final PagosActivity activity;
    private final Pedido pedido;

    public PagosControl(PagosActivity activity) {
        this.activity = activity;
        pedido = Datos.getInstance().getPedido();
    }

    public void siguiente() {
        guardarDatos();

        activity.setErrores(pedido.getPago().getErrores());

        if (!pedido.getPago().getErrores().hayError()) {
            activity.siguiente();
        }
    }

    @Override
    public void recuperarDatos() {
        Pago pago = pedido.getPago();

        activity.setTitular(pago.getTitular());
        activity.setTarjeta(pago.getTarjeta());
        activity.setCVC(pago.getCvc());
        activity.setMes(pago.getMesVto());
        activity.setYear(pago.getYearVto());
        activity.setMonto(pago.getMonto());
        activity.setMetodoPago(pago.getMetodoPago());
    }

    @Override
    public void guardarDatos() {
        Pago pago = pedido.getPago();

        pago.setTitular(activity.getTitular());
        pago.setTarjeta(activity.getTarjeta());
        pago.setCvc(activity.getCVC());
        pago.setMesVto(activity.getMes());
        pago.setYearVto(activity.getYear());
        pago.setMonto(activity.getMonto());
        pago.setMetodoPago(activity.getMetodoPago());
    }
}
