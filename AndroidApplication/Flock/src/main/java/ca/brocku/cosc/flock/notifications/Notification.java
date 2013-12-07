package ca.brocku.cosc.flock.notifications;

/**
 * Created by kubasub on 12/6/2013.
 */
public class Notification {
    public static final int PECK = 0;
    public static final int FRIEND_REQUEST = 1;

    private int type;
    private long friendUserID;
    private String username, firstName, lastName, message;

    public Notification(int type, long friendUserID, String username, String firstName, String lastName, String message) {
        this.type = type;
        this.friendUserID = friendUserID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public long getFriendUserID() {
        return friendUserID;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getMessage() {
        return message;
    }
}
