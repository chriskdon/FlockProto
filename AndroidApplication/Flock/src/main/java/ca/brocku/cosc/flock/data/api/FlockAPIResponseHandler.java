package ca.brocku.cosc.flock.data.api;

import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

/**
 * Handles the response from a network request.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/17/2013
 */
public abstract class FlockAPIResponseHandler<TResult extends JsonModelBase> {
    /**
     * Fires when a response has completed.
     *
     * @param result The result data from the server.
     */
    public abstract void onResponse(TResult result);

    /**
     * Fires when there is an error with the API request.
     *
     * @param result
     */
    public void onError(ErrorModel result) {
        // Do nothing -- can be overridden
    }
}
