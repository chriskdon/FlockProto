package controllers.api;

import api.json.models.GenericErrorModel;
import api.json.models.GenericSuccessModel;
import api.json.models.location.SetLocationRequestModel;
import models.Location;
import models.User;
import play.mvc.*;

import java.util.Calendar;

/**
 * Handle all location based requests.
 */
public class LocationController extends ApiControllerBase {
    /**
     * Set your own location.
     * @return
     */
    public static Result set() {
        try {
            SetLocationRequestModel setRequest = mapper.readValue(request().body().asJson(),
                                                                  SetLocationRequestModel.class);

            Location location = new Location(User.findBySecret(setRequest.secret).id, setRequest.secret,
                                             setRequest.latitude, setRequest.longitude,
                                             Calendar.getInstance().getTime());

            // Try update - if doesn't exist then save
            // TODO: The model needs to be changed so that the ID is a foreign primary key.
            try {
                location.update();
            } catch(Exception ex) {
                location.save();
            }

            return ok((new GenericSuccessModel("Location Updated")).toJsonString());
        } catch(Exception ex) {
            return ok((new GenericErrorModel(ex.getMessage())).toJsonString());
        }
    }

    /**
     * Set your own visibility.
     * @return
     */
    public static Result visibility() {
        return ok("VIS");
    }

    /**
     * Get the location of a friend
     * @return
     */
    public static Result get() {
        return ok("get");
    }

    /**
     * Get the locations for all friends of a user.
     *
     * @return
     */
    public static Result friends() {
        return ok("get all");
    }


}
