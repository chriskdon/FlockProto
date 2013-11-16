package api.json.models.User;

import api.json.models.JsonModelBase;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * JSON POJO for Jackson that represents a request to register a new user.
 */

public class UserInformationModel extends JsonModelBase {
    private String username, firstname, lastname, email;

    public String getUsername() { return username; }
    public void setUsername(String value) { username = value; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String value) { firstname = value; }

    public String getLastname() { return lastname; }
    public void setLastname(String value) { lastname = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }

    public UserInformationModel() { }

    public UserInformationModel(String username, String firstname, String lastname, String email) {
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
    }
}
