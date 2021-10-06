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

/**
 * Clase de la interfaz gráfica de usario sobre el pago del pedido.
 */
public class PagosActivity extends BaseActivity implements View.OnClickListener {

    /**
     * Botón para elegir tarjeta como método de pago.
     */
    private MaterialButton mBtnTarjeta;

    /**
     * Botón para elegir efectivo como método de pago.
     */
    private MaterialButton mBtnEfectivo;

    /**
     * Card con datos de pago con tarjeta de crédito VISA.
     */
    private View mCardTarjeta;

    /**
     * Card con datos de pago con efectivo.
     */
    private View mCardEfectivo;

    /**
     * Grupo de botones para elegir el método de pago.
     */
    private MaterialButtonToggleGroup mBtnGroup;

    /**
     * Combobox de mes del vencimiento de la tarjeta.
     */
    private AutoCompleteTextView mTxtMes;

    /**
     * Combobox del año de vencimiento de la tarjeta.
     */
    private AutoCompleteTextView mTxtYear;

    /**
     * Campo de texto para la tarjeta.
     */
    private TextInputEditText mTxtTarjeta;

    /**
     * Layout del campo de texto del nombre del titular de la tarjeta.
     */
    private TextInputLayout mLayTitular;

    /**
     * Layout del campo de texto de la tarjeta.
     */
    private TextInputLayout mLayTarjeta;

    /**
     * Layout del campo de texto del CVC.
     */
    private TextInputLayout mLayCVC;

    /**
     * Layout del campo de texto de mes de vencimiento.
     */
    private TextInputLayout mLayMes;

    /**
     * Layout del campo de texto del año de vencimiento.
     */
    private TextInputLayout mLayYear;

    /**
     * Layout del campo de texto del monto.
     */
    private TextInputLayout mLayMonto;

    /**
     * Controlador de la interfaz gráfica de usuario.
     */
    private PagosControl mControl;

    @Override
    protected IControl getControl() {
        mControl = new PagosControl(this);
        return mControl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pagos;
    }

    @Override
    protected void iniciarViews() {
        mBtnTarjeta = findViewById(R.id.btnTarjeta);
        mBtnEfectivo = findViewById(R.id.btnEfectivo);

        mCardTarjeta = findViewById(R.id.cardPagoTarjeta);
        mCardEfectivo = findViewById(R.id.cardPagoEfectivo);
        mBtnGroup = findViewById(R.id.buttonGroupTipoPago);

        mTxtMes = findViewById(R.id.txtMesVto);
        mLayMes = findViewById(R.id.layMesVto);

        mTxtYear = findViewById(R.id.txtYearVto);
        mLayYear = findViewById(R.id.layYearVto);

        mTxtTarjeta = findViewById(R.id.txtTarjeta);
        mLayTarjeta = findViewById(R.id.txtLayTarjeta);

        TextInputEditText txtTitular = findViewById(R.id.txtTitular);
        mLayTitular = findViewById(R.id.txtLayTitular);

        TextInputEditText txtCVC = findViewById(R.id.txtCVC);
        mLayCVC = findViewById(R.id.txtLayCVC);

        TextInputEditText txtMonto = findViewById(R.id.txtMonto);
        mLayMonto = findViewById(R.id.txtLayMonto);

        TextView lblMonto = findViewById(R.id.lblMonto);
        lblMonto.setText(mControl.getMonto());

        findViewById(R.id.btnMonto).setOnClickListener(x ->
                toast("El monto se calcula sumando $50 cada 500 metros de viaje."));

        mTxtTarjeta.addTextChangedListener(new ObservadorTexto() {
            boolean mostrandoVisa = false;
            boolean mostrandoError = false;

            @Override
            protected void textoModificado(String nuevoTexto) {
                if (nuevoTexto.length() != 0) {
                    if (nuevoTexto.charAt(0) == '4'){
                        if (!mostrandoVisa) {
                            mTxtTarjeta.setCompoundDrawablesWithIntrinsicBounds(
                                    0,
                                    0,
                                    R.drawable.ic_visa,
                                    0);

                            mLayTarjeta.setError("");
                            mostrandoVisa = true;
                            mostrandoError = false;
                        }
                    }
                    else {
                        if (!mostrandoError) {
                            mostrandoVisa = false;
                            mostrandoError = true;
                            mLayTarjeta.setError("Solo se admiten tarjetas VISA.");
                            mTxtTarjeta.setCompoundDrawablesWithIntrinsicBounds(
                                    0,
                                    0,
                                    R.drawable.ic_credit_card,
                                    0);
                        }
                    }
                }
                else {
                    mTxtTarjeta.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_credit_card,
                            0);
                    mLayTarjeta.setError("");
                    mostrandoVisa = false;
                    mostrandoError = false;
                }
            }
        });


        iniciarAutocompletado();

        mBtnTarjeta.setOnClickListener(this);
        mBtnEfectivo.setOnClickListener(this);

        findViewById(R.id.txtSiguiente).setOnClickListener(x -> mControl.siguiente());

        txtTitular.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayTitular;
            }
        });
        txtCVC.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayCVC;
            }
        });
        mTxtMes.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayMes;
            }
        });
        mTxtYear.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayYear;
            }
        });
        txtMonto.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayMonto;
            }
        });
    }

    /**
     * Navega a la actividad de pagos.
     */
    public void siguiente() {
        navegar(ConfirmarActivity.class);
    }

    /**
     * Inicia los combobox para el vencimiento de la tarjeta.
     */
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

        mTxtMes.setAdapter(adapterMeses);
        mTxtYear.setAdapter(adapterYears);
    }

    @Override
    public void onClick(View v) {
        mostrarMetodoPago();
    }

    /**
     * Muestra la card con el método de pago elegido.
     */
    private void mostrarMetodoPago() {
        boolean esTarjeta = mBtnGroup.getCheckedButtonId() == R.id.btnTarjeta;

        mCardTarjeta.setVisibility(esTarjeta ? View.VISIBLE : View.GONE);
        mCardEfectivo.setVisibility(!esTarjeta ? View.VISIBLE : View.GONE);
    }

    /**
     * Obtener el titular de su campo de texto.
     * @return Nombre del titular.
     */
    public String getTitular() {
        return getTxtString(R.id.txtTitular);
    }

    /**
     * Obtener el número de tarjeta VISA ingresado.
     * @return Tarjeta de crédito del usuario.
     */
    public long getTarjeta() {
        return getTxtLong(R.id.txtTarjeta);
    }

    /**
     * Obtener el CVC ingresado.
     * @return CVC
     */
    public int getCVC() {
        return getTxtInt(R.id.txtCVC);
    }

    /**
     * Obtener el mes de vencimiento ingresado de la tarjeta VISA.
     * @return Mes de vencimiento.
     */
    public int getMes() {
        return getTxtInt(R.id.txtMesVto, -1);
    }

    /**
     * Obtener el año de vencimiento ingresado de la tarjeta VISA.
     * @return Año de vencimiento de la tarjeta.
     */
    public int getYear() {
        return getTxtInt(R.id.txtYearVto);
    }

    /**
     * Muestra los errores de usuario en la interfaz gráfica de pagos.
     * @param errores Clase con los errores de pagos.
     */
    public void setErrores(Pago.Errores errores) {
        if (errores.eLargoTarjeta())
            mLayTarjeta.setError("La longitud de la tarjeta es inválida (16 dígitos).");

        if (errores.eTarjetaNoVisa())
            mLayTarjeta.setError("Solo se admiten tarjetas VISA.");

        mLayTitular.setError(errores.eTitular()
                ? "Ingresá el numbre del titular. (min 5 carac)"
                : "");

        mLayCVC.setError(errores.eCVC()
                ? "Ingresá un CVC válido."
                : "");

        mLayMes.setError(errores.eMes()
                ? "Ingresá el mes de vencimiento."
                : "");

        mLayYear.setError(errores.eYear()
                ? "Ingresá el año de vencimiento."
                : "");

        if (errores.eTarjetaVencida())
            mLayMes.setError("La tarjeta está vencida.");

        mLayMonto.setError(errores.eMonto()
                ? "Debés abonar por lo menos con " + mControl.getMonto() + "."
                : "");
    }

    /**
     * Obtiene el método de pago elegido por el usuario.
     * @return Método de pago elegido.
     */
    public MetodoPago getMetodoPago() {
        return mBtnGroup.getCheckedButtonId() == R.id.btnTarjeta
                ? MetodoPago.Tarjeta
                : MetodoPago.Efectivo;
    }

    /**
     * Obtiene el monto ingresado por el usuario.
     * @return Monto ingresado por el usuario.
     */
    public double getMonto() {
        return getTxtDouble(R.id.txtMonto);
    }

    /**
     * Setea el titular de la tarjeta en su campo de texto.
     * @param titular Titular de la tarjeta.
     */
    public void setTitular(String titular) {
        setTxtString(R.id.txtTitular, titular);
    }

    /**
     * Setea la tarjeta de crédito del usuario.
     * @param tarjeta Tarjeta de crédito.
     */
    public void setTarjeta(long tarjeta) {
        setTxtString(R.id.txtTarjeta, tarjeta == 0
                ? ""
                : String.valueOf(tarjeta));
    }

    /**
     * Setea el CVC de la tarjeta de usuario.
     * @param cvc CVC.
     */
    public void setCVC(int cvc) {
        setTxtString(R.id.txtCVC, cvc == 0
                ? ""
                : String.valueOf(cvc));
    }

    /**
     * Setea el mes de vencimiento de la tarjeta de crédito del usuario.
     * @param mes Mes de vencimiento.
     */
    public void setMes(int mes) {
        String mesStr;
        if (mes == -1) {
            mesStr = "";
        }
        else{
            mesStr = String.valueOf(mes);
            if (mesStr.length() == 1) mesStr = "0" + mesStr;
        }
        mTxtMes.setText(mesStr, false);
    }

    /**
     * Setea el año de vencimiento de la tarjeta de crédito del usuario.
     * @param year Año de vencimiento.
     */
    public void setYear(int year) {
        if (year == 0) return;
        mTxtYear.setText(String.valueOf(year), false);
    }

    /**
     * Setea el monto ingresado en la interfaz de usuario.
     * @param monto Monto ingresado por el usuario.
     */
    public void setMonto(double monto) {
        setTxtString(R.id.txtMonto, monto == 0
                ? ""
                : String.valueOf(monto));
    }

    /**
     * Setea el método de pago en la interfaz de usuario.
     * @param metodoPago Método de pago elegido.
     */
    public void setMetodoPago(MetodoPago metodoPago) {
        mBtnTarjeta.setChecked(metodoPago == MetodoPago.Tarjeta);
        mBtnEfectivo.setChecked(metodoPago == MetodoPago.Efectivo);

        mostrarMetodoPago();
    }
}