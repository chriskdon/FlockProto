package api.json.models.user;

import api.json.models.JsonModelBase;

/**
 * Existing user login request
 */
public class LoginUserRequestModel extends JsonModelBase {
    public String username, password;
}
