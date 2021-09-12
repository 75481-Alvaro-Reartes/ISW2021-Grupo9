package com.example.delivereat.persistencia;

import com.example.delivereat.model.Pedido;

/**
 * Por el momento esta clase no provee realmente
 * persistencia, pero permite acceso global a los datos.
 */
public class Datos {

    // Singleton
    private Datos() {

    }

    private static Datos instance;

    public static Datos getInstance() {
        if (instance == null) instance = new Datos();
        return instance;
    }

    // Acceso a datos global

    private Pedido pedido;

    public void limpiar() {
        pedido = new Pedido();
    }

    public Pedido getPedido() {
        if (pedido == null) pedido = new Pedido();
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
