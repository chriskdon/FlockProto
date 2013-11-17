package ca.brocku.cosc.flock.data.api.json.models.user;

import ca.brocku.cosc.flock.data.api.json.models.UserActionModel;

/**
 * Delete a user.
 */
public class DeleteUserRequestModel extends UserActionModel {
    public String password;
}
