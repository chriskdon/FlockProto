package api.json.models.User;

/**
 * Created by chriskellendonk on 11/16/2013.
 */
public class RegisterUserRequestModel extends UserInformationModel {
    String password;

    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }
}
