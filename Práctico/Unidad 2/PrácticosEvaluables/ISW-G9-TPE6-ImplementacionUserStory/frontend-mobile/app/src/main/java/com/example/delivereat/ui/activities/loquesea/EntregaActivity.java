package com.example.delivereat.ui.activities.loquesea;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;

import com.example.delivereat.R;
import com.example.delivereat.control.EntregaControl;
import com.example.delivereat.control.IControl;
import com.example.delivereat.model.Entrega;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EntregaActivity extends BaseActivity {

    private TextInputLayout layEntrega;
    private SwitchMaterial switchEntrega;
    private TextView lblSwitch;
    private boolean entregaPronto = true;
    private Calendar fechaHoraEntrega;
    private TextInputEditText dtpEntrega;

    private EntregaControl control;

    @Override
    protected IControl getControl() {
        control = new EntregaControl(this);
        return control;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_entrega;
    }

    @Override
    protected void iniciarViews() {
        lblSwitch = findViewById(R.id.lblSwitch);

        layEntrega = findViewById(R.id.layEntrega);
        dtpEntrega = findViewById(R.id.dtpEntrega);
        switchEntrega = findViewById(R.id.switchTiempo);

        dtpEntrega.setOnClickListener(x -> mostrarDialogoFecha());
        switchEntrega.setOnClickListener(x -> alternarTiempoEntrega());
        lblSwitch.setOnClickListener(x -> {
            alternarTiempoEntrega();
            switchEntrega.setChecked(entregaPronto);
        });
        findViewById(R.id.txtSiguiente).setOnClickListener(x -> control.siguiente());

        dtpEntrega.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layEntrega;
            }
        });
    }

    private void alternarTiempoEntrega() {
        entregaPronto = !entregaPronto;
        actualizarEntrega();
    }

    private void actualizarEntrega() {
        layEntrega.setVisibility(entregaPronto
                ? View.GONE
                : View.VISIBLE);
        lblSwitch.setText(entregaPronto
                ? "Recibirlo lo antes posible"
                : "Recibirlo en fecha y hora");
        switchEntrega.setChecked(entregaPronto);
    }

    private void mostrarDialogoFecha() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                fechaHoraEntrega = calendar;

                setTxtEntrega();
            };

            new TimePickerDialog(
                    EntregaActivity.this,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false).
                    show();
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

    private void setTxtEntrega() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

        dtpEntrega.setText(simpleDateFormat.format(fechaHoraEntrega.getTime()));
    }

    public void setErrores(Entrega.Errores errores) {
        String errorFecha = errores.eFechaRequerida()
                ? "Ingresá cuando querés que te llevemos el pedido."
                : "";

        errorFecha += errores.eFechaMinima()
                ? "Por lo menos dentro de una hora."
                : "";

        layEntrega.setError(errorFecha);
    }

    public void siguiente() {
        navegar(PagosActivity.class);
    }

    public void setEntregaPronto(boolean entregaPronto) {
        this.entregaPronto = entregaPronto;
        actualizarEntrega();
    }

    public void setFechaEntrega(Calendar fechaHoraEntrega) {
        if (fechaHoraEntrega == null) return;

        this.fechaHoraEntrega = fechaHoraEntrega;
        setTxtEntrega();
    }

    public boolean getEntregaPronto() {
        return entregaPronto;
    }

    public Calendar getFechaHoraEntrega() {
        return entregaPronto
                ? null
                : fechaHoraEntrega;
    }
}