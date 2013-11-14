package api.json.models.User;

import api.json.models.JsonModelBase;

/**
 * Response to a user being succefful registered.
 */
public class UserRegisteredResponseModel extends JsonModelBase {
    private Long userId = null;

    public long getUserId() { return userId; }
    public void setUserId(Long value) { userId = value; }

    public UserRegisteredResponseModel() {}

    public UserRegisteredResponseModel(Long id) {
        setUserId(id);
    }
}
