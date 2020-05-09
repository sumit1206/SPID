package com.example.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ParseImage {
    ImageView imageView;

    public ParseImage(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setImageString(String image){
        String imageString = image;
        byte[] decodedString;
        decodedString = Base64.decode(imageString, Base64.DEFAULT);
        imageView.setImageBitmap( BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
    }

    public String getImageString(){
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

}
