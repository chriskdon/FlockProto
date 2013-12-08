package ca.brocku.cosc.flock.friends;

/**
 * Represents a friend, and there information.
 * Is used within a listview.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/5/2013
 */
public class Friend {
    public String username;
    public String firstName;
    public String lastName;
    public long userID;

    public Friend(String firstName, String lastName, String username, long userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.userID = userID;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
