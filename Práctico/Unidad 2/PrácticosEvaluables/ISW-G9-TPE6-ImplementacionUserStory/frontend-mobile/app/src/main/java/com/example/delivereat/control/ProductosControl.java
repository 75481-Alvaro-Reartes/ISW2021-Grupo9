package com.example.delivereat.control;

import com.example.delivereat.model.pedidos.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.ui.activities.loquesea.ProductosActivity;

public class ProductosControl implements IControl{

    private final ProductosActivity activity;
    private final Pedido pedido;

    public ProductosControl(ProductosActivity activity) {
        this.activity = activity;
        pedido = Datos.getInstance().getPedido();
    }

    public void clickSiguiente() {
        guardarDatos();
        
        activity.setErrores(pedido.getProducto().getErrores());

        if (!pedido.getProducto().getErrores().hayError()) activity.siguiente();
    }

    @Override
    public void recuperarDatos() {
        activity.setProducto(pedido.getProducto().getNombre());
        activity.mostrarImagen(pedido.getProducto().getImagen());
    }

    public void guardarDatos() {
        pedido.getProducto().setNombre(activity.getProducto());
        pedido.getProducto().setImagen(activity.getImagen());
    }
}
