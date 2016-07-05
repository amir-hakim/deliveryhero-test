package com.test.deliveryhero.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.test.deliveryhero.R;
import com.test.deliveryhero.controller.AlbumManager;
import com.test.deliveryhero.controller.backend.RequestObserver;
import com.test.deliveryhero.model.Photo;
import com.test.deliveryhero.view.adapter.PhotosAdapter;
import com.test.deliveryhero.view.adapter.PhotoViwHolderEvents;
import com.test.deliveryhero.view.components.LoadingDataView;

import java.util.ArrayList;

import com.test.deliveryhero.helper.Logger;
import com.test.deliveryhero.helper.ZoomViewAnimation;
import com.test.deliveryhero.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Album activity, it hold the photos list
 */
public class AlbumActivity extends BaseActivity implements RequestObserver{

    private static final int REQUEST_ID_PHOTOS = 1;

    ArrayList<Photo> photosList = new ArrayList<>(); // Photos List
    PhotosAdapter adapter; // Adapter for the photos list

    @BindView(R.id.album_recycler_view) RecyclerView mRecyclerView; // Photos recycler view
    @BindView(R.id.lLoadingAbum) LoadingDataView lLoadingAbum; // Loading view
    @BindView(R.id.flPhotoDetailsContainerAlbum) View flPhotoDetailsContainerAlbum;
    @BindView(R.id.srlContainerAlbum) SwipeRefreshLayout srlContainerAlbum; // container views
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout; // collapsing toolbar layout
    @BindView(R.id.main_appbar) AppBarLayout mAppBarLayout;


    PhotoDetailsFragment fPhotoDetailsAlbum; // Photo details fragment

    ZoomViewAnimation zoomViewAnimation; // Animation class to zoom in view while opening it


    /*
     * ************************************************************
     * ****** PhotoViwHolder events, clicked and long clicked *****
     * ************************************************************
     */
    PhotoViwHolderEvents photoViewHolderEvents = new PhotoViwHolderEvents() {
        @Override
        public void onPhotoViewClick(Photo photo, View view) {

            fPhotoDetailsAlbum.loadNewPhoto(photo);
            View thumbView = view;
            openPhotoDetailsWithZoom(srlContainerAlbum, thumbView, flPhotoDetailsContainerAlbum);
        }
    };
    // End of RecyclerViewEvents /////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        ButterKnife.bind(this);

        // Handle appbar
        collapsingToolbarLayout.setTitle(getString(R.string.toolbar_title));

        // Initialize views
        fPhotoDetailsAlbum = (PhotoDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fPhotoDetailsAlbum);

        // Initialize ZoomAnimation class
        zoomViewAnimation = new ZoomViewAnimation();

        callPhotosRequest();
        setOnrefreshLayout();
    }

    private void setOnrefreshLayout() {
        srlContainerAlbum.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callPhotosRequest();
            }
        });
    }

    /**
     * Call photos request
     */
    private void callPhotosRequest() {
        handleLoadingView(true);
        AlbumManager.getInstance().callPhotosRequest(REQUEST_ID_PHOTOS, this);
    }

    /**
     * Handle the appearance of loading view
     * @param isShowLoading Show the loading view or not
     */
    private void handleLoadingView(boolean isShowLoading) {
        if(isShowLoading) {
            handleSwipeRefreshLayout(false);
            lLoadingAbum.setVisibility(View.VISIBLE);
            lLoadingAbum.startLoading();
        } else {
            handleSwipeRefreshLayout(true);
            srlContainerAlbum.setRefreshing(false);
            lLoadingAbum.setVisibility(View.GONE);
        }
    }

    /**
     * Handle swiperefresh loading
     * @param isFinishLoading if true then hide the refresh loading
     */
    private void handleSwipeRefreshLayout(boolean isFinishLoading) {
        if(isFinishLoading) {
            srlContainerAlbum.setRefreshing(false);
            srlContainerAlbum.setEnabled(true);
        } else {
            srlContainerAlbum.setEnabled(false);
        }
    }


    /**
     * Set Adapter
     * @param _photosList
     */
    private void setAdapter(ArrayList<Photo> _photosList) {
        this.photosList = _photosList;
        adapter = new PhotosAdapter(this, _photosList);

        handleLoadingView(false);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.setPhotoViewHolderEvents(photoViewHolderEvents);
    }

    /**
     * Open photo details with zoom
     * @param container
     * @param thumbView
     * @param expandedView
     */
    private void openPhotoDetailsWithZoom(View container, final View thumbView, View expandedView) {
//        mAppBarLayout.hide();
        flPhotoDetailsContainerAlbum.setVisibility(View.VISIBLE);
        zoomViewAnimation.openWithZoom(container, thumbView, expandedView);
    }

    /**
     * Close photo details
     */
    private void closePhotoDetailsWithZoom() {
//        mAppBarLayout.show();
        zoomViewAnimation.closeWithZoom();
    }

    /*
     * *********************************************************
     * ****************** Request Observer *********************
     * *********************************************************
     */
    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resulObject) {
        Logger.instance().v("On Respond", "REQUEST_ID_PHOTOS: " + requestId + ", Response: " + resulObject + " -- Error: " + error);

        if (requestId == REQUEST_ID_PHOTOS) {
            if (resulObject != null && resulObject instanceof ArrayList) {
                ArrayList<Photo> photosList = (ArrayList<Photo>) resulObject;
                setAdapter(photosList);
            } else {
                Logger.instance().v("On Error Respond", "Error: " + error.getMessage() + " -- " + error);
                lLoadingAbum.failLoading(getString(R.string.fail_load_photos));
                handleSwipeRefreshLayout(true);
                // TODO get and load from cache
//                CachingManager.getInstance().getSavedPhotosList(); // list cached
            }
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {
    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {
    }
    // End of Request Observer ///////////////////////////

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // If the photo details is open, then close it
            if (flPhotoDetailsContainerAlbum.getVisibility() == View.VISIBLE) {
                if (fPhotoDetailsAlbum.canGoBack()) {
                    closePhotoDetailsWithZoom();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
