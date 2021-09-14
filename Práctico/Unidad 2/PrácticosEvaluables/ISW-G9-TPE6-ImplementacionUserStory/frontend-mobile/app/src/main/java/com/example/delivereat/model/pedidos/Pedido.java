package com.example.delivereat.model.pedidos;

import com.example.delivereat.model.otros.ErrorManager;

public class Pedido {

    public Pedido() {
        mProducto = new Producto();
        mEntrega = new Entrega();
        mUbicacion = new Ubicacion();
        mPago = new Pago();

    }

    // Atributos
    private final Producto mProducto;
    private final Pago mPago;
    private final Entrega mEntrega;
    private final Ubicacion mUbicacion;

    // Getters
    public Producto getProducto() {
        return mProducto;
    }

    public Pago getPago() {
        return mPago;
    }

    public Entrega getEntrega() {
        return mEntrega;
    }

    public Ubicacion getUbicacion() {
        return mUbicacion;
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return mProducto.getErrores().hayError() || mEntrega.getErrores().hayError()
                    || mUbicacion.getErrores().hayError() || mPago.getErrores().hayError();
        }
    }
}
