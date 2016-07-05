package com.test.deliveryhero.controller.backend;

/**
 * Requests Observer, It applies the observer design pattern
 * Only use it between the API requests calling and the activity
 */
public interface RequestObserver {
    /**
     * Handle the request finished normally
     * @param requestId Request ID
     * @param error Error happened to the request
     * @param resulObject Response result
     */
    public void handleRequestFinished(Object requestId, Throwable error, Object resulObject);

    /**
     * It will be called if the request cancelled
     * @param requestId
     * @param error
     */
    public void requestCanceled(Integer requestId, Throwable error);

    /**
     * Update status of the request, it will be called while doing the request to deliver the status to requester
     * @param requestId
     * @param statusMsg
     */
    public void updateStatus(Integer requestId, String statusMsg);
}
