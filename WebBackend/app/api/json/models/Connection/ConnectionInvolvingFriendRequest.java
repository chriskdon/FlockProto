package api.json.models.Connection;

/**
 * Asking to make two users friends.
 */
public class ConnectionInvolvingFriendRequest extends ConnectionRequestBase {
    private Long friendUserID;

    public Long getFriendUserID() { return friendUserID; }
    public void setFriendUserID(Long value) { friendUserID = value; }
}
