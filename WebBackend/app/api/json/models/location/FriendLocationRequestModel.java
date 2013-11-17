package api.json.models.location;

import api.json.models.UserActionModel;

/**
 * Get the location of a friend
 */
public class FriendLocationRequestModel extends UserActionModel {
    public long friendUserID;
}
