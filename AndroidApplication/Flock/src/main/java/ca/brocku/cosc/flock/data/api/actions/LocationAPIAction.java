package ca.brocku.cosc.flock.data.api.actions;

import android.location.Location;

import ca.brocku.cosc.flock.data.api.APIConnection;
import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.api.json.models.UserActionModel;
import ca.brocku.cosc.flock.data.api.json.models.location.FriendLocationRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.location.SetLocationRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.location.UserLocationModel;

/**
 * Location related web server calls.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/5/2013
 */
public class LocationAPIAction extends APIAction {
    private static final String API_PATH = "/api/locations/";

    public static void setLocation(SetLocationRequestModel request,
                                   APIResponseHandler<GenericSuccessModel> response) {
        APIConnection.send(API_PATH + "set", request, GenericSuccessModel.class, response);
    }

    /**
     * Set the current location of the user.
     *
     * @param secret
     * @param location
     * @param response
     */
    public static void setLocation(String secret, Location location,
                                   APIResponseHandler<GenericSuccessModel> response) {
        SetLocationRequestModel req = new SetLocationRequestModel();

        req.secret = secret;
        req.latitude = location.getLatitude();
        req.longitude = location.getLongitude();

        setLocation(req, response);
    }

    /**
     * Hide the users location. I.e. remove location from server
     *
     * @param request
     * @param response
     */
    public static void hide(UserActionModel request,
                            APIResponseHandler<UserActionModel> response) {

        APIConnection.send(API_PATH + "hide", request, UserActionModel.class, response);
    }

    /**
     * Hide the users location. I.e. remove location from server
     * @param secret
     * @param response
     */
    public static void hide(String secret, APIResponseHandler<UserActionModel> response) {
        UserActionModel req = new UserActionModel();
        req.secret = secret;
        hide(req, response);
    }

    /**
     * Get a friends location.
     * @param request
     * @param response
     */
    public static void get(FriendLocationRequestModel request,
                           APIResponseHandler<UserLocationModel> response) {
        APIConnection.send(API_PATH + "get", request, UserLocationModel.class, response);
    }

    /**
     * Get a friends location
     * @param secret
     * @param friendUserID
     * @param response
     */
    public static void get(String secret, int friendUserID,
                           APIResponseHandler<UserLocationModel> response) {
        FriendLocationRequestModel request = new FriendLocationRequestModel();

        request.secret = secret;
        request.friendUserID = friendUserID;

        get(request, response);
    }

    /**
     * Return all available locations of friends.
     *
     * @param request
     * @param response
     */
    public static void friendLocations(UserActionModel request,
                                       APIResponseHandler<UserActionModel> response) {
        APIConnection.send(API_PATH + "friends", request, UserActionModel.class, response);
    }

    /**
     * Return all available locations of friends.
     *
     * @param secret
     * @param response
     */
    public static void friendLocations(String secret,
                                       APIResponseHandler<UserActionModel> response) {
        UserActionModel request = new UserActionModel();
        request.secret = secret;
        friendLocations(request, response);
    }

}
