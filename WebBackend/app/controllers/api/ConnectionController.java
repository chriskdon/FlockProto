package controllers.api;

import api.json.models.Connection.AskFriendRequestModel;
import api.json.models.GenericErrorModel;
import api.json.models.GenericSuccessModel;
import models.Connection;
import models.User;
import org.codehaus.jackson.map.ObjectMapper;
import play.mvc.Controller;
import play.mvc.*;

import java.io.IOException;

/**
 * Created by chriskellendonk on 11/14/2013.
 */
public class ConnectionController extends Controller {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Create a request to become "friends"/start a connection with
     * another user.
     *
     * @return JSON Response
     */
    public static Result ask() {
        try {
            AskFriendRequestModel askModel = mapper.readValue(request().body().asJson(), AskFriendRequestModel.class);

            long userID = User.findBySecret(askModel.getSecret()).id;

            // Make sure they aren't already friends
            if(userID != askModel.getFriendUserID() && !Connection.alreadyExists(userID, askModel.getFriendUserID())) {
                (new Connection(userID, askModel.getFriendUserID(), false)).save(); // Insert new record
                return ok((new GenericSuccessModel("Friend request sent.")).toJsonString());
            } else {
                return ok((new GenericErrorModel("Friend request already sent.")).toJsonString());
            }
        } catch(IOException ex) { }

        return ok((new GenericErrorModel()).toJsonString());
    }
}
