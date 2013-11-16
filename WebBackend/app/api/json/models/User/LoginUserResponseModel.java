package api.json.models.user;


import api.json.models.UserActionModel;

/**
 * Response to a user being successfully logged in.
 */
public class LoginUserResponseModel extends UserActionModel {
    public LoginUserResponseModel(String secret) {
        this.secret = secret;
    }
}
