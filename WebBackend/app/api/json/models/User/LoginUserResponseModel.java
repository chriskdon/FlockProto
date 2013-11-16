package api.json.models.user;

import api.json.models.JsonModelBase;

/**
 * Response to a user being successfully logged in.
 */
public class LoginUserResponseModel extends JsonModelBase {
    public String secret;

    public LoginUserResponseModel(String secret) {
        this.secret = secret;
    }
}
