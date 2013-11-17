package api.json.models.user;

import api.json.models.JsonModelBase;

/**
 * Query to search for users.
 */
public class SearchUserRequestModel extends JsonModelBase {
    public String usernameQuery;
}
