package com.test.deliveryhero.view.adapter;

import android.view.View;

import com.test.deliveryhero.model.Photo;

/**
 * Used to deliver the single click on photo item
 */
public interface PhotoViwHolderEvents {
    public void onPhotoViewClick(Photo photo, View view);
}
