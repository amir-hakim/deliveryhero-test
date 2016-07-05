package com.test.deliveryhero.controller.backend;

import com.test.deliveryhero.model.Photo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * APIs class
 */
public interface ApiInterface {

    @Headers("Cache-Control: max-age=50000")
    @GET("/photos")
    Call<ArrayList<Photo>> getPhotosList();
}
