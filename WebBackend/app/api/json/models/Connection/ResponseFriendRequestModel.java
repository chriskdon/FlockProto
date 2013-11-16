package api.json.models.Connection;

/**
 * Accepting a friend request.
 */
public class ResponseFriendRequestModel extends ConnectionInvolvingFriendRequest {
    private boolean accept;

    public boolean getAccept() { return accept; }
    public void setAccept(boolean value) { accept = value; }
}
