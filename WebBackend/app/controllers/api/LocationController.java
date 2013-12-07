package controllers.api;

import api.json.models.ErrorTypes;
import api.json.models.ErrorModel;
import api.json.models.GenericSuccessModel;
import api.json.models.UserActionModel;
import api.json.models.location.FriendLocationRequestModel;
import api.json.models.location.FriendLocationsListResponseModel;
import api.json.models.location.SetLocationRequestModel;
import api.json.models.location.UserLocationModel;

import models.Location;
import models.User;
import play.mvc.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Handle all location based requests.
 */
public class LocationController extends ApiControllerBase {
    /**
     * Set your own location.
     *
     * @return
     */
    public static Result set() {
        try {
            SetLocationRequestModel setRequest = mapper.readValue(request().body().asJson(),
                                                                  SetLocationRequestModel.class);

            Location location = new Location(User.findBySecret(setRequest.secret).id,
                                             setRequest.latitude, setRequest.longitude,
                                             Calendar.getInstance().getTime());

            location.save();

            return ok((new GenericSuccessModel("Location Updated")).toJsonString());
        } catch(Exception ex) {
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }

    /**
     * Remove all location information.
     *
     * @return
     */
    public static Result hide() {
        try {
            UserActionModel request = mapper.readValue(request().body().asJson(), UserActionModel.class);

            Location.find.byId(User.findBySecret(request.secret).id).delete();

            return ok((new GenericSuccessModel("Location Hidden/Deleted")).toJsonString());
        } catch(Exception ex) {
            return ok((new ErrorModel()).toJsonString());
        }
    }

    /**
     * Get the location of a friend
     * @return
     */
    public static Result get() {
        try {
            FriendLocationRequestModel request = mapper.readValue(request().body().asJson(),
                                                                  FriendLocationRequestModel.class);

            Location friendLocation = Location.getFriendLocation(User.findBySecret(request.secret).id,
                                                                 request.friendUserID);

            return ok((new UserLocationModel(friendLocation)).toJsonString());
        } catch(Exception ex) {
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }

    /**
     * Get the locations for all friends of a user.
     *
     * @return
     */
    public static Result friends() {
        try {
            UserActionModel request = mapper.readValue(request().body().asJson(), UserActionModel.class);

            List<Location> locations = Location.getFriendLocations(User.findBySecret(request.secret).id);

            // Set Locations
            ArrayList<UserLocationModel> resultList = new ArrayList<UserLocationModel>(locations.size());
            for(Location l : locations) {
                resultList.add(new UserLocationModel(l));
            }

            FriendLocationsListResponseModel response = new FriendLocationsListResponseModel(resultList);

            return ok(response.toJsonString());
        } catch(Exception ex) {
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }


}
