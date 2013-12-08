package controllers.api;

import api.json.models.ErrorTypes;
import api.json.models.ErrorModel;
import api.json.models.UserActionModel;
import api.json.models.connection.ConnectionInvolvingFriendRequest;
import api.json.models.connection.ConnectionListResponse;
import api.json.models.connection.FriendRequest;
import api.json.models.connection.ResponseFriendRequestModel;
import api.json.models.GenericSuccessModel;
import api.json.models.user.UserInformationModel;
import models.Connection;
import models.User;
import play.mvc.*;

import java.io.IOException;
import java.util.ArrayList;

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
            FriendRequest askModel = mapper.readValue(request().body().asJson(), FriendRequest.class);

            long userID = User.findBySecret(askModel.secret).id;
            User friend = User.findByUsername(askModel.friendUsername);

            // User doesn't exist
            if(friend == null) {
                return ok((new ErrorModel("Invalid Username", ErrorTypes.ERROR_TYPE_USER, 1)).toJsonString());
            }

            // Make sure they aren't already friends
            if(userID != friend.id && !Connection.alreadyExists(userID, friend.id)) {
                (new Connection(userID, friend.id, false, true)).save(); // Insert new record
                return ok((new GenericSuccessModel("Friend request sent.")).toJsonString());
            } else {
                return ok((new ErrorModel("Friend request already sent.", ErrorTypes.ERROR_TYPE_USER)).toJsonString());
            }
        } catch(IOException ex) { }

        return ok((new ErrorModel()).toJsonString());
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
             return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
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
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
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
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }

    /**
     * Return a list of pending friend requests
     * @return
     */
    public static Result pending() {
        try {
            UserActionModel request = mapper.readValue(jsonBody(), UserActionModel.class);

            User user = User.findBySecret(request.secret);

            if(user == null) {
                return ok((new ErrorModel("Invalid User", ErrorTypes.ERROR_TYPE_USER)).toJsonString());
            }

            ArrayList<api.json.models.connection.Connection> result = new ArrayList<api.json.models.connection.Connection>();
            for(Connection c : Connection.getPendingConnections(user.id)) {
                User u = User.find.byId(c.userA);
                result.add(new api.json.models.connection.Connection(c.userA, u.username, u.firstname, u.lastname));
            }

            return ok((new ConnectionListResponse(result)).toJsonString());
        } catch (Exception ex) {
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }

    public static Result friends() {
        try {
            UserActionModel request = mapper.readValue(jsonBody(), UserActionModel.class);

            User user = User.findBySecret(request.secret);

            if(user == null) {
                return ok((new ErrorModel("Invalid Secret", ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
            }

            ArrayList<api.json.models.connection.Connection> result = new ArrayList<api.json.models.connection.Connection>();
            for(Connection c : Connection.getFriends(user)) {
                User info = null;
                if(c.userA == user.id) {
                    info = User.find.byId(c.userB);
                } else {
                    info = User.find.byId(c.userA);
                }

                api.json.models.connection.Connection friend = new api.json.models.connection.Connection();
                friend.firstname = info.firstname;
                friend.lastname = info.lastname;
                friend.username = info.username;
                friend.friendUserID = info.id;

                result.add(friend);
            }

            return ok((new ConnectionListResponse(result)).toJsonString());
        } catch(Exception ex) {
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }
}
