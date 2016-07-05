package com.test.deliveryhero.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.test.deliveryhero.R;
import com.test.deliveryhero.controller.ImageLoader;
import com.test.deliveryhero.model.Photo;
import com.test.deliveryhero.view.components.ImageViewZoom;

import com.test.deliveryhero.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Photo details fragment
 * Show the photo and enable zooming it
 */
public class PhotoDetailsFragment extends BaseFragment {

    private static final String BUNDLE_KEY_PHOTO = "BundleKeyPhoto"; // Pass the photo to fragment with this key

    private Photo photo; // Photo object

    private View view; // View Container
    @BindView(R.id.ivPhotoDetails) ImageViewZoom ivPhotoDetails; //ImageView to hold the image
    private Unbinder unbinder;
    /**
     * Initialize new instance from PhotoDetails fragment
     * @param photo
     * @return
     */
    public static PhotoDetailsFragment newInstance(Photo photo) {
        PhotoDetailsFragment photoDetailsFragment = new PhotoDetailsFragment();

        // Put Photo in a bundle
        Bundle arguments = new Bundle();
        arguments.putParcelable(BUNDLE_KEY_PHOTO, photo);

        photoDetailsFragment.setArguments(arguments);
        return photoDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewParent oldParent = view.getParent();
            if (oldParent != container) {
                ((ViewGroup) oldParent).removeView(view);
            }
            return view;
        } else {
            // Initialize the view
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_photo_details, null);
            unbinder = ButterKnife.bind(this, view);

            Bundle bundle = getArguments();
            if(bundle != null)
                photo = (Photo) bundle.getParcelable(BUNDLE_KEY_PHOTO);

            ivPhotoDetails.setMaxZoom(4f);
            // Load photo
            loadNewPhoto(photo);
            return view;
        }
    }

    /**
     * Load new photo in the imageview
     * @param photo to display it
     */
    public void loadNewPhoto(Photo photo) {
        this.photo  = photo;
        if(photo != null) {
            ivPhotoDetails.resetZoom();
            ImageLoader.loadImage(getActivity(), photo.url, R.drawable.placeholder, ivPhotoDetails, false);
        }
    }

    /**
     * Can go back from the fragment
     * @return true if nothing to do before going back, false if there's function to be done before going back, So if false, don't go back and wait.
     */
    public boolean canGoBack() {
        // Reset zoom before go back
        if(ivPhotoDetails.isZoomed()) {
            ivPhotoDetails.resetZoom();
            return false;
        }
        return true;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null) unbinder.unbind();
    }
}
