package com.example.delivereat.ui.activities.loquesea;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.control.PagosControl;
import com.example.delivereat.model.pedidos.MetodoPago;
import com.example.delivereat.model.pedidos.Pago;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.example.delivereat.ui.abstracts.ObservadorTexto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PagosActivity extends BaseActivity implements View.OnClickListener {

    private MaterialButton btnTarjeta, btnEfectivo;
    private View cardTarjeta, cardEfectivo;
    private MaterialButtonToggleGroup btnGroup;
    private AutoCompleteTextView txtMes, txtYear;
    private TextInputEditText txtTitular, txtTarjeta, txtCVC, txtMonto;
    private TextInputLayout layTitular, layTarjeta, layCVC, layMes, layYear, layMonto;
    private TextView lblMonto;

    private PagosControl control;

    @Override
    protected IControl getControl() {
        control = new PagosControl(this);
        return control;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pagos;
    }

    @Override
    protected void iniciarViews() {
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

        lblMonto = findViewById(R.id.lblMonto);
        lblMonto.setText(control.getMonto());

        txtTarjeta.addTextChangedListener(new ObservadorTexto() {
            boolean mostrandoVisa = false;
            boolean mostrandoError = false;

            @Override
            protected void textoModificado(String nuevoTexto) {
                if (nuevoTexto.length() != 0) {
                    if (nuevoTexto.charAt(0) == '4'){
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
        });


        iniciarAutocompletado();

        btnTarjeta.setOnClickListener(this);
        btnEfectivo.setOnClickListener(this);

        findViewById(R.id.txtSiguiente).setOnClickListener(x -> control.siguiente());

        txtTitular.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layTitular;
            }
        });
        txtCVC.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layCVC;
            }
        });
        txtMes.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layMes;
            }
        });
        txtYear.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layYear;
            }
        });
        txtMonto.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layMonto;
            }
        });
    }

    public void siguiente() {
        navegar(ConfirmarActivity.class);
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
        mostrarMetodoPago();
    }

    private void mostrarMetodoPago() {
        boolean esTarjeta = btnGroup.getCheckedButtonId() == R.id.btnTarjeta;

        cardTarjeta.setVisibility(esTarjeta ? View.VISIBLE : View.GONE);
        cardEfectivo.setVisibility(!esTarjeta ? View.VISIBLE : View.GONE);
    }

    public String getTitular() {
        return getTxtString(R.id.txtTitular);
    }

    public long getTarjeta() {
        return getTxtLong(R.id.txtTarjeta);
    }

    public int getCVC() {
        return getTxtInt(R.id.txtCVC);
    }

    public int getMes() {
        return getTxtInt(R.id.txtMesVto, -1);
    }

    public int getYear() {
        return getTxtInt(R.id.txtYearVto);
    }

    public void setErrores(Pago.Errores errores) {
        if (errores.eLargoTarjeta())
            layTarjeta.setError("La longitud de la tarjeta es inválida (16 dígitos).");

        if (errores.eTarjetaNoVisa())
            layTarjeta.setError("Solo se admiten tarjetas VISA.");

        layTitular.setError(errores.eTitular()
                ? "Ingresá el numbre del titular. (min 5 carac)"
                : "");

        layCVC.setError(errores.eCVC()
                ? "Ingresá un CVC válido."
                : "");

        layMes.setError(errores.eMes()
                ? "Ingresá el mes de vencimiento."
                : "");

        layYear.setError(errores.eYear()
                ? "Ingresá el año de vencimiento."
                : "");

        if (errores.eTarjetaVencida())
            layMes.setError("La tarjeta está vencida.");

        layMonto.setError(errores.eMonto()
                ? "Debés abonar por lo menos con " + control.getMonto() + "."
                : "");
    }

    public MetodoPago getMetodoPago() {
        return btnGroup.getCheckedButtonId() == R.id.btnTarjeta
                ? MetodoPago.Tarjeta
                : MetodoPago.Efectivo;
    }

    public double getMonto() {
        return getTxtDouble(R.id.txtMonto);
    }

    public void setTitular(String titular) {
        setTxtString(R.id.txtTitular, titular);
    }

    public void setTarjeta(long tarjeta) {
        setTxtString(R.id.txtTarjeta, tarjeta == 0
                ? ""
                : String.valueOf(tarjeta));
    }

    public void setCVC(int cvc) {
        setTxtString(R.id.txtCVC, cvc == 0
                ? ""
                : String.valueOf(cvc));
    }

    public void setMes(int mes) {
        String mesStr;
        if (mes == -1) {
            mesStr = "";
        }
        else{
            mesStr = String.valueOf(mes);
            if (mesStr.length() == 1) mesStr = "0" + mesStr;
        }
        txtMes.setText(mesStr, false);
    }

    public void setYear(int year) {
        if (year == 0) return;
        txtYear.setText(String.valueOf(year), false);
    }

    public void setMonto(double monto) {
        setTxtString(R.id.txtMonto, monto == 0
                ? ""
                : String.valueOf(monto));
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        btnTarjeta.setChecked(metodoPago == MetodoPago.Tarjeta);
        btnEfectivo.setChecked(metodoPago == MetodoPago.Efectivo);

        mostrarMetodoPago();
    }
}