package ca.brocku.cosc.flock.data.api.json.models.location;

import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

import java.util.Date;

/**
 * Represents a user's location
 */
public class UserLocationModel extends JsonModelBase {
    public long userID;
    public double latitude, longitude;
    public String username;
    public Date timestamp;
}
