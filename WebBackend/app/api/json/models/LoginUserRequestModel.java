package api.json.models;

import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * Existing user login request
 */
public class LoginUserRequestModel extends JsonModelBase {
    private String username, password;

    public String getUsername() { return username; }
    public void setUsername(String value) { username = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }

    public static LoginUserRequestModel revive(JsonNode json) throws IOException {
        return mapper.readValue(json, LoginUserRequestModel.class);
    }
}
