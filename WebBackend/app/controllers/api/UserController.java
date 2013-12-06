package controllers.api;

import api.json.models.*;
import api.json.models.user.*;
import models.User;
import play.mvc.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController extends ApiControllerBase {

    /**
     * Register a new user.
     *
     * @return JSON Response with UserID if successful.
     */
    public static Result register() {
        try {
            RegisterUserRequestModel model = mapper.readValue(request().body().asJson(), RegisterUserRequestModel.class);

            String salt = auth.generateSalt();
            String saltedPassword = auth.generateSaltedPassword(model.password, salt);

            User newUser = new User(model.username, model.firstname, model.lastname,
                                    saltedPassword, salt, model.email,
                                    auth.generateSalt());

            // Check if username is already taken
            boolean usernameTaken = (User.find.where().eq("username",model.username).findRowCount() > 0);
            if(usernameTaken) {
                return ok((new GenericErrorModel("Username Taken")).toJsonString()); // Error username taken
            } else {
                newUser.save();
            }

            return ok((new LoginUserResponseModel(newUser.secret)).toJsonString());
        } catch (IOException ex) {
            return ok((new GenericErrorModel()).toJsonString());
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
            LoginUserRequestModel loginModel = mapper.readValue(request().body().asJson(), LoginUserRequestModel.class);
            User user = User.find.where().eq("username", loginModel.username).findUnique();

            if(user != null && auth.checkPassword(user, loginModel.password)) { // user found now test password.
                return ok((new LoginUserResponseModel(user.secret)).toJsonString());
            } else {
                return ok((new GenericErrorModel("Login Error").toJsonString()));
            }

        } catch(IOException ex) { }

        // If we got this far an error occurred
        return ok((new GenericErrorModel()).toJsonString());
    }

    /**
     * Delete a user.
     *
     * @return
     */
    public static Result delete() {
        try {
            DeleteUserRequestModel deleteModel = mapper.readValue(request().body().asJson(), DeleteUserRequestModel.class);
            User user = User.findBySecret(deleteModel.secret);

            if(user != null && auth.checkPassword(user, deleteModel.password)) {
                user.delete();
                return ok((new GenericSuccessModel("User Deleted")).toJsonString());
            } else {
                return ok((new GenericErrorModel("Couldn't Delete user")).toJsonString());
            }
        } catch(IOException ex) { }

        return ok((new GenericErrorModel()).toJsonString());
    }

    /**
     * Search for users by username.
     *
     * @return JSON List matching the username.
     */
    public static Result search() {
        try {
            SearchUserRequestModel search = mapper.readValue(request().body().asJson(), SearchUserRequestModel.class);

            List<User> users = User.queryByUsername(search.usernameQuery);

            ArrayList<SearchUserResponseModel.UserSearchResult> resultList =
                    new ArrayList<SearchUserResponseModel.UserSearchResult>(users.size());

            for(User u : users) {
                resultList.add(new SearchUserResponseModel.UserSearchResult(u.username, u.id));
            }

            SearchUserResponseModel response = new SearchUserResponseModel();
            response.userList = resultList;

            return ok(response.toJsonString());
        } catch(Exception ex) {
            return ok((new GenericErrorModel(ex.getMessage())).toJsonString());
        }
    }
}
