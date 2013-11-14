package controllers.api;

import api.json.models.ErrorResponseModel;
import api.json.models.RegisterUserRequestModel;
import api.json.models.UserRegisteredResponseModel;
import authentication.FlockAuthentication;
import models.User;
import org.apache.commons.codec.binary.Hex;
import play.mvc.*;
import java.util.UUID;
import java.security.*;

public class UserController extends Controller {
    /**
     * Register a new user.
     *
     * @return JSON Response with UserID if successful.
     */
    public static Result register() {
        try {
            RegisterUserRequestModel model = RegisterUserRequestModel.revive(request().body().asJson());

            String salt = FlockAuthentication.getInstance().generateSalt();
            String saltedPassword = FlockAuthentication.getInstance().generateSaltedPassowrd(model.getPassword(), salt);

            User newUser = new User(model.getUsername(), model.getFirstname(), model.getLastname(),
                                    saltedPassword, salt, model.getEmail(),
                                    FlockAuthentication.getInstance().generateSalt());

            newUser.save();

            return ok((new UserRegisteredResponseModel(newUser.id)).toJsonString());
        } catch (Exception ex) {
            return ok((new ErrorResponseModel()).toJsonString());
        }
    }
}
