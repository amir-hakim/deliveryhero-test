package com.test.deliveryhero.controller.backend;

import com.test.deliveryhero.model.Photo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Request Handler for Photos List
 * Doing the call to server and get the photos list
 * <p/>
 * Separating the request in this layer
 * Retrofit is used
 */
public class PhotosRequestHandler {
    Object requestID; // Request ID
    RequestObserver requestObserver; // Observer to deliver the result to UI

    /**
     * Constructor
     *
     * @param requestID       id of the request
     * @param requestObserver observer to deliver the request result
     */
    public PhotosRequestHandler(Object requestID, RequestObserver requestObserver) {
        this.requestID = requestID;
        this.requestObserver = requestObserver;
    }


    /**
     * Execute the request, the request will not start untill calling this method
     */
    public void execute() {
        doGetPhotos();
    }

    /**
     * Get photos from server
     */
    private void doGetPhotos() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<Photo>> call = apiService.getPhotosList();
        call.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                // Get Response from server
                if (requestObserver != null) {
                    HttpError error = null;
                    ArrayList<Photo> responseObject = null;
                    if (response == null)
                        error = new HttpError("", -1);
                    if (response != null) {
                        if (!response.isSuccessful())
                            error = new HttpError(response.errorBody().toString(), response.code());
                        else
                            responseObject = response.body();
                    }
                    requestObserver.handleRequestFinished(requestID, error, responseObject);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

                if (requestObserver != null)
                    requestObserver.handleRequestFinished(requestID, new HttpError((t != null) ? t.getMessage() : "", -1), null);
            }
        });
    }
}
