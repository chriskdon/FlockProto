package ca.brocku.cosc.flock.data.api.json.models.user;

import ca.brocku.cosc.flock.data.api.json.models.UserActionModel;

/**
 * Response to a user being successfully logged in.
 */
public class LoginUserResponseModel extends UserActionModel {
    public LoginUserResponseModel(String secret) {
        this.secret = secret;
    }

    public LoginUserResponseModel() {}
}
