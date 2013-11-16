package api.json.models.location;

import api.json.models.JsonModelBase;

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
