package api.json.models.connection;

import api.json.models.UserActionModel;

/**
 * Asking to make two users friends.
 */
public class ConnectionInvolvingFriendRequest extends UserActionModel {
    public Long friendUserID;
}
