package com.test.deliveryhero.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.deliveryhero.R;
import com.test.deliveryhero.controller.ImageLoader;
import com.test.deliveryhero.model.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for photos list
 */
public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<Photo> photosList; // Photos list
    PhotoViwHolderEvents photoViewHolderEvents; // RecyclerView Events (Clicks)
    Context context;

    /**
     * Constructor
     * @param context
     * @param photosList
     */
    public PhotosAdapter(Context context, ArrayList<Photo> photosList) {
        this.photosList = photosList;
        this.context = context;
    }

    // Create new views
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _holder, final int position) {
        ViewHolder holder = (ViewHolder) _holder;
        holder.bindData(photosList.get(position), photoViewHolderEvents);
    }

    /**
     * Get items Count
     * @return
     */
    @Override
    public int getItemCount() {
        return (photosList != null) ? photosList.size() : 0;
    }

    /**
     * Set recyclerview events observer
     * @param photoViewHolderEvents
     */
    public void setPhotoViewHolderEvents(PhotoViwHolderEvents photoViewHolderEvents) {
        this.photoViewHolderEvents = photoViewHolderEvents;
    }

    /**
     * Remove recyclerview events observer
     */
    public void removephotoViewHolderEvents() {
        setPhotoViewHolderEvents(null);
    }

    /*
     * ************************************************
     * ************ View Holder for the item **********
     * ************************************************
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public @BindView(R.id.tvTitlePhotosItem) TextView tvTitlePhotosItem;
        public @BindView(R.id.ivThumbPhotosItem) ImageView ivThumbPhotosItem;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(final Photo photo, final PhotoViwHolderEvents photoViewHolderEvents) {
            // Handle View
            tvTitlePhotosItem.setText(photo.title);
            ImageLoader.loadImage(itemView.getContext(), photo.thumbnailUrl, R.drawable.placeholder, ivThumbPhotosItem, true);

            // Handle Click on item
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(photoViewHolderEvents != null)
                        photoViewHolderEvents.onPhotoViewClick(photo, v);
                }
            });
        }
    }
    // End of View Holder ///////////////////////////
}
