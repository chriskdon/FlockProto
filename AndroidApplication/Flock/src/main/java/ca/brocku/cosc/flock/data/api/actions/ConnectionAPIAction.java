package ca.brocku.cosc.flock.data.api.actions;

import ca.brocku.cosc.flock.data.api.APIConnection;
import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.api.json.models.UserActionModel;
import ca.brocku.cosc.flock.data.api.json.models.connection.ConnectionInvolvingFriendRequest;
import ca.brocku.cosc.flock.data.api.json.models.connection.ConnectionListResponse;
import ca.brocku.cosc.flock.data.api.json.models.connection.FriendRequest;
import ca.brocku.cosc.flock.data.api.json.models.connection.ResponseFriendRequestModel;
import ca.brocku.cosc.flock.data.api.json.models.user.UserInformationModel;

/**
 * API tasks related to connections (i.e. friends)
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/6/2013
 */
public class ConnectionAPIAction extends APIAction {
    private static final String API_PATH = "/api/connections/";

    /**
     * Initiate a friend request.
     *
     * @param request
     * @param response
     */
    public static void initiateFriendRequest(FriendRequest request,
                           APIResponseHandler<GenericSuccessModel> response) {

        APIConnection.send(API_PATH + "ask", request, GenericSuccessModel.class, response);
    }

    /**
     * Add a friend
     * @param secret
     * @param friendUsername
     * @param response
     */
    public static void initiateFriendRequest(String secret, String friendUsername,
                            APIResponseHandler<GenericSuccessModel> response) {

        FriendRequest request = new FriendRequest();

        request.secret = secret;
        request.friendUsername = friendUsername;

        initiateFriendRequest(request, response);
    }

    /**
     * Respond to a friend request.
     *
     * @param request
     * @param response
     */
    public static void responseToFriendRequest(ResponseFriendRequestModel request,
                                               APIResponseHandler<GenericSuccessModel> response) {

        APIConnection.send(API_PATH + "respond", request, GenericSuccessModel.class, response);
    }

    /**
     * Respond to a friend request.
     *
     * @param secret
     * @param friendUserID
     * @param accept
     * @param response
     */
    public static void responseToFriendRequest(String secret, long friendUserID, boolean accept,
                                               APIResponseHandler<GenericSuccessModel> response) {

        ResponseFriendRequestModel request = new ResponseFriendRequestModel();

        request.secret = secret;
        request.friendUserID = friendUserID;
        request.accept = accept;

        responseToFriendRequest(request, response);
    }

    /**
     * Remove a friend.
     * @param request
     * @param response
     */
    public static void removeFriend(ConnectionInvolvingFriendRequest request,
                                    APIResponseHandler<GenericSuccessModel> response) {

        APIConnection.send(API_PATH + "remove", request, GenericSuccessModel.class, response);
    }

    /**
     * Remove a friend.
     * @param secret
     * @param friendUserID
     */
    public static void removeFriend(String secret, long friendUserID,
                                    APIResponseHandler<GenericSuccessModel> response) {

        ConnectionInvolvingFriendRequest request = new ConnectionInvolvingFriendRequest();
        request.secret = secret;
        request.friendUserID = friendUserID;

        removeFriend(request, response);
    }

    /**
     * Get information about a friend.
     * @param request
     * @param response
     */
    public static void getInformation(ConnectionInvolvingFriendRequest request,
                                      APIResponseHandler<UserInformationModel> response) {

        APIConnection.send(API_PATH + "get", request, UserInformationModel.class, response);
    }

    /**
     * Get information about a friend
     * @param secret
     * @param friendUserID
     * @param response
     */
    public static void getInformation(String secret, long friendUserID,
                                      APIResponseHandler<UserInformationModel> response) {

        ConnectionInvolvingFriendRequest request = new ConnectionInvolvingFriendRequest();
        request.secret = secret;
        request.friendUserID = friendUserID;

        getInformation(request, response);
    }

    /**
     * List pending friend requests.
     * @param request
     * @param response
     */
    public static void getPendingConnections(UserActionModel request,
                                             APIResponseHandler<ConnectionListResponse> response) {

        APIConnection.send(API_PATH + "pending", request, ConnectionListResponse.class, response);
    }

    /**
     * List pending friend requests.
     * @param secret
     * @param response
     */
    public static void getPendingConnections(String secret,
                                             APIResponseHandler<ConnectionListResponse> response) {

        UserActionModel request = new UserActionModel();
        request.secret = secret;

        getPendingConnections(request, response);
    }

    /**
     * List friends
     * @param request
     * @param response
     */
    public static void getFriends(UserActionModel request,
                                  APIResponseHandler<ConnectionListResponse> response) {

        APIConnection.send(API_PATH + "friends", request, ConnectionListResponse.class, response);
    }

    /**
     * List pending friend requests.
     * @param secret
     * @param response
     */
    public static void getFriends(String secret,
                                             APIResponseHandler<ConnectionListResponse> response) {

        UserActionModel request = new UserActionModel();
        request.secret = secret;

        getFriends(request, response);
    }
}
