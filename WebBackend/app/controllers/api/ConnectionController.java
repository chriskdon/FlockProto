package controllers.api;

import api.json.models.Connection.AskFriendRequestModel;
import api.json.models.Connection.ResponseFriendRequestModel;
import api.json.models.GenericErrorModel;
import api.json.models.GenericSuccessModel;
import models.Connection;
import models.User;
import org.codehaus.jackson.map.ObjectMapper;
import play.mvc.Controller;
import play.mvc.*;

import java.io.IOException;

/**
 * Controller to handle connections/friends in the application.
 */
public class ConnectionController extends ApiControllerBase {

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

    /**
     * Respond to friend request (accept/deny)
     * @return
     */
    public static Result respond() {
         try {
             ResponseFriendRequestModel responseModel = mapper.readValue(request().body().asJson(),
                                                                         ResponseFriendRequestModel.class);

             User userA = User.findBySecret(responseModel.getSecret());

             if(responseModel.getAccept()) {
                Connection.acceptConnection(userA.id, responseModel.getFriendUserID());
             } else {
                Connection.declineConnection(userA.id, responseModel.getFriendUserID());
             }

             return ok((new GenericSuccessModel()).toJsonString());
         } catch(Exception ex) {
             return ok((new GenericErrorModel(ex.getMessage())).toJsonString());
         }
    }
}
