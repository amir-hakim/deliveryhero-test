package com.test.deliveryhero.controller;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

/**
 * Use this class to make the image loading centeralized in one place
 * We're using Glide here to load images, but if it changed to another library, just change the methods here.
 */
public class ImageLoader {
    /**
     * Lazy loading the image
     * @param context Context to initialize Glide
     * @param url of the image
     * @param placeHolderResID Resource id of placeholder, to display untill the image is loading
     * @param imageView cotainer to load the image in
     * @param isCenterCrop apply center crop or not
     */
    public static void loadImage(Context context, String url, int placeHolderResID, ImageView imageView, boolean isCenterCrop) {
        DrawableRequestBuilder drawableRequestBuilder = Glide.with(context)
                .load(url)
                .placeholder(placeHolderResID)
                .crossFade();
        if (isCenterCrop)
            drawableRequestBuilder.centerCrop();
        drawableRequestBuilder.into(imageView);
    }
}
