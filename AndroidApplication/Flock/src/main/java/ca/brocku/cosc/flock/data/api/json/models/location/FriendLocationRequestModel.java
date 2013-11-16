package ca.brocku.cosc.flock.data.api.json.models.location;

import ca.brocku.cosc.flock.data.api.json.models.UserActionModel;

/**
 * Get the location of a friend
 */
public class FriendLocationRequestModel extends UserActionModel {
    public long friendUserID;
}
