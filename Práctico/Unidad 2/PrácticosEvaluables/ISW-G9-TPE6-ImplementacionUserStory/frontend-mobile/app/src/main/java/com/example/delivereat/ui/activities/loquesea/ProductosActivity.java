package com.example.delivereat.ui.activities.loquesea;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.control.ProductosControl;
import com.example.delivereat.model.Imagen;
import com.example.delivereat.model.ImagenFactory;
import com.example.delivereat.model.Pedido;
import com.example.delivereat.model.Producto;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.example.delivereat.ui.abstracts.ObservadorTexto;
import com.example.delivereat.ui.adapters.ImagenesAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class ProductosActivity extends BaseActivity {

    private TextInputLayout layProducto;
    private RecyclerView listaImagenes;
    private ImagenesAdapter adapter;
    private long filesKB;
    private TextView lblPesoImg;

    private ProductosControl control;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_productos;
    }

    @Override
    protected IControl getControl() {
        control = new ProductosControl(this);
        return control;
    }

    @Override
    protected void iniciarViews() {
        listaImagenes = findViewById(R.id.listaImagenes);
        lblPesoImg = findViewById(R.id.lblPesoImg);
        layProducto = findViewById(R.id.txtLayProducto);
        TextInputEditText txtProducto = findViewById(R.id.txtProducto);

        findViewById(R.id.txtSiguiente).setOnClickListener(x -> control.clickSiguiente());

        iniciarRecycler();

        txtProducto.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layProducto;
            }
        });

        findViewById(R.id.btnCargarImg).setOnClickListener(x -> abrirSelectorImagen());
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && resultCode == RESULT_OK && null != data) {
                extraerPathImagenes(data);
                mostrarPesoImagenes();
            }

    }

    private void extraerPathImagenes(Intent data) {
        try{
            int imagenesOmitidas = 0;

            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();

                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);

                    Imagen imagen = ImagenFactory.fabricar(item.getUri(), getContentResolver());
                    if (imagen == null) continue;

                    if (imagen.getSize() >= 5000) {
                        imagenesOmitidas++;
                        continue;
                    }
                    if (filesKB + imagen.getSize() >= 5000) {
                        imagenesOmitidas++;
                        continue;
                    }
                    filesKB += imagen.getSize();
                    adapter.addImagen(imagen);
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

    public void imagenEliminada(Imagen imagen) {
        filesKB -= imagen.getSize();
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
        return getTxtString(R.id.txtProducto);
    }

    public List<Imagen> getImagenes() {
        return adapter.getImagenes();
    }

    public void setErrores(Producto.Errores errores) {
        layProducto.setError(errores.eRequerido() ? "Ingresá un producto (5 a 280 caracteres)." : "");
    }

    public void siguiente() {
        navegar(UbicacionActivity.class);
    }

    public void setProducto(String producto) {
        setTxtString(R.id.txtProducto, producto);
    }

    public void setImagenes(List<Imagen> imagenes) {
        if (imagenes == null) return;
        filesKB = 0;
        iniciarRecycler();
        for (Imagen img: imagenes) {
            adapter.addImagen(img);
            filesKB += img.getSize();
        }
        mostrarPesoImagenes();
        hayImagenes();
    }
}