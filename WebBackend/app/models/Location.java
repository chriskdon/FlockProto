package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
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
    public double latitude;

    @Constraints.Required
    @Column(name="Longitude")
    public double longitude;

    @Constraints.Required
    @Column(name="Timestamp")
    public Date timestamp;

    public Location() { }

    public Location(long userID,  double latitude, double longitude, Date timestamp) {
        this.userID = userID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public Location(SqlRow row) {
        this(row.getLong("UserID"), row.getDouble("Latitude"), row.getDouble("Longitude"), row.getDate("Timestamp"));
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
        if(!Connection.areFriends(currentUserID, friendUserID) || !Connection.isVisible(currentUserID, friendUserID)) {
            throw new Exception("Not Friends or Not Visible");
        }

        return find.byId(friendUserID);
    }

    /**
     * Get the locations of all a user's friends.
     *
     * @param currentUserID
     * @return
     */
    public static List<Location> getFriendLocations(long currentUserID) {
        String sql = " SELECT L.* " +
                     " FROM Locations L " +
                     " LEFT JOIN Connections C ON (C.UserA = L.UserID OR C.UserB = L.UserID) " +
                     " WHERE " +
                     " L.UserID <> :UserID AND " +
                     " C.Visible = 1 AND " +
                     " (C.UserA = :UserID OR C.UserB = :UserID); ";

        List<SqlRow> data = Ebean.createSqlQuery(sql).setParameter("UserID", currentUserID).findList();
        ArrayList<Location> locations = new ArrayList<Location>(data.size());

        for(SqlRow r : data) {
            locations.add(new Location(r));
        }

        return locations;
    }
}
