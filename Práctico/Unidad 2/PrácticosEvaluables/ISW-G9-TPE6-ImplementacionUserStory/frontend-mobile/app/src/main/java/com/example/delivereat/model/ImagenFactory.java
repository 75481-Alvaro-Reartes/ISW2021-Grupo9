package com.example.delivereat.model;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;

import java.io.FileDescriptor;

public class ImagenFactory {

    public static Imagen fabricar(Uri uri, ContentResolver cr){
        try {
            ParcelFileDescriptor parcelFileDescriptor = cr.openFileDescriptor(uri, "r");
            assert parcelFileDescriptor != null;
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();

            return new Imagen(
                    image,
                    getSizeArchivo(uri, cr)
            );
        }
        catch (Exception ignore) {
            return null;
        }
    }

    private static double getSizeArchivo(Uri uri, ContentResolver cr) {

        @SuppressLint("Recycle")
        Cursor returnCursor = cr.query(uri, null, null, null, null);

        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        return returnCursor.getLong(sizeIndex) / 1000d;
    }
}
