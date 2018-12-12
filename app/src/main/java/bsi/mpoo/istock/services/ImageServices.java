package bsi.mpoo.istock.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageServices {

    public ImageServices(){}

    public byte[] imageToByte(Bitmap imageBitmap){

        byte[] imageByte = null;

        if (imageBitmap != null){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,70,out);
            imageByte = out.toByteArray();
        }
        return imageByte;
    }

    public Bitmap byteToImage(byte[] imageByte){

        Bitmap imageBitmap = null;

        if (imageByte != null){
            imageBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        return imageBitmap;

    }

    public byte[] reduceBitmap(byte[] imageByte){
        byte[] resultImageByte = null;

        if (imageByte != null){

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);

            final int REQUIRED_SIZE = 100;

            int scale = 1;

            while (options.outWidth/scale/2 >= REQUIRED_SIZE &&
                    options.outHeight/scale/2 >= REQUIRED_SIZE){
                scale *= 2;
            }

            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale;

            Bitmap resultBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length,options2);

            resultImageByte = imageToByte(resultBitmap);

        }
        return resultImageByte;
    }


}
