package ca.brocku.cosc.flock.data.api.actions;

import ca.brocku.cosc.flock.data.api.APIConnection;
import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.api.json.models.user.DeleteUserRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.user.LoginUserResponseModel;
import ca.brocku.cosc.flock.data.api.json.models.user.RegisterUserRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.user.SearchUserRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.user.SearchUserResponseModel;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/17/2013
 */
public class UserAPIAction extends APIAction {
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
                                APIResponseHandler<LoginUserResponseModel> responseHandler) {

        RegisterUserRequestModel request = new RegisterUserRequestModel();

        request.username = username;
        request.firstname = firstname;
        request.lastname = lastname;
        request.email = email;
        request.password = password;

        register(request, responseHandler);
    }

    /**
     * Register a new user.
     *
     * @param registrationRequest
     * @param responseHandler
     */
    public static void register(RegisterUserRequestModel registrationRequest,
                                APIResponseHandler<LoginUserResponseModel> responseHandler) {

        APIConnection.send(API_PATH + "register", registrationRequest,
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
                             APIResponseHandler<LoginUserResponseModel> responseHandler) {

        LoginUserRequestModel request = new LoginUserRequestModel();
        request.username = username;
        request.password = password;

        login(request, responseHandler);
    }

    /**
     * Login an existing user.
     *
     * @param loginRequest
     * @param responseHandler
     */
    public static void login(LoginUserRequestModel loginRequest,
                            APIResponseHandler<LoginUserResponseModel> responseHandler) {
        APIConnection.send(API_PATH + "login", loginRequest,
                LoginUserResponseModel.class, responseHandler);
    }

    /**
     * Delete an existing user.
     * @param deleteRequest
     * @param responseHandler
     */
    public static void delete(DeleteUserRequestModel deleteRequest,
                              APIResponseHandler<GenericSuccessModel> responseHandler) {
        APIConnection.send(API_PATH + "delete", deleteRequest,
                GenericSuccessModel.class, responseHandler);
    }

    /**
     * Delete user.
     * @param secret
     * @param password
     * @param responseHandler
     */
    public static void delete(String secret, String password,
                              APIResponseHandler<GenericSuccessModel> responseHandler) {
        DeleteUserRequestModel req = new DeleteUserRequestModel();
        req.password = password;
        req.secret = secret;

        delete(req, responseHandler);
    }

    /**
     * Search for users by username
     * @param request
     * @param response
     */
    public static void search(SearchUserRequestModel request,
                              APIResponseHandler<SearchUserResponseModel> response) {

        APIConnection.send(API_PATH + "search", request, SearchUserRequestModel.class, response);
    }

    /**
     * Search for users by username
     * @param usernameQuery
     * @param response
     */
    public static void search(String usernameQuery,
                              APIResponseHandler<SearchUserResponseModel> response) {

        SearchUserRequestModel request = new SearchUserRequestModel();

        request.usernameQuery = usernameQuery;

        search(request, response);
    }
}
