package api.json.models.User;

import api.json.models.JsonModelBase;

import java.util.List;

/**
 * Created by chriskellendonk on 11/16/2013.
 */
public class SearchUserResponseModel extends JsonModelBase {
    private List<UserSearchResult> userList;

    public List<UserSearchResult> getUserList() { return userList; }
    public void setUserList(List<UserSearchResult> value) { userList = value; }

    public static class UserSearchResult {
        private String username;
        private long userID;

        public String getUsername() { return username; }
        public void setUsername(String value) { username = value; }

        public long getUserID() { return userID; }
        public void setUserID(long value) { userID = value; }

        public UserSearchResult() { }

        public UserSearchResult(String username, long userID) {
            setUsername(username);
            setUserID(userID);
        }
    }
}
