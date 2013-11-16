package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

/**
 * Location information for a particular user.
 */
@Entity
@Table(name="Locations")
public class Location extends Model {
    public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);

    @Id

    @Column(name="UserID")
    public Long userID;

    @Column(name="UserSecret")
    public String userSecret;

    @Constraints.Required
    @Column(name="Latitude")
    public long latitude;

    @Constraints.Required
    @Column(name="Longitude")
    public long longitude;

    @Constraints.Required
    @Column(name="Timestamp")
    public Date timestamp;

    public Location() { }

    public Location(long userID, String userSecret,  long latitude, long longitude, Date timestamp) {
        this.userID = userID;
        this.userSecret = userSecret;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }
}
