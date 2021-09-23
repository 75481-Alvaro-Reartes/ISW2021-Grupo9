package com.example.delivereat.ui.activities.loquesea;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.delivereat.R;
import com.example.delivereat.control.EntregaControl;
import com.example.delivereat.control.IControl;
import com.example.delivereat.model.pedidos.Entrega;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Clase de la interfaz de usuario de entrega del pedido.
 */
public class EntregaActivity extends BaseActivity {

    /**
     * Layout del campo de texto de entrega.
     */
    private TextInputLayout mLayEntrega;

    /**
     * Switch para alternar entre entrega lo antes posible o entrega programada.
     */
    private SwitchMaterial mSwitchEntrega;

    /**
     * Label que muestra si la entrega es lo antes posible o programada en función del
     * estado del switch.
     */
    private TextView mLblSwitch;

    /**
     * Atributo para representar si se entrega lo antes posible o no.
     */
    private boolean mEntregaPronto = true;

    /**
     * Fecha y hora de la entrega.
     */
    private Calendar mFechaHoraEntrega;

    /**
     * Campo de texto con la fecha y hora de entrega.
     */
    private TextInputEditText mDtpEntrega;

    /**
     * Controlador de la interfaza gráfica de usuario.
     */
    private EntregaControl mControl;

    @Override
    protected IControl getControl() {
        mControl = new EntregaControl(this);
        return mControl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_entrega;
    }

    @Override
    protected void iniciarViews() {
        mLblSwitch = findViewById(R.id.lblSwitch);

        mLayEntrega = findViewById(R.id.layEntrega);
        mDtpEntrega = findViewById(R.id.dtpEntrega);
        mSwitchEntrega = findViewById(R.id.switchTiempo);

        mDtpEntrega.setOnClickListener(x -> mostrarDialogoFecha());
        mSwitchEntrega.setOnClickListener(x -> alternarTiempoEntrega());
        mLblSwitch.setOnClickListener(x -> {
            alternarTiempoEntrega();
            mSwitchEntrega.setChecked(mEntregaPronto);
        });
        findViewById(R.id.txtSiguiente).setOnClickListener(x -> mControl.siguiente());

        mDtpEntrega.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayEntrega;
            }
        });
    }

    /**
     * Alterna entre tiempo de entrega lo antes posible o programado.
     */
    private void alternarTiempoEntrega() {
        mEntregaPronto = !mEntregaPronto;
        actualizarEntrega();
    }

    /**
     * Actualiza la visualización del switch con el momento de entrega.
     */
    private void actualizarEntrega() {
        mLayEntrega.setVisibility(mEntregaPronto
                ? View.GONE
                : View.VISIBLE);
        mLblSwitch.setText(mEntregaPronto
                ? "Recibirlo lo antes posible"
                : "Recibirlo en fecha y hora");
        mSwitchEntrega.setChecked(mEntregaPronto);
    }

    /**
     * Muestra el diálogo de selección de fecha y hora, seteando la fecha mínima y máxima
     * en función de la fecha actual.
     */
    private void mostrarDialogoFecha() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                mFechaHoraEntrega = calendar;

                setTxtEntrega();
            };

            new TimePickerDialog(
                    EntregaActivity.this,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false).show();
        };
        DatePickerDialog dp = new DatePickerDialog(
                EntregaActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DATE, 7);
        dp.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        dp.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        dp.show();
    }

    /**
     * Muestra la fecha y hora de entrega elegida en el campo de texto.
     */
    private void setTxtEntrega() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

        mDtpEntrega.setText(simpleDateFormat.format(mFechaHoraEntrega.getTime()));
    }

    /**
     * Muestra los errores de la interfaz actual en los campos de texto correspondientes.
     * @param errores Clase con los errores de la interfaz entrega.
     */
    public void setErrores(Entrega.Errores errores) {
        String errorFecha = errores.eFechaRequerida()
                ? "Ingresá cuando querés que te llevemos el pedido."
                : "";

        errorFecha += errores.eFechaMinima()
                ? "Por lo menos dentro de una hora."
                : "";

        errorFecha += errores.eRangoHoras()
                ? "Horas hábiles: de 8 a 23:59"
                : "";

        mLayEntrega.setError(errorFecha);
    }

    /**
     * Avanza a la actividad de pagos.
     */
    public void siguiente() {
        navegar(PagosActivity.class);
    }

    /**
     * Setea el momento de entrega.
     * @param entregaPronto Entrega lo antes posible o en momento programado.
     */
    public void setEntregaPronto(boolean entregaPronto) {
        this.mEntregaPronto = entregaPronto;
        actualizarEntrega();
    }

    /**
     * Setea la fecha y hora de entrega en caso de haberla.
     * @param fechaHoraEntrega Fecha y hora de entrega.
     */
    public void setFechaEntrega(@Nullable Calendar fechaHoraEntrega) {
        if (fechaHoraEntrega == null) return;

        this.mFechaHoraEntrega = fechaHoraEntrega;
        setTxtEntrega();
    }

    /**
     * Indica si entrega o no el pedido lo antes posible.
     * @return True si entrega el pedido lo antes posible.
     */
    public boolean getEntregaPronto() {
        return mEntregaPronto;
    }

    /**
     * Obtiene la fecha y hora de entrega del pedido, en caso de haberla.
     * @return Fecha y hora de entrega del pedido.
     */
    @Nullable
    public Calendar getFechaHoraEntrega() {
        return mEntregaPronto
                ? null
                : mFechaHoraEntrega;
    }
}