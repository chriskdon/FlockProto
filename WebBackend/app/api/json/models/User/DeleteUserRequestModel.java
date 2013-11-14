package api.json.models.User;

import api.json.models.JsonModelBase;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * Created by chriskellendonk on 11/14/2013.
 */
public class DeleteUserRequestModel extends JsonModelBase {
    private String userHash;    // hash
    private String password;    // Password

    public String getUserHash() { return userHash; }
    public void setUserHash(String value) { userHash = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }

    public static DeleteUserRequestModel revive(JsonNode json) throws IOException {
        return mapper.readValue(json, DeleteUserRequestModel.class);
    }
}
