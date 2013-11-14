package api.json.models.User;

import api.json.models.JsonModelBase;

/**
 * Response to a user being successfully logged in.
 */
public class LoginUserResponseModel extends JsonModelBase {
    private String secret;

    public String getSecret() { return secret; }
    public void setSecret(String value) { secret = value; }

    public LoginUserResponseModel(String secret) {
        setSecret(secret);
    }
}
