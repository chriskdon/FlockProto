package ca.brocku.cosc.flock.data.api.json.models.location;

import ca.brocku.cosc.flock.data.api.json.models.UserActionModel;

/**
 * Set a user's location
 */
public class SetLocationRequestModel extends UserActionModel {
    public double latitude, longitude;
}
