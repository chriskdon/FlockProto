package ca.brocku.cosc.flock.data.api.actions;

import ca.brocku.cosc.flock.data.api.APIConnection;
import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.api.json.models.gcm.GCMRegistrationRequest;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/7/2013
 */
public class GCMAPIAction {
    private static final String API_PATH = "/api/gcm/";

    /**
     * Register GCM Registration ID
     * @param request
     * @param response
     */
    public static void register(GCMRegistrationRequest request,
                                APIResponseHandler<GenericSuccessModel> response) {

        APIConnection.send(API_PATH + "register", request, GenericSuccessModel.class, response);
    }

    /**
     * Register GCM Registration ID
     * @param secret
     * @param gcmRegistrationID
     * @param response
     */
    public static void register(String secret, String gcmRegistrationID,
                                APIResponseHandler<GenericSuccessModel> response) {

        GCMRegistrationRequest request = new GCMRegistrationRequest();

        request.secret = secret;
        request.gcmRegistrationID = gcmRegistrationID;

        register(request, response);
    }
}
