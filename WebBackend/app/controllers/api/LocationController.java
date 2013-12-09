package controllers.api;

import api.json.models.ErrorTypes;
import api.json.models.ErrorModel;
import api.json.models.GenericSuccessModel;
import api.json.models.UserActionModel;
import api.json.models.location.FriendLocationRequestModel;
import api.json.models.location.FriendLocationsListResponseModel;
import api.json.models.location.SetLocationRequestModel;
import api.json.models.location.UserLocationModel;

import gcm.GcmManager;
import models.Connection;
import models.Location;
import models.User;
import play.mvc.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
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

            User user = User.findBySecret(setRequest.secret);

            if(user == null) {
                return ok((new ErrorModel("Invalid Secret", ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
            }

            Location location = new Location(user.id,
                                             setRequest.latitude, setRequest.longitude,
                                             Calendar.getInstance().getTime());

            location.save();

            // Do GCM Push notification
            List<Connection> friends = Connection.getFriends(user);
            HashSet<String> gcmIds = new HashSet<String>();

            for(Connection c : friends) {
                if(c.userA == user.id) {
                    gcmIds.add(User.find.byId(c.userB).gcmRegistrationID);
                } else {
                    gcmIds.add(User.find.byId(c.userA).gcmRegistrationID);
                }
            }

            GcmManager.send(gcmIds, "FLOCK_NEW_FRIEND_LOCATION");

            // Return
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

            User friend = User.find.byId(request.friendUserID);

            if(friend == null) {
                return ok((new ErrorModel("Invalid Friend", ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
            }

            Location friendLocation = Location.getFriendLocation(User.findBySecret(request.secret).id,
                                                                 request.friendUserID);


            return ok((new UserLocationModel(friend.username, friendLocation)).toJsonString());
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
                User u = User.find.byId(l.userID);
                resultList.add(new UserLocationModel(u.username, l));
            }

            FriendLocationsListResponseModel response = new FriendLocationsListResponseModel(resultList);

            return ok(response.toJsonString());
        } catch(Exception ex) {
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }


}
