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
    public static Finder<Long,Location> find = new Finder<Long,Location>(Long.class, Location.class);

    @Id
    @Column(name="UserID")
    public Long userID;

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

    public Location(long userID,  long latitude, long longitude, Date timestamp) {
        this.userID = userID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    /**
     * Save the model data.
     */
    @Override
    public void save() {
        // Try update - if doesn't exist then save
        // TODO: The model needs to be changed so that the ID is a foreign primary key.
        try {
            this.update();
        } catch(Exception ex) {
            super.save();
        }
    }

    /**
     * Get the current location of a user's friend.
     *
     * @param currentUserID
     * @param friendUserID
     *
     * @return The location of the friend.
     *
     * @throws Exception The user's are not friends.
     */
    public static Location getFriendLocation(long currentUserID, long friendUserID) throws Exception {
        if(!Connection.areFriends(currentUserID, friendUserID)) {
            throw new Exception("Not Friends");
        }

        return find.byId(friendUserID);
    }
}
