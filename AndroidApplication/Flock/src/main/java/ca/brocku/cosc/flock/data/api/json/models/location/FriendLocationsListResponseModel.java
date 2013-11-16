package ca.brocku.cosc.flock.data.api.json.models.location;

import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

import java.util.List;

/**
 * Returns the location of all visible friends.
 */
public class FriendLocationsListResponseModel extends JsonModelBase {
    public List<UserLocationModel> friendLocations;

    public FriendLocationsListResponseModel() {}

    public FriendLocationsListResponseModel(List<UserLocationModel> list) {
        this.friendLocations = list;
    }
}
