package com.FarmPe.Farmer.Bean;

import android.graphics.Bitmap;

public class AddPhotoBean {

    Bitmap image_upload;


    public AddPhotoBean(Bitmap image_upload) {

        this.image_upload = image_upload;

    }

    public Bitmap getImage_upload() {
        return image_upload;
    }
}


