package controllers.api;

import api.json.models.connection.ConnectionInvolvingFriendRequest;
import api.json.models.connection.ResponseFriendRequestModel;
import api.json.models.GenericErrorModel;
import api.json.models.GenericSuccessModel;
import api.json.models.user.UserInformationModel;
import models.Connection;
import models.User;
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
            ConnectionInvolvingFriendRequest askModel = mapper.readValue(request().body().asJson(), ConnectionInvolvingFriendRequest.class);

            long userID = User.findBySecret(askModel.secret).id;

            // Make sure they aren't already friends
            if(userID != askModel.friendUserID && !Connection.alreadyExists(userID, askModel.friendUserID)) {
                (new Connection(userID, askModel.friendUserID, false)).save(); // Insert new record
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

             User userA = User.findBySecret(responseModel.secret);

             if(responseModel.accept) {
                Connection.acceptConnection(userA.id, responseModel.friendUserID);
             } else {
                Connection.declineConnection(userA.id, responseModel.friendUserID);
             }

             return ok((new GenericSuccessModel()).toJsonString());
         } catch(Exception ex) {
             return ok((new GenericErrorModel(ex.getMessage())).toJsonString());
         }
    }

    /**
     * Remove a friend.
     *
     * @return JSON Response
     */
    public static Result remove() {
        try {
            ConnectionInvolvingFriendRequest request = mapper.readValue(request().body().asJson(),
                                                                        ConnectionInvolvingFriendRequest.class);

             User user = User.findBySecret(request.secret);

            Connection.declineConnection(user.id, request.friendUserID);

            return ok((new GenericSuccessModel("Friend Removed")).toJsonString());
        } catch(Exception ex) {
            return ok((new GenericErrorModel(ex.getMessage())).toJsonString());
        }
    }

    /**
     * Get information on a friend.
     *
     * @return JSON Result of information
     */
    public static Result get() {
        try {
            ConnectionInvolvingFriendRequest request = mapper.readValue(request().body().asJson(),
                                                                        ConnectionInvolvingFriendRequest.class);

            User friend = Connection.getFriendInformation(User.findBySecret(request.secret).id, request.friendUserID);

            UserInformationModel userInfo = new UserInformationModel(friend.username, friend.firstname,
                                                                     friend.lastname, friend.email);

            return ok(userInfo.toJsonString());
        } catch(Exception ex) {
            return ok((new GenericErrorModel(ex.getMessage())).toJsonString());
        }
    }
}
