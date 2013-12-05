package ca.brocku.cosc.flock.data.api.actions;

import ca.brocku.cosc.flock.data.api.FlockAPIConnection;
import ca.brocku.cosc.flock.data.api.FlockAPIResponseHandler;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.api.json.models.user.DeleteUserRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.api.json.models.user.RegisterUserRequestModel;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/17/2013
 */
public class FlockUserAPIAction extends FlockAPIAction {
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
                                FlockAPIResponseHandler<LoginUserResponseModel> responseHandler) {

        register(new RegisterUserRequestModel(username, firstname, lastname, email, password), responseHandler);
    }

    /**
     * Register a new user.
     *
     * @param registrationRequest
     * @param responseHandler
     */
    public static void register(RegisterUserRequestModel registrationRequest,
                                FlockAPIResponseHandler<LoginUserResponseModel> responseHandler) {

        FlockAPIConnection.send(API_PATH + "register", registrationRequest,
                                LoginUserResponseModel.class, responseHandler);
    }

    /**
     * Login an existing user.
     *
     * @param username
     * @param password
     * @param responseHandler
     */
    public static void login(String username, String password,
                             FlockAPIResponseHandler<LoginUserResponseModel> responseHandler) {

        login(new LoginUserRequestModel(username, password), responseHandler);
    }

    /**
     * Login an existing user.
     *
     * @param loginRequest
     * @param responseHandler
     */
    public static void login(LoginUserRequestModel loginRequest,
                            FlockAPIResponseHandler<LoginUserResponseModel> responseHandler) {
        FlockAPIConnection.send(API_PATH + "login", loginRequest,
                                LoginUserResponseModel.class, responseHandler);
    }

    /**
     * Delete an existing user.
     * @param deleteRequest
     * @param responseHandler
     */
    public static void delete(DeleteUserRequestModel deleteRequest,
                              FlockAPIResponseHandler<GenericSuccessModel> responseHandler) {
        FlockAPIConnection.send(API_PATH + "delete", deleteRequest,
                GenericSuccessModel.class, responseHandler);
    }

    public static void delete(String secret, String password,
                              FlockAPIResponseHandler<GenericSuccessModel> responseHandler) {
        DeleteUserRequestModel req = new DeleteUserRequestModel();
        req.password = password;
        req.secret = secret;

        delete(req, responseHandler);
    }
}
