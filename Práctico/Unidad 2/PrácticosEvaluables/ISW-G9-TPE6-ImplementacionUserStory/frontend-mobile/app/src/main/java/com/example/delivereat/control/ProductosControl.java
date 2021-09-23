package com.example.delivereat.control;

import com.example.delivereat.model.pedidos.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.ui.activities.loquesea.ProductosActivity;

public class ProductosControl implements IControl{

    private final ProductosActivity mActivity;
    private final Pedido mPedido;

    public ProductosControl(ProductosActivity activity) {
        mActivity = activity;
        mPedido = Datos.getInstance().getPedido();
    }

    public void clickSiguiente() {
        guardarDatos();
        
        mActivity.setErrores(mPedido.getProducto().getErrores());

        if (!mPedido.getProducto().getErrores().hayError()) mActivity.siguiente();
    }

    @Override
    public void recuperarDatos() {
        mActivity.setProducto(mPedido.getProducto().getNombre());
        mActivity.mostrarImagen(mPedido.getProducto().getImagen());
    }

    /**
     * guarda los datos de la actividada en la clase de persistencia
     */
    public void guardarDatos() {
        mPedido.getProducto().setNombre(mActivity.getProducto());
        mPedido.getProducto().setImagen(mActivity.getImagen());
    }
}
