package api.json.models.User;

import api.json.models.JsonModelBase;

/**
 * Query to search for users.
 */
public class SearchUserRequestModel extends JsonModelBase {
    private String usernameQuery;

    public String getUsernameQuery() { return usernameQuery; }
    public void setUsernameQuery(String value) { usernameQuery = value; }
}
