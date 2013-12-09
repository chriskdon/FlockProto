package api.json.models.location;

import api.json.models.JsonModelBase;
import models.Location;

import java.util.Date;

/**
 * Represents a user's location
 */
public class UserLocationModel extends JsonModelBase {
    public long userID;
    public double latitude, longitude;
    public String username;
    public Date timestamp;

    public UserLocationModel() { }

    public UserLocationModel(String username, Location location) {
        this.username = username;
        this.userID = location.userID;
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.timestamp = location.timestamp;
    }
}
