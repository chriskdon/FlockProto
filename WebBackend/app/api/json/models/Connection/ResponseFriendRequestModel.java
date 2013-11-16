package api.json.models.Connection;

/**
 * Accepting a friend request.
 */
public class ResponseFriendRequestModel extends ConnectionRequestBase {
    private boolean accept;
    private long friendUserID;

    public boolean getAccept() { return accept; }
    public void setAccept(boolean value) { accept = value; }

    public long getFriendUserID() { return friendUserID; }
    public void setFriendUserID(long value) { friendUserID = value; }
}
