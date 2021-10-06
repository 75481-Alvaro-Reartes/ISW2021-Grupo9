package com.example.delivereat.persistencia;

import com.example.delivereat.model.pedidos.Pedido;

/**
 * Por el momento esta clase no provee realmente
 * persistencia, pero permite acceso global a los datos.
 */
public class Datos {

    // Singleton
    private Datos() {

    }

    private static Datos mInstance;

    public static Datos getInstance() {
        if (mInstance == null) mInstance = new Datos();
        return mInstance;
    }

    // Acceso a datos global

    private Pedido mPedido;

    public void limpiar() {
        mPedido = new Pedido();
    }

    public Pedido getPedido() {
        if (mPedido == null) mPedido = new Pedido();
        return mPedido;
    }

    public void setPedido(Pedido pedido) {
        this.mPedido = pedido;
    }

}
