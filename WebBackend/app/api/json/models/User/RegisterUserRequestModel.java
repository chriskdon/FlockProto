package api.json.models.User;

import api.json.models.JsonModelBase;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * JSON POJO for Jackson that represents a request to register a new user.
 */

public class RegisterUserRequestModel extends JsonModelBase {
    // JSON FIELDS
    private String username, firstname, lastname, password, email;

    public String getUsername() { return username; }
    public void setUsername(String value) { username = value; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String value) { firstname = value; }

    public String getLastname() { return lastname; }
    public void setLastname(String value) { lastname = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    // =========

    public static RegisterUserRequestModel revive(JsonNode json) throws IOException {
        return mapper.readValue(json, RegisterUserRequestModel.class);
    }
}
