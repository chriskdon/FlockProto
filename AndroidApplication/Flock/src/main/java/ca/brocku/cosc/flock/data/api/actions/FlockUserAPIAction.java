package ca.brocku.cosc.flock.data.api.actions;

import ca.brocku.cosc.flock.data.api.FlockAPI;
import ca.brocku.cosc.flock.data.api.FlockAPIConnection;
import ca.brocku.cosc.flock.data.api.IFlockAPIResponse;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.api.json.models.user.RegisterUserRequestModel;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/17/2013
 */
public class FlockUserAPIAction extends FlockAPI {
    private static final String API_PATH = "/api/users/";

    /**
     * Register a new user.
     *
     * @param username
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param responseHandler
     */
    public static void register(String username, String firstname, String lastname,
                                                  String email, String password,
                                                  IFlockAPIResponse responseHandler) {

        register(new RegisterUserRequestModel(username, firstname, lastname, email, password), responseHandler);
    }

    /**
     * Register a new user.
     *
     * @param registrationRequest
     * @param responseHandler
     */
    public static void register(RegisterUserRequestModel registrationRequest,
                                IFlockAPIResponse<LoginUserResponseModel> responseHandler) {

        FlockAPIConnection.send(API_PATH + "register", registrationRequest, LoginUserResponseModel.class, responseHandler);
    }
}
