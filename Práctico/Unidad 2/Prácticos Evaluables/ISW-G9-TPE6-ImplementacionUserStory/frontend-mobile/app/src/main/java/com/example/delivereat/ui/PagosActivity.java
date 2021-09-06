package com.example.delivereat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.delivereat.R;
import com.example.delivereat.control.PagosControl;
import com.example.delivereat.model.MetodoPago;
import com.example.delivereat.model.Pedido;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class PagosActivity extends AppCompatActivity implements View.OnClickListener {

    private View cardTarjeta, cardEfectivo;
    private MaterialButtonToggleGroup btnGroup;
    private MaterialButton btnTarjeta, btnEfectivo;
    private AutoCompleteTextView txtMes, txtYear;
    private TextInputEditText txtTitular, txtTarjeta, txtCVC, txtMonto;
    private TextInputLayout layTitular, layTarjeta, layCVC, layMes, layYear, layMonto;
    private LinearProgressIndicator progress;

    private PagosControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        progress = findViewById(R.id.progressPago);

        btnTarjeta = findViewById(R.id.btnTarjeta);
        btnEfectivo = findViewById(R.id.btnEfectivo);

        cardTarjeta = findViewById(R.id.cardPagoTarjeta);
        cardEfectivo = findViewById(R.id.cardPagoEfectivo);
        btnGroup = findViewById(R.id.buttonGroupTipoPago);

        txtMes = findViewById(R.id.txtMesVto);
        layMes = findViewById(R.id.layMesVto);

        txtYear = findViewById(R.id.txtYearVto);
        layYear = findViewById(R.id.layYearVto);

        txtTarjeta = findViewById(R.id.txtTarjeta);
        layTarjeta = findViewById(R.id.txtLayTarjeta);

        txtTitular = findViewById(R.id.txtTitular);
        layTitular = findViewById(R.id.txtLayTitular);

        txtCVC = findViewById(R.id.txtCVC);
        layCVC = findViewById(R.id.txtLayCVC);

        txtMonto = findViewById(R.id.txtMonto);
        layMonto = findViewById(R.id.txtLayMonto);

        txtTarjeta.addTextChangedListener(new TextWatcher() {
            boolean mostrandoVisa = false;
            boolean mostrandoError = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (s.charAt(0) == '4'){
                        if (!mostrandoVisa) {
                            txtTarjeta.setCompoundDrawablesWithIntrinsicBounds(
                                    0,
                                    0,
                                    R.drawable.ic_visa,
                                    0);

                            layTarjeta.setError("");
                            mostrandoVisa = true;
                            mostrandoError = false;
                        }
                    }
                    else {
                        if (!mostrandoError) {
                            mostrandoVisa = false;
                            mostrandoError = true;
                            layTarjeta.setError("Solo se admiten tarjetas VISA.");
                            txtTarjeta.setCompoundDrawablesWithIntrinsicBounds(
                                    0,
                                    0,
                                    R.drawable.ic_credit_card,
                                    0);
                        }
                    }
                }
                else {
                    txtTarjeta.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_credit_card,
                            0);
                    layTarjeta.setError("");
                    mostrandoVisa = false;
                    mostrandoError = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        iniciarAutocompletado();

        btnTarjeta.setOnClickListener(this);
        btnEfectivo.setOnClickListener(this);

        findViewById(R.id.btnConfirmar).setOnClickListener(x -> control.siguiente());

        control = new PagosControl(this);
    }

    public void siguiente() {
        Intent intent = new Intent(PagosActivity.this, CompletadoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
    }

    private void iniciarAutocompletado() {
        String[] meses = new String[12];
        String[] years = new String[30];
        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 0; i < 12; i++) {
            meses[i] = (i < 9 ? "0" : "") + (i + 1);
        }

        for (int i = 0; i < 30; i++) {
            years[i] = String.valueOf(year + i);
        }

        ArrayAdapter<String> adapterMeses = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                meses);

        ArrayAdapter<String> adapterYears = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                years);

        txtMes.setAdapter(adapterMeses);
        txtYear.setAdapter(adapterYears);
    }

    @Override
    public void onClick(View v) {

        boolean esTarjeta = btnGroup.getCheckedButtonId() == R.id.btnTarjeta;

        cardTarjeta.setVisibility(esTarjeta ? View.VISIBLE : View.GONE);
        cardEfectivo.setVisibility(!esTarjeta ? View.VISIBLE : View.GONE);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }

    public String getTitular() {
        return Objects.requireNonNull(txtTitular.getText()).toString();
    }

    public long getTarjeta() {
        String txt = Objects.requireNonNull(txtTarjeta.getText()).toString();

        return txt.length() == 0
                ? -1
                : Long.parseLong(txt);
    }

    public int getCVC() {
        String txt = Objects.requireNonNull(txtCVC.getText()).toString();

        return txt.length() == 0
                ? -1
                : Integer.parseInt(txt);
    }

    public int getMes() {
        String txt = Objects.requireNonNull(txtMes.getText()).toString();

        return txt.length() == 0
                ? -1
                : Integer.parseInt(txt);
    }

    public int getYear() {
        String txt = Objects.requireNonNull(txtYear.getText()).toString();

        return txt.length() == 0
                ? -1
                : Integer.parseInt(txt);
    }

    public void setErrores(Pedido.Errores errores) {
        if (errores.tarjetaLargo)
            layTarjeta.setError("La longitud de la tarjeta es inválida (16 dígitos).");

        if (errores.tarjetaNoVisa)
            layTarjeta.setError("Solo se admiten tarjetas VISA.");

        layTitular.setError(errores.titular
                ? "Ingresá el numbre del titular."
                : "");

        layCVC.setError(errores.cvc
                ? "Ingresá el CVC."
                : "");

        layMes.setError(errores.mesVto
                ? "Ingresá el mes de vencimiento."
                : "");

        layYear.setError(errores.yearVto
                ? "Ingresá el año de vencimiento."
                : "");

        if (errores.tarjetaVencida)
            layMes.setError("La tarjeta está vencida.");

        layMonto.setError(errores.monto
                ? "Ingresá el monto con el que abonarás."
                : "");
    }

    public MetodoPago getMetodoPago() {
        return btnGroup.getCheckedButtonId() == R.id.btnTarjeta
                ? MetodoPago.Tarjeta
                : MetodoPago.Efectivo;
    }

    public double getMonto() {
        String txt = Objects.requireNonNull(txtMonto.getText()).toString();

        return txt.length() == 0
                ? -1d
                : Double.parseDouble(txt);
    }

    public void esperar(boolean esperar) {
        progress.setVisibility(esperar
                ? View.VISIBLE
                : View.GONE);

        layTitular.setEnabled(!esperar);
        layTarjeta.setEnabled(!esperar);
        layCVC.setEnabled(!esperar);
        layMes.setEnabled(!esperar);
        layYear.setEnabled(!esperar);
        btnEfectivo.setEnabled(!esperar);
        btnTarjeta.setEnabled(!esperar);
    }

    public void toast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}