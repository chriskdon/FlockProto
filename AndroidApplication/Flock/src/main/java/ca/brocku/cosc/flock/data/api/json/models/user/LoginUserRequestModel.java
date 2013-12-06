package ca.brocku.cosc.flock.data.api.json.models.user;

import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

/**
 * Existing user login request
 */
public class LoginUserRequestModel extends JsonModelBase {
    public String username, password;
}
