package ca.brocku.cosc.flock.data.api.actions;

import android.location.Location;

import ca.brocku.cosc.flock.data.api.FlockAPIConnection;
import ca.brocku.cosc.flock.data.api.FlockAPIResponseHandler;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.api.json.models.location.SetLocationRequestModel;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/5/2013
 */
public class LocationAPIAction extends FlockAPIAction {
    private static final String API_PATH = "/api/locations/";

    public static void setLocation(SetLocationRequestModel request,
                                   FlockAPIResponseHandler<GenericSuccessModel> response) {
        FlockAPIConnection.send(API_PATH + "set", request, GenericSuccessModel.class, response);
    }

    public static void setLocation(String secret, Location location,
                                   FlockAPIResponseHandler<GenericSuccessModel> response) {
        SetLocationRequestModel req = new SetLocationRequestModel();

        req.secret = secret;
        req.latitude = location.getLatitude();
        req.longitude = location.getLongitude();

        setLocation(req, response);
    }
}
