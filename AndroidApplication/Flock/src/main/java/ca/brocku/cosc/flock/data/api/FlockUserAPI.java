package ca.brocku.cosc.flock.data.api;

import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.api.json.models.user.RegisterUserRequestModel;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/17/2013
 */
public class FlockUserAPI extends FlockAPI {
    private static final String API_PATH = "/api/users/";

    public static void register(String username, String firstname, String lastname,
                                                  String email, String password, IFlockAPIResponse responseHandler) {

        register(new RegisterUserRequestModel(username, firstname, lastname, email, password), responseHandler);
    }

    public static void register(RegisterUserRequestModel registrationRequest, IFlockAPIResponse<LoginUserResponseModel> responseHandler) {
        FlockAPIConnection.send(API_PATH + "register", registrationRequest, LoginUserResponseModel.class, responseHandler);
    }
}
