package ca.brocku.cosc.flock.data.api.json.models.connection;

import ca.brocku.cosc.flock.data.api.json.models.UserActionModel;

/**
 * Asking to make two users friends.
 */
public class ConnectionInvolvingFriendRequest extends UserActionModel {
    public Long friendUserID;
}
