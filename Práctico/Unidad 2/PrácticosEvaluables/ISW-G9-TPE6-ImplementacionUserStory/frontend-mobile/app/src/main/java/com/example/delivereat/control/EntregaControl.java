package com.example.delivereat.control;

import com.example.delivereat.model.pedidos.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.ui.activities.loquesea.EntregaActivity;

public class EntregaControl implements IControl {

    private final EntregaActivity mActivity;
    private final Pedido mPedido;

    public EntregaControl(EntregaActivity activity) {
        mActivity = activity;
        mPedido = Datos.getInstance().getPedido();
    }

    /**
     * Indica a la actividad que avance a la actividad de pago
     */
    public void siguiente() {
        guardarDatos();
        mActivity.setErrores(mPedido.getEntrega().getErrores());

        if (!mPedido.getEntrega().getErrores().hayError())
            mActivity.siguiente();
    }

    @Override
    public void recuperarDatos() {
        mActivity.setEntregaPronto(mPedido.getEntrega().isEntregaLoAntesPosible());
        mActivity.setFechaEntrega(mPedido.getEntrega().getEntregaProgramada());
    }

    @Override
    public void guardarDatos() {
        mPedido.getEntrega().setEntregaLoAntesPosible(mActivity.getEntregaPronto());
        mPedido.getEntrega().setEntregaProgramada(mActivity.getFechaHoraEntrega());
    }
}
