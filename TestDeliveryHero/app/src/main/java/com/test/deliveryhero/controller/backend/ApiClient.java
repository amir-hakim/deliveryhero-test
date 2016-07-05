package com.test.deliveryhero.controller.backend;

import com.test.deliveryhero.view.base.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * APIs manager
 * It hold the URLs, the headers, and initializing of Retrofit
 */
public class ApiClient {

    public static final boolean IS_PRODUCTION = true;

    // Server
    private static final String REQUESTS_URL_STAGING = "http://jsonplaceholder.typicode.com";
    private static final String REQUESTS_URL_PRODUCTION = "http://jsonplaceholder.typicode.com";
    public static final String REQUESTS_BASE_URL = IS_PRODUCTION ? REQUESTS_URL_PRODUCTION : REQUESTS_URL_STAGING;

    /**
     * Get common application headers
     * @return
     */
    public static HashMap<String, String> getDefaultHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        return headers;
    }

    /*
     ********************************************************
     ************* Initialize Retrofit **********************
     ********************************************************
     */
    private static final long CACHE_SIZE = 20 * 1024 * 1024; // 10 MB
    private static OkHttpClient.Builder clientBuilder;
    static {
        clientBuilder = new OkHttpClient
                .Builder()
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(BaseApplication.getContext().getCacheDir(), CACHE_SIZE)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (BaseApplication.getApplication().isNetworkConnected()) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 50000).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                });
    }


    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if(retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(REQUESTS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();
        }
        return retrofit;
    }
    // End of initializing Retrofit ////////////////////////
}
