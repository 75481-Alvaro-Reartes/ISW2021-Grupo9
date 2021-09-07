package com.example.delivereat.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivereat.R;
import com.example.delivereat.control.ProductosControl;
import com.example.delivereat.model.Pedido;
import com.example.delivereat.ui.adapters.ImagenesAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class ProductosActivity extends AppCompatActivity {

    private TextInputEditText dtpEntrega, txtProducto;
    private TextInputLayout layEntrega, layProducto;
    private SwitchMaterial switchEntrega;
    private TextView lblSwitch, lblPesoImg;
    private boolean entregaPronto = true;
    private RecyclerView listaImagenes;
    private ImagenesAdapter adapter;
    private long filesKB;
    private Calendar fechaHoraEntrega;

    private ProductosControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        filesKB = 0;

        control = new ProductosControl(this);
        iniciarViews();
    }

    private void iniciarViews() {
        listaImagenes = findViewById(R.id.listaImagenes);
        dtpEntrega = findViewById(R.id.dtpEntrega);
        layEntrega = findViewById(R.id.layEntrega);
        txtProducto = findViewById(R.id.txtProducto);
        switchEntrega = findViewById(R.id.switchTiempo);
        lblPesoImg = findViewById(R.id.lblPesoImg);
        layProducto = findViewById(R.id.txtLayProducto);
        lblSwitch = findViewById(R.id.lblSwitch);
        dtpEntrega.setOnClickListener(x -> mostrarDialogoFecha());

        findViewById(R.id.txtSiguiente).setOnClickListener(x -> control.clickSiguiente());
        findViewById(R.id.btnCargarImg).setOnClickListener(x -> abrirSelectorImagen());
        switchEntrega.setOnClickListener(x -> alternarTiempoEntrega());
        lblSwitch.setOnClickListener(x -> {
            alternarTiempoEntrega();
            switchEntrega.setChecked(entregaPronto);
        });

        iniciarRecycler();

        txtProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean mostrarError = s.length() == 0;

                layProducto.setError(mostrarError ? "Tenés que agregar un producto." : "");
                layProducto.setErrorEnabled(mostrarError);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void iniciarRecycler() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        adapter = new ImagenesAdapter(this);
        listaImagenes.setLayoutManager(layoutManager);
        listaImagenes.setAdapter(adapter);
    }

    @SuppressLint("IntentReset")
    private void abrirSelectorImagen() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        @SuppressLint("IntentReset")
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        Intent chooserIntent = Intent.createChooser(intent, "Dónde buscar la imagen?");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, 777);
    }

    private double round(double num) {
        return Math.round(num * 100d) / 100d;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 777 && resultCode == RESULT_OK && null != data) {
                extraerPathImagenes(data);
                mostrarPesoImagenes();
            }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void extraerPathImagenes(Intent data) {
        try{
            int imagenesOmitidas = 0;

            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();

                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();

                    double peso = fileSizeKB(uri);

                    if (peso >= 5000) {
                        imagenesOmitidas++;
                        continue;
                    }
                    if (filesKB + peso >= 5000) {
                        imagenesOmitidas++;
                        continue;
                    }
                    filesKB += peso;
                    adapter.addImagen(uri);
                }
            }

            if (imagenesOmitidas != 0) {
                Toast.makeText(
                        this,
                        "Se han omitido " + imagenesOmitidas + " imágenes por exceder 5 MB en total",
                        Toast.LENGTH_LONG)
                        .show();
            }

            hayImagenes();
        }
        catch(Exception ex) {
            Toast.makeText(this, "Lo sentimos, ocurrió un error :(", Toast.LENGTH_LONG).show();
        }
    }

    private void hayImagenes() {
        listaImagenes.setVisibility(adapter.getItemCount() == 0
                ? View.GONE
                : View.VISIBLE);
    }

    private void alternarTiempoEntrega() {
        entregaPronto = !entregaPronto;
        layEntrega.setVisibility(entregaPronto
                ? View.GONE
                : View.VISIBLE);
        lblSwitch.setText(entregaPronto
                ? "Recibirlo lo antes posible"
                : "Recibirlo en fecha y hora");
    }

    private void mostrarDialogoFecha() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener= (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

                fechaHoraEntrega = calendar;
                dtpEntrega.setText(simpleDateFormat.format(calendar.getTime()));
                layEntrega.setErrorEnabled(false);
            };

            new TimePickerDialog(
                    ProductosActivity.this,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false).
                    show();
        };
        DatePickerDialog dp = new DatePickerDialog(
                ProductosActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dp.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        dp.show();
    }
    private double fileSizeKB(Uri uri) {
        @SuppressLint("Recycle")
        Cursor returnCursor = getContentResolver()
                .query(uri, null, null, null, null);

        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        return returnCursor.getLong(sizeIndex) / 1000d;
    }

    public void imagenEliminada(Uri uri) {
        filesKB -= fileSizeKB(uri);
        if (filesKB < 0) filesKB = 0;
        mostrarPesoImagenes();
        hayImagenes();
    }

    private void mostrarPesoImagenes() {
        String stringSize;

        if (filesKB >= 1000) {
            stringSize = round(filesKB / 1000d) + " MB";
        }
        else if (filesKB == 0) {
            stringSize = "0 KB";
        }
        else {
            stringSize = round(filesKB) + " KB";
        }
        lblPesoImg.setText(stringSize);
    }

    public String getProducto() {
        return Objects.requireNonNull(txtProducto.getText()).toString();
    }

    public List<Uri> getImagenes() {
        return adapter.getImagenes();
    }

    public boolean getRecibirPronto() {
        return switchEntrega.isChecked();
    }

    public Calendar getFechaHoraRecepcion() {
        return fechaHoraEntrega;
    }

    public void setErrores(Pedido.Errores errores) {
        layProducto.setError(errores.producto ? "Ingresá un producto (5 a 250 caracteres)." : "");

        String errorFecha = errores.fechaRequerida
                ? "Ingresá cuando querés que te llevemos el pedido."
                : "";

        errorFecha += errores.fechaPasada
                ? "La fecha que ingresaste ya pasó."
                : "";

        layEntrega.setError(errorFecha);
    }

    public void siguiente() {
        startActivity(new Intent(this, UbicacionActivity.class));
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }
}