package api.json.models.user;

import api.json.models.JsonModelBase;

import java.util.List;

/**
 * Created by chriskellendonk on 11/16/2013.
 */
public class SearchUserResponseModel extends JsonModelBase {
    public List<UserSearchResult> userList;

    public static class UserSearchResult {
        public String username;
        public long userID;

        public UserSearchResult() { }

        public UserSearchResult(String username, long userID) {
            this.username = username;
            this.userID = userID;
        }
    }
}
