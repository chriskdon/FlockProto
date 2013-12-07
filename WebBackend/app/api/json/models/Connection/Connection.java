package api.json.models.connection;

import api.json.models.JsonModelBase;

/**
 * Created by Chris Kellendonk
 * Student #: 4810800
 * Date: 12/6/2013.
 */
public class Connection extends JsonModelBase {
    public Long friendUserID;
    public String username;
    public String firstname;
    public String lastname;

    public Connection() {}

    public Connection(long friendUserID, String username, String firstname, String lastname) {
        this.friendUserID = friendUserID;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
