package com.example.delivereat.ui.activities.loquesea;

import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.control.UbicacionControl;
import com.example.delivereat.model.pedidos.Direccion;
import com.example.delivereat.model.pedidos.Ubicacion;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.example.delivereat.ui.activities.otros.MapsActivity;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.util.Constantes;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * Clase de la interfaz gráfica para cargar la ubicación del origen y destino del pedido.
 */
public class UbicacionActivity extends BaseActivity {

    private AutoCompleteTextView mTxtCiudadOrigen, mTxtCiudadDestino;
    private TextInputEditText mTxtNumO, mTxtNumD;
    private TextInputLayout mLayCalleO, mLayCalleD, mLayNumO, mLayNumD, mLayCiudadOrigen, mLayCiudadDestino;
    private LinearProgressIndicator mProgress;
    private ActionMenuItemView mBtnSiguiente;

    private UbicacionControl mControl;

    @Override
    protected IControl getControl() {
        mControl = new UbicacionControl(this);
        return mControl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ubicacion;
    }

    @Override
    protected void iniciarViews() {
        mTxtCiudadOrigen = findViewById(R.id.txtCiudadOrigen);
        mTxtCiudadDestino = findViewById(R.id.txtCiudadDestino);
        mControl.iniciarCiudades();

        TextInputEditText txtCalleO = findViewById(R.id.txtCalleOrigen);
        TextInputEditText txtCalleD = findViewById(R.id.txtCalleDestino);
        mTxtNumO = findViewById(R.id.txtNumOrigen);
        mTxtNumD = findViewById(R.id.txtNumDestino);

        mLayCiudadOrigen = findViewById(R.id.txtLayCiudadOrigen);
        mLayCiudadDestino = findViewById(R.id.txtLayCiudadDestino);
        mLayCalleO = findViewById(R.id.txtLayCalleOrigen);
        mLayCalleD = findViewById(R.id.txtLayCalleDestino);
        mLayNumO = findViewById(R.id.txtLayNumOrigen);
        mLayNumD = findViewById(R.id.txtLayNumDestino);

        mProgress = findViewById(R.id.progressUbicacion);
        mBtnSiguiente = findViewById(R.id.txtSiguiente);

        findViewById(R.id.btnMapsOrigen).setOnClickListener(x -> abrirMapa(Constantes.UBICACION_ORIGEN));
        findViewById(R.id.btnMapsDestino).setOnClickListener(x -> abrirMapa(Constantes.UBICACION_DESTINO));

        mBtnSiguiente.setOnClickListener(x -> mControl.siguiente());

        txtCalleO.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayCalleO;
            }
        });

        txtCalleD.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayCalleD;
            }
        });

        mTxtNumO.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayNumO;
            }
        });

        mTxtNumD.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayNumD;
            }
        });

        mTxtCiudadOrigen.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayCiudadOrigen;
            }
        });

        mTxtCiudadDestino.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayCiudadDestino;
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
        mTxtCiudadOrigen.setAdapter(adapter);
        mTxtCiudadDestino.setAdapter(adapter);
    }

    public void siguiente() {
        navegar(EntregaActivity.class);
    }

    public void setErrores(Ubicacion.Errores errores) {
        Direccion.Errores eOrigen = errores.getErroresOrigen();
        Direccion.Errores eDestino = errores.getErroresDestino();

        mLayCalleO.setError(eOrigen.eCalle()
                ? "Debe ingresar la calle de origen. (min 5 caracteres)"
                : "");

        mLayCalleD.setError(eDestino.eCalle()
                ? "Debe ingresar la calle de destino. (min 5 caracteres)"
                : "");

        mLayNumO.setError(eOrigen.eNumero()
                ? "Debe ingresar el número de domicilio de origen."
                : "");

        mLayNumD.setError(eDestino.eNumero()
                ? "Debe ingresar número de domicilio de destino."
                : "");

        mLayCiudadOrigen.setError(eOrigen.eCiudad()
                ? "Por favor, seleccioná la ciudad del comercio."
                : "");

        mLayCiudadDestino.setError(eDestino.eCiudad()
                ? "Por favor, seleccioná la ciudad para la entrega."
                : "");

        if (errores.eCiudadesDistintas()){
            mLayCiudadDestino.setError("No se realizan envíos entre ciudades.");
            mLayCiudadOrigen.setError("No se realizan envíos entre ciudades.");
        }

        if (errores.eDireccionDuplicada() ){
            mLayCalleO.setError("Ingresaste la misma dirección.");
            mLayCalleD.setError("Ingresaste la misma dirección.");
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
            mControl.buscarDireccion(requestCode);
    }

    public void esperar(boolean esperar) {
        mProgress.setVisibility(esperar
                ? View.VISIBLE
                : View.GONE);
        mBtnSiguiente.setEnabled(!esperar);
    }

    public void setCiudadOrigen(String ciudad) {
        mTxtCiudadOrigen.setText(ciudad, false);
    }

    public void setNumeroOrigen(int numero) {
        mTxtNumO.setText(numero == 0
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
        mTxtCiudadDestino.setText(ciudad, false);
    }

    public void setNumeroDestino(int numero) {
        mTxtNumD.setText(numero == 0
                ? ""
                : String.valueOf(numero));    }

    public void setCalleDestino(String calle) {
        setTxtString(R.id.txtCalleDestino, calle);
    }

    public void setComentarioDestino(String comentario) {
        setTxtString(R.id.txtComentarioDestino, comentario);
    }
}