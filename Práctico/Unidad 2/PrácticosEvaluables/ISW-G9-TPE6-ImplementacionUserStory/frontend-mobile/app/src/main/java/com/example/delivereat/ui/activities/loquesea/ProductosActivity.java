package com.example.delivereat.ui.activities.loquesea;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.control.ProductosControl;
import com.example.delivereat.model.otros.Imagen;
import com.example.delivereat.model.otros.ImagenFactory;
import com.example.delivereat.model.pedidos.Producto;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.example.delivereat.util.Constantes;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ProductosActivity extends BaseActivity {

    private TextInputLayout layProducto;
    private double filesKB;
    private TextView lblPesoImg;
    private Imagen imagen;
    private ImageView imgProducto;
    private ConstraintLayout layoutImagen;

    private ProductosControl control;

    ActivityResultLauncher<String> mGetContent;

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
        imgProducto = findViewById(R.id.imgProducto);
        lblPesoImg = findViewById(R.id.lblPesoImg);
        layProducto = findViewById(R.id.txtLayProducto);
        TextInputEditText txtProducto = findViewById(R.id.txtProducto);
        layoutImagen = findViewById(R.id.layoutImgProducto);

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                this::mostrarImagen);

        findViewById(R.id.txtSiguiente).setOnClickListener(x -> control.clickSiguiente());

        txtProducto.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return layProducto;
            }
        });

        findViewById(R.id.btnCargarImg).setOnClickListener(x -> abrirSelectorImagen());
        findViewById(R.id.btnBorrarImg).setOnClickListener(x -> borrarImagen());
    }

    private void borrarImagen() {
        imgProducto.setImageBitmap(null);
        imagen = null;
        lblPesoImg.setText("0KB");
        layoutImagen.setVisibility(View.GONE);
    }

    public void mostrarImagen(Uri uri) {
        imagen = ImagenFactory.fabricar(uri, getContentResolver());

        mostrarImagen(imagen);
    }

    public void mostrarImagen(Imagen imagen) {
        if (imagen == null) return;

        if (imagen.getSize() > Constantes.MAYOR_PESO_IMAGEN_KB) {
            toast("La imágen no puede exceder los 5MB.");
            return;
        }

        imgProducto.setImageBitmap(imagen.getBm());
        lblPesoImg.setText(imagen.getFormatedSize());
        layoutImagen.setVisibility(View.VISIBLE);
    }

    @SuppressLint("IntentReset")
    private void abrirSelectorImagen() {
        mGetContent.launch("image/*");
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//
//        @SuppressLint("IntentReset")
//        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickIntent.setType("image/*");
//        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//
//        Intent chooserIntent = Intent.createChooser(intent, "Dónde buscar la imagen?");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
//
//        startActivityForResult(chooserIntent, 777);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 777 && resultCode == RESULT_OK && null != data) {
//                extraerPathImagenes(data);
//                mostrarPesoImagenes();
//            }
//
//    }

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
                    filesKB = imagen.getSize();
                    //adapter.addImagen(imagen);
                }
            }

            if (imagenesOmitidas != 0) {
                Toast.makeText(
                        this,
                        "Se han omitido " + imagenesOmitidas + " imágenes por exceder 5 MB en total",
                        Toast.LENGTH_LONG)
                        .show();
            }

            //hayImagenes();
        }
        catch(Exception ex) {
            Toast.makeText(this, "Lo sentimos, ocurrió un error :(", Toast.LENGTH_LONG).show();
        }
    }

//    private void hayImagenes() {
//        listaImagenes.setVisibility(adapter.getItemCount() == 0
//                ? View.GONE
//                : View.VISIBLE);
//    }

//    public void imagenEliminada(Imagen imagen) {
//        filesKB -= imagen.getSize();
//        if (filesKB < 0) filesKB = 0;
//        mostrarPesoImagenes();
//        hayImagenes();
//    }

//    private void mostrarPesoImagenes() {
//        String stringSize;
//
//        if (filesKB >= 1000) {
//            stringSize = round(filesKB / 1000d) + " MB";
//        }
//        else if (filesKB == 0) {
//            stringSize = "0 KB";
//        }
//        else {
//            stringSize = round(filesKB) + " KB";
//        }
//        lblPesoImg.setText(stringSize);
//    }

    public String getProducto() {
        return getTxtString(R.id.txtProducto);
    }

    //public List<Imagen> getImagenes() {
    //    return adapter.getImagenes();
    //}

    public void setErrores(Producto.Errores errores) {
        layProducto.setError(errores.eRequerido() ? "Ingresá un producto (5 a 280 caracteres)." : "");
    }

    public void siguiente() {
        navegar(UbicacionActivity.class);
    }

    public void setProducto(String producto) {
        setTxtString(R.id.txtProducto, producto);
    }

    public Imagen getImagen() {
        return imagen;
    }

//    public void setImagenes(List<Imagen> imagenes) {
//        if (imagenes == null) return;
//        filesKB = 0;
//        iniciarRecycler();
//        for (Imagen img: imagenes) {
//            adapter.addImagen(img);
//            filesKB += img.getSize();
//        }
//        mostrarPesoImagenes();
//        hayImagenes();
//    }
}