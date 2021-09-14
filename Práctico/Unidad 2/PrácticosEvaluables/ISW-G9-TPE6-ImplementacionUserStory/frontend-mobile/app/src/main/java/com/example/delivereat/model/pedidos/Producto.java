package com.example.delivereat.model.pedidos;

import com.example.delivereat.model.otros.ErrorManager;
import com.example.delivereat.model.otros.Imagen;
import com.example.delivereat.util.Constantes;

public class Producto {

    private String mNombre = "papas fritas";
    private Imagen mImagen;

    public Producto() {
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public Imagen getImagen() {
        return mImagen;
    }

    public void setImagen(Imagen imagen) {
        mImagen = imagen;
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return eRequerido();
        }

        public boolean eRequerido() {
            return mNombre.length() < Constantes.MIN_CARACTERES;
        }
    }
}
