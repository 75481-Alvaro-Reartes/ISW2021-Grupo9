package com.example.delivereat.ui.abstracts;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Esta clase limpia automáticamente el mensaje de error de un textbox al escribir
 */
public abstract class ObservadorLimpiador implements TextWatcher {
    private TextInputLayout layout;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (layout == null) layout = setEditTextLayout();
        layout.setError("");
    }

    public abstract TextInputLayout setEditTextLayout();

    @Override
    public void afterTextChanged(Editable s) {

    }

}
