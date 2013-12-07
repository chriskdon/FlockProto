package ca.brocku.cosc.flock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.ConnectionAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.connection.Connection;
import ca.brocku.cosc.flock.data.api.json.models.connection.PendingFriendsResponse;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.notifications.Notification;
import ca.brocku.cosc.flock.notifications.adapters.NotificationAdapter;

/**
 * Created by kubasub on 11/18/2013.
 */
public class NotificationsFragment extends Fragment {
    private List<Notification> notificationList;
    private NotificationAdapter notificationAdapter;
    private ListView notificationsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        //Populates the list with friend requests
        notificationList = new ArrayList<Notification>();
        populateList();

        //Creates the list adapter
        notificationAdapter = new NotificationAdapter(getActivity(), notificationList);

        //Sets the list adapter for the list view
        notificationsListView = (ListView) v.findViewById(R.id.notifications_listView);
        notificationsListView.setAdapter(notificationAdapter);

        return v;
    }

    private void populateList() {
        try {
            ConnectionAPIAction.getPendingConnections(new UserDataManager(getActivity()).getUserSecret(), new APIResponseHandler<PendingFriendsResponse>() {
                @Override
                public void onResponse(PendingFriendsResponse pendingFriendsResponse) {
                    List<Connection> connectionList = pendingFriendsResponse.connections;

                    for(Connection connection : connectionList) {
                        //Sets variables for a notification row
                        long friendUserID = connection.friendUserID;
                        String username = connection.username;
                        String firstName = connection.firstname;
                        String lastName = connection.lastname;
                        String message = username+" would like to be friends.";

                        //Adds a new notification to the list
                        notificationList.add(new Notification(Notification.FRIEND_REQUEST,
                                                              friendUserID,
                                                              username,
                                                              firstName,
                                                              lastName,
                                                              message));
                    }
                }

                @Override
                public void onError(ErrorModel result) {
                    super.onError(result);
                }
            });
        } catch (NoUserSecretException e) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }
}