package com.test.deliveryhero.controller;

import com.test.deliveryhero.controller.backend.PhotosRequestHandler;
import com.test.deliveryhero.controller.backend.RequestObserver;

/**
 * Album manager is used to call album's photos api, store photos list and photos caching
 */
public class AlbumManager {

    private static AlbumManager _instance;
    public static AlbumManager getInstance() {
        if(_instance == null)
            _instance = new AlbumManager();
        return _instance;
    }

    /**
     * Call photos api
     * @param requestID
     * @param requestObserver
     */
    public void callPhotosRequest(Object requestID, RequestObserver requestObserver) {
        new PhotosRequestHandler(requestID, requestObserver).execute();
    }
}
