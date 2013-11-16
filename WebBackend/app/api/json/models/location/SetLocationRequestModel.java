package api.json.models.location;

import api.json.models.UserActionModel;

/**
 * Set a user's location
 */
public class SetLocationRequestModel extends UserActionModel {
    public long latitude, longitude;
}
