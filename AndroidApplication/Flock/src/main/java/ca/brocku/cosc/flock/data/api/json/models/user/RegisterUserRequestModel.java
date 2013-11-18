package ca.brocku.cosc.flock.data.api.json.models.user;

/**
 * Created by chriskellendonk on 11/16/2013.
 */
public class RegisterUserRequestModel extends UserInformationModel {
    public String password;

    public RegisterUserRequestModel(String username, String firstname, String lastname,
                                    String email, String password) {

        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
