package com.example.delivereat.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.delivereat.R;
import com.example.delivereat.control.UbicacionControl;
import com.example.delivereat.model.Pedido;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class UbicacionActivity extends AppCompatActivity {

    private String[] ciudades;

    private AutoCompleteTextView txtCiudadO, txtCiudadD;
    private TextInputEditText txtCalleO, txtCalleD, txtNumO, txtNumD, txtComentarioO, txtComentarioD;
    private TextInputLayout layCalleO, layCalleD, layNumO, layNumD, layCiudadOrigen, layCiudadDestino;
    private LinearProgressIndicator progress;
    private ActionMenuItemView btnSiguiente;

    private static final int UBICACION_ORIGEN = 778;
    private static final int UBICACION_DESTINO = 779;

    private UbicacionControl control;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        ciudades = getResources().getStringArray(R.array.ciudades);

        control = new UbicacionControl(this);
        iniciarViews();
    }

    private void iniciarViews() {
        txtCiudadO = findViewById(R.id.txtCiudadOrigen);
        txtCiudadD = findViewById(R.id.txtCiudadDestino);
        txtCalleO = findViewById(R.id.txtCalleOrigen);
        txtCalleD = findViewById(R.id.txtCalleDestino);
        txtNumO = findViewById(R.id.txtNumOrigen);
        txtNumD = findViewById(R.id.txtNumDestino);
        txtComentarioO = findViewById(R.id.txtComentarioOrigen);
        txtComentarioD = findViewById(R.id.txtComentarioDestino);

        layCiudadOrigen = findViewById(R.id.txtLayCiudadOrigen);
        layCiudadDestino = findViewById(R.id.txtLayCiudadDestino);
        layCalleO = findViewById(R.id.txtLayCalleOrigen);
        layCalleD = findViewById(R.id.txtLayCalleDestino);
        layNumO = findViewById(R.id.txtLayNumOrigen);
        layNumD = findViewById(R.id.txtLayNumDestino);

        progress = findViewById(R.id.progressUbicacion);
        btnSiguiente = findViewById(R.id.txtSiguiente);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ciudades);
        txtCiudadO.setAdapter(adapter);
        txtCiudadD.setAdapter(adapter);

        findViewById(R.id.btnMapsOrigen).setOnClickListener(
                x -> {
                    try {
                        startActivityForResult(new Intent(UbicacionActivity.this, MapsActivity.class), UBICACION_ORIGEN);
                        overridePendingTransition(R.anim.from_up, R.anim.to_down);
                    }
                    catch (Exception e) {
                        toast("Ocurrió un error al abrir el mapa");
                    }
                }
        );
        findViewById(R.id.btnMapsDestino).setOnClickListener(
                x -> {
                    try {
                        startActivityForResult(new Intent(UbicacionActivity.this, MapsActivity.class), UBICACION_DESTINO);
                        overridePendingTransition(R.anim.from_up, R.anim.to_down);
                    }
                    catch (Exception e) {
                        toast("Ocurrió un error al abrir el mapa");
                    }
                }
        );
        btnSiguiente.setOnClickListener(x -> control.siguiente());
    }

    public void siguiente() {
        startActivity(new Intent(this, PagosActivity.class));
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
    }

    public void setErrores(Pedido.Errores errores) {
        layCalleO.setError(errores.calleOrigen
                ? "Debe ingresar la calle de origen."
                : "");

        layCalleD.setError(errores.calleDestino
                ? "Debe ingresar la calle de destino."
                : "");

        layNumO.setError(errores.numeroOrigen
                ? "Debe ingresar el número de domicilio de origen."
                : "");

        layNumD.setError(errores.numeroDestino
                ? "Debe ingresar número de domicilio de destino."
                : "");

        layCiudadOrigen.setError(errores.ciudadOrigen
                ? "Debe ingresar la ciudad de origen."
                : "");

        layCiudadDestino.setError(errores.ciudadDestino
                ? "Debe ingresar la ciudad de destino."
                : "");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }

    public String getCalleOrigen() {
        return Objects.requireNonNull(txtCalleO.getText()).toString();
    }

    public String getCalleDestino() {
        return Objects.requireNonNull(txtCalleD.getText()).toString();
    }

    public int getNumeroOrigen() {
        String num = Objects.requireNonNull(txtNumO.getText()).toString();
        return num.length() > 0
                ? Integer.parseInt(num)
                : -1;
    }

    public int getNumeroDestino() {
        String num = Objects.requireNonNull(txtNumD.getText()).toString();
        return num.length() > 0
                ? Integer.parseInt(num)
                : -1;    }

    public String getCiudadOrigen() {
        return Objects.requireNonNull(txtCiudadO.getText()).toString();
    }

    public String getCiudadDestino() {
        return Objects.requireNonNull(txtCiudadD.getText()).toString();
    }

    public String getComentarioDestino() {
        return Objects.requireNonNull(txtComentarioD.getText()).toString();
    }

    public String getComentarioOrigen() {
        return Objects.requireNonNull(txtComentarioO.getText()).toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        control.buscarDireccion(requestCode);
    }

    public void toast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void setDireccion(int codigoDireccion, String calle, String numero, String ciudad) {
        if (codigoDireccion == UBICACION_ORIGEN) {
            txtCalleO.setText(calle);
            txtNumO.setText(numero);
            txtCiudadO.setText(ciudad);
        }
        else {
            txtCalleD.setText(calle);
            txtNumD.setText(numero);
            txtCiudadD.setText(ciudad);
        }
    }

    public void esperar(boolean esperar) {
        progress.setVisibility(esperar
                ? View.VISIBLE
                : View.GONE);
        btnSiguiente.setEnabled(!esperar);
    }
}