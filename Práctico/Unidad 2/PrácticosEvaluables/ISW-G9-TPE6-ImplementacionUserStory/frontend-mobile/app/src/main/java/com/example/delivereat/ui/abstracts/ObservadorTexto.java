package com.example.delivereat.ui.abstracts;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Esta clase se implementa ya que en la app no se hace uso de todos
 * los métodos de TextWatcher, y de este modo permite que en cada caso
 * que se la utilice, solo implemente un método simplificado
 * derivado de onTextChanged, simplificando el código de las activities.
 *
 */
public abstract class ObservadorTexto implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        textoModificado(s.toString());
    }

    protected abstract void textoModificado(String nuevoTexto);

    @Override
    public void afterTextChanged(Editable s) {

    }
}
