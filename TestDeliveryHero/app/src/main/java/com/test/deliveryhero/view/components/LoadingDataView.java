package com.test.deliveryhero.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.deliveryhero.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Loading data layout
 * It will be displayed while loading data
 * If loading is failed, then the loading will disappear, and pass a message to be written on the failure textview
 * <p/>
 * Make the loading is centeralized in this view, to make it the same for all the application screen.
 */
public class LoadingDataView extends FrameLayout {

    @BindView(R.id.pbLoadingData) ProgressBar pbLoadingData; // Loading indicator
    @BindView(R.id.tvLoadingData) TextView tvLoadingData; // textview to show a message, or used when fail

    /*
     * Constructor
     */
    public LoadingDataView(Context context) {
        this(context, null);
    }

    /*
     * Constructor
     */
    public LoadingDataView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    /*
     * Initialize the loading view
     */
    private void init() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_loading_data, null);
        ButterKnife.bind(this, view);

        removeAllViews();
        addView(view);
    }

    /*
     * Start loading, show the progress cycle
     */
    public void startLoading() {
        pbLoadingData.setVisibility(VISIBLE);
        tvLoadingData.setVisibility(GONE);
    }

    /*
     * Fail to load, show the error happen while loading
     */
    public void failLoading(String error) {
        pbLoadingData.setVisibility(GONE);
        tvLoadingData.setVisibility(VISIBLE);
        tvLoadingData.setText(error);
    }
}
