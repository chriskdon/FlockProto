package api.json.models.User;

import api.json.models.JsonModelBase;

/**
 * Response to a user being successfully logged in.
 */
public class LoginUserResponseModel extends JsonModelBase {
    private Long id;

    public Long getId() { return id; }
    public void setId(Long value) { id = value; }

    public LoginUserResponseModel(Long id) {
        setId(id);
    }
}
