package api.json.models.User;

import api.json.models.JsonModelBase;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * Created by chriskellendonk on 11/14/2013.
 */
public class DeleteUserRequestModel extends JsonModelBase {
    private String secret;
    private String password;    // Password

    public String getSecret() { return secret; }
    public void setSecret(String value) { secret = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }
}
