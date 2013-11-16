package ca.brocku.cosc.flock.data.api.json.models.user;

import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

/**
 * Query to search for users.
 */
public class SearchUserRequestModel extends JsonModelBase {
    public String usernameQuery;
}
