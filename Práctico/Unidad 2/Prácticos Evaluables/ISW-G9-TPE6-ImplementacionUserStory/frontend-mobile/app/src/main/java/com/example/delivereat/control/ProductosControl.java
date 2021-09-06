package com.example.delivereat.control;

import com.example.delivereat.model.Pedido;
import com.example.delivereat.ui.ProductosActivity;

public class ProductosControl {

    private ProductosActivity activity;

    public ProductosControl(ProductosActivity activity) {
        this.activity = activity;
    }

    public void clickSiguiente() {
        
        Pedido pedido = Pedido.getInstance();
        
        pedido.setProducto(activity.getProducto());
        pedido.setImagenes(activity.getImagenes());
        pedido.setRecibirPronto(activity.getRecibirPronto());
        pedido.setFechaHoraRecepcion(activity.getFechaHoraRecepcion());
        
        activity.setErrores(pedido.errores);

        if (!pedido.errores.errorFormProducto()) activity.siguiente();
    }
}
