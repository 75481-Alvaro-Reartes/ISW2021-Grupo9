package com.example.delivereat.control;

import com.example.delivereat.model.pedidos.Pago;
import com.example.delivereat.model.pedidos.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.ui.activities.loquesea.PagosActivity;
import com.example.delivereat.util.Constantes;

public class PagosControl implements IControl {

    private final PagosActivity mActivity;
    private final Pedido mPedido;

    public PagosControl(PagosActivity activity) {
        this.mActivity = activity;
        mPedido = Datos.getInstance().getPedido();
    }
    /**
     * Indica a la actividad que avance a la actividad de pago
     */
    public void siguiente() {
        guardarDatos();

        mActivity.setErrores(mPedido.getPago().getErrores());

        if (!mPedido.getPago().getErrores().hayError()) {
            mActivity.siguiente();
        }
    }

    @Override
    public void recuperarDatos() {
        Pago pago = mPedido.getPago();

        mActivity.setTitular(pago.getTitular());
        mActivity.setTarjeta(pago.getTarjeta());
        mActivity.setCVC(pago.getCvc());
        mActivity.setMes(pago.getMesVto());
        mActivity.setYear(pago.getYearVto());
        mActivity.setMonto(pago.getMonto());
        mActivity.setMetodoPago(pago.getMetodoPago());
    }

    @Override
    public void guardarDatos() {
        Pago pago = mPedido.getPago();

        pago.setTitular(mActivity.getTitular());
        pago.setTarjeta(mActivity.getTarjeta());
        pago.setCvc(mActivity.getCVC());
        pago.setMesVto(mActivity.getMes());
        pago.setYearVto(mActivity.getYear());
        pago.setMonto(mActivity.getMonto());
        pago.setMetodoPago(mActivity.getMetodoPago());
    }

    /**
     * Devuele el monto del pago en formato string
     * @return Monto del pedido
     */
    public String getMonto() {
        return "$ " + mPedido.getPago().getMontoPedido();
    }
}
