package com.example.delivereat.control;

import com.example.delivereat.model.MetodoPago;
import com.example.delivereat.model.PagoTarjeta;
import com.example.delivereat.model.Pedido;
import com.example.delivereat.service.ClientePagoVisa;
import com.example.delivereat.service.MockPagos;
import com.example.delivereat.ui.PagosActivity;

public class PagosControl {

    private final PagosActivity activity;
    private ClientePagoVisa pagoVisa;

    public PagosControl(PagosActivity activity) {
        this.activity = activity;
        pagoVisa = new MockPagos().setObserver(this);
    }

    public void siguiente() {
        Pedido p = Pedido.getInstance();

        MetodoPago metodoPago = activity.getMetodoPago();
        String titular = activity.getTitular();
        long tarjeta = activity.getTarjeta();
        int cvc = activity.getCVC();
        int mes = activity.getMes();
        int year = activity.getYear();
        double monto = activity.getMonto();

        p.setMetodoPago(metodoPago);
        p.setTitular(titular);
        p.setTarjeta(tarjeta);
        p.setCvc(cvc);
        p.setMesVto(mes);
        p.setYearVto(year);
        p.setMonto(monto);

        activity.setErrores(p.errores);

        if (!p.errores.errorFormPagos()) {
            if (metodoPago == MetodoPago.Tarjeta) {
                activity.esperar(true);
                pagoVisa.validar(new PagoTarjeta(titular, tarjeta, cvc, mes, year));
            }
            else
                activity.siguiente();
        }
    }

    public void tarjetaValida(boolean valida) {
        activity.esperar(false);
        if (valida) activity.siguiente();
        else activity.toast("No se pudo verificar la tarjeta.");
    }
}
