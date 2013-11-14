package controllers.api;

import api.json.models.*;
import authentication.FlockAuthentication;
import models.User;
import play.mvc.*;

import java.io.IOException;

public class UserController extends Controller {
    private static final FlockAuthentication auth = FlockAuthentication.getInstance();

    /**
     * Register a new user.
     *
     * @return JSON Response with UserID if successful.
     */
    public static Result register() {
        try {
            RegisterUserRequestModel model = RegisterUserRequestModel.revive(request().body().asJson());

            String salt = auth.generateSalt();
            String saltedPassword = auth.generateSaltedPassword(model.getPassword(), salt);

            User newUser = new User(model.getUsername(), model.getFirstname(), model.getLastname(),
                                    saltedPassword, salt, model.getEmail(),
                                    auth.generateSalt());

            // Check if username is already taken
            boolean usernameTaken = (User.find.where().eq("username",model.getUsername()).findRowCount() > 0);
            if(usernameTaken) {
                return ok((new ErrorResponseModel("Username Taken")).toJsonString()); // Error username taken
            } else {
                newUser.save();
            }

            return ok((new UserRegisteredResponseModel(newUser.id)).toJsonString());
        } catch (IOException ex) {
            return ok((new ErrorResponseModel()).toJsonString());
        }
    }

    /**
     * Login an existing user.
     *
     * @return The userid of the user that was logged in for future requests.
     */
    public static Result login() {
        try {
            // Data
            LoginUserRequestModel loginModel = LoginUserRequestModel.revive(request().body().asJson());
            User user = User.find.where().eq("username", loginModel.getUsername()).findUnique();

            if(user != null && auth.checkPassword(user.saltedPassword, loginModel.getPassword(), user.salt)) { // User found now test password.
                return ok((new LoginUserResponseModel(user.id)).toJsonString());
            } else {
                return ok((new ErrorResponseModel("Login Error").toJsonString()));
            }

        } catch(IOException ex) { }

        // If we got this far an error occurred
        return ok((new ErrorResponseModel()).toJsonString());
    }
}
