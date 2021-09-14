package com.example.delivereat.model.pedidos;

import com.example.delivereat.model.otros.ErrorManager;
import com.example.delivereat.model.otros.Imagen;
import com.example.delivereat.util.Constantes;

public class Producto {

    private String nombre = "papas fritas";
    private Imagen imagen;

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
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
            return nombre.length() < Constantes.MIN_CARACTERES;
        }
    }
}
