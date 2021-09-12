package com.example.delivereat.model;

public class Pedido {

    public Pedido() {
        producto = new Producto();
        entrega = new Entrega();
        ubicacion = new Ubicacion();
        pago = new Pago();

    }

    // Atributos
    private final Producto producto;
    private final Pago pago;
    private final Entrega entrega;
    private final Ubicacion ubicacion;

    // Getters
    public Producto getProducto() {
        return producto;
    }

    public Pago getPago() {
        return pago;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return producto.getErrores().hayError() || entrega.getErrores().hayError() || ubicacion.getErrores().hayError() || pago.getErrores().hayError();
        }
    }
}
