package ca.brocku.cosc.flock.data.api.json.models.user;

import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

/**
 * JSON POJO for Jackson that represents a request to register a new user.
 */

public class UserInformationModel extends JsonModelBase {
    public String username, firstname, lastname, email;

    public UserInformationModel() { }

    public UserInformationModel(String username, String firstname, String lastname, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
