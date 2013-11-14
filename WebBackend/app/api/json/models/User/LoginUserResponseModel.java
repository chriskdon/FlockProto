package api.json.models.User;

import api.json.models.JsonModelBase;

/**
 * Response to a user being successfully logged in.
 */
public class LoginUserResponseModel extends JsonModelBase {
    private String userHash;

    public String getUserHash() { return userHash; }
    public void setUserHash(String value) { userHash = value; }

    public LoginUserResponseModel(String userHash) {
        setUserHash(userHash);
    }
}
