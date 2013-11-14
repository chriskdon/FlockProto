package api.json.models.User;

import api.json.models.JsonModelBase;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * Created by chriskellendonk on 11/14/2013.
 */
public class DeleteUserRequestModel extends JsonModelBase {
    private Long id;             // UserID
    private String password;    // Password

    public Long getId() { return id; }
    public void setId(Long value) { id = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }

    public static DeleteUserRequestModel revive(JsonNode json) throws IOException {
        return mapper.readValue(json, DeleteUserRequestModel.class);
    }
}
