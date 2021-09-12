package com.example.delivereat.ui.activities.loquesea;

import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.control.UbicacionControl;
import com.example.delivereat.model.Direccion;
import com.example.delivereat.model.Ubicacion;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.example.delivereat.ui.activities.otros.MapsActivity;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.util.Constantes;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class UbicacionActivity extends BaseActivity {

    private AutoCompleteTextView txtCiudadOrigen, txtCiudadDestino;
    private TextInputEditText txtNumO, txtNumD;
    private TextInputLayout layCalleO, layCalleD, layNumO, layNumD, layCiudadOrigen, layCiudadDestino;
    private LinearProgressIndicator progress;
    private ActionMenuItemView btnSiguiente;

    private UbicacionControl control;

    @Override
    protected IControl getControl() {
        control = new UbicacionControl(this);
        return control;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ubicacion;
    }

    @Override
    protected void iniciarViews() {
        txtCiudadOrigen = findViewById(R.id.txtCiudadOrigen);
        txtCiudadDestino = findViewById(R.id.txtCiudadDestino);
        control.iniciarCiudades();

        TextInputEditText txtCalleO = findViewById(R.id.txtCalleOrigen);
        TextInputEditText txtCalleD = findViewById(R.id.txtCalleDestino);
        txtNumO = findViewById(R.id.txtNumOrigen);
        txtNumD = findViewById(R.id.txtNumDestino);

        layCiudadOrigen = findViewById(R.id.txtLayCiudadOrigen);
        layCiudadDestino = findViewById(R.id.txtLayCiudadDestino);
        layCalleO = findViewById(R.id.txtLayCalleOrigen);
        layCalleD = findViewById(R.id.txtLayCalleDestino);
        layNumO = findViewById(R.id.txtLayNumOrigen);
        layNumD = findViewById(R.id.txtLayNumDestino);

        progress = findViewById(R.id.progressUbicacion);
        btnSiguiente = findViewById(R.id.txtSiguiente);

        findViewById(R.id.btnMapsOrigen).setOnClickListener(x -> abrirMapa(Constantes.UBICACION_ORIGEN));
        findViewById(R.id.btnMapsDestino).setOnClickListener(x -> abrirMapa(Constantes.UBICACION_DESTINO));

        btnSiguiente.setOnClickListener(x -> control.siguiente());

        txtCalleO.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layCalleO;
            }
        });

        txtCalleD.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layCalleD;
            }
        });

        txtNumO.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layNumO;
            }
        });

        txtNumD.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layNumD;
            }
        });

        txtCiudadOrigen.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layCiudadOrigen;
            }
        });

        txtCiudadDestino.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layCiudadDestino;
            }
        });
    }



    private void abrirMapa(int codigoMapa) {
        try {
            startActivityForResult(new Intent(UbicacionActivity.this, MapsActivity.class), codigoMapa);
            overridePendingTransition(R.anim.from_up, R.anim.to_down);
        }
        catch (Exception e) {
            toast("Ocurrió un error al abrir el mapa");
        }
    }

    public void setListaCiudades(List<String> ciudades) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ciudades);
        txtCiudadOrigen.setAdapter(adapter);
        txtCiudadDestino.setAdapter(adapter);
    }

    public void siguiente() {
        navegar(EntregaActivity.class);
    }

    public void setErrores(Ubicacion.Errores errores) {
        Direccion.Errores eOrigen = errores.getErroresOrigen();
        Direccion.Errores eDestino = errores.getErroresDestino();

        layCalleO.setError(eOrigen.eCalle()
                ? "Debe ingresar la calle de origen. (min 5 caracteres)"
                : "");

        layCalleD.setError(eDestino.eCalle()
                ? "Debe ingresar la calle de destino. (min 5 caracteres)"
                : "");

        layNumO.setError(eOrigen.eNumero()
                ? "Debe ingresar el número de domicilio de origen."
                : "");

        layNumD.setError(eDestino.eNumero()
                ? "Debe ingresar número de domicilio de destino."
                : "");

        layCiudadOrigen.setError(eOrigen.eCiudad()
                ? "Por favor, seleccioná la ciudad del comercio."
                : "");

        layCiudadDestino.setError(eDestino.eCiudad()
                ? "Por favor, seleccioná la ciudad para la entrega."
                : "");

        if (errores.eCiudadesDistintas()){
            layCiudadDestino.setError("No se realizan envíos entre ciudades.");
            layCiudadOrigen.setError("No se realizan envíos entre ciudades.");
        }

        if (errores.eDireccionDuplicada() ){
            layCalleO.setError("Ingresaste la misma dirección.");
            layCalleD.setError("Ingresaste la misma dirección.");
        }
    }

    public String getCalleOrigen() {
        return getTxtString(R.id.txtCalleOrigen);
    }

    public String getCalleDestino() {
        return getTxtString(R.id.txtCalleDestino);
    }

    public int getNumeroOrigen() {
        return getTxtInt(R.id.txtNumOrigen);
    }

    public int getNumeroDestino() {
        return getTxtInt(R.id.txtNumDestino);
    }

    public String getCiudadOrigen() {
        return getTxtString(R.id.txtCiudadOrigen);
    }

    public String getCiudadDestino() {
        return getTxtString(R.id.txtCiudadDestino);
    }

    public String getComentarioDestino() {
        return getTxtString(R.id.txtComentarioDestino);
    }

    public String getComentarioOrigen() {
        return getTxtString(R.id.txtComentarioOrigen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            control.buscarDireccion(requestCode);
    }

    public void esperar(boolean esperar) {
        progress.setVisibility(esperar
                ? View.VISIBLE
                : View.GONE);
        btnSiguiente.setEnabled(!esperar);
    }

    public void setCiudadOrigen(String ciudad) {
        txtCiudadOrigen.setText(ciudad, false);
    }

    public void setNumeroOrigen(int numero) {
        txtNumO.setText(numero == 0
                ? ""
                : String.valueOf(numero));
    }

    public void setCalleOrigen(String calle) {
        setTxtString(R.id.txtCalleOrigen, calle);
    }

    public void setComentarioOrigen(String comentario) {
        setTxtString(R.id.txtComentarioOrigen, comentario);
    }

    public void setCiudadDestino(String ciudad) {
        txtCiudadDestino.setText(ciudad, false);
    }

    public void setNumeroDestino(int numero) {
        txtNumD.setText(numero == 0
                ? ""
                : String.valueOf(numero));    }

    public void setCalleDestino(String calle) {
        setTxtString(R.id.txtCalleDestino, calle);
    }

    public void setComentarioDestino(String comentario) {
        setTxtString(R.id.txtComentarioDestino, comentario);
    }
}