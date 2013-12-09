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
import ca.brocku.cosc.flock.data.api.json.models.connection.ConnectionListResponse;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.notifications.Notification;
import ca.brocku.cosc.flock.notifications.adapters.NotificationAdapter;

/**
 * Created by kubasub on 11/18/2013.
 */
public class NotificationsFragment extends PageFragment {
    private List<Notification> notificationList;        // TODO: Do we need this? Doesn't adapter hold it
    private NotificationAdapter notificationAdapter;
    private ListView notificationsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        //Sets the list adapter for the list view
        notificationsListView = (ListView) v.findViewById(R.id.notifications_listView);

        //Populates the list with friend requests
        notificationList = new ArrayList<Notification>();

        // TODO: Set a flag in settings or somewhere from the push notification intent
        // TODO: Need to repopulate list whenever we look at notifications
        populateList(); // When we go to the view check for new friend requests

        return v;
    }

    /**
     * Fired when the page becomes visible
     */
    @Override
    public void onPageVisible() {
        super.onPageVisible();

        // Populate Friends
        populateList();
    }

    /**
     * Populate the notification list with the notifications
     */
    private void populateList() {
        try {
            ConnectionAPIAction.getPendingConnections(new UserDataManager(getActivity()).getUserSecret(), new APIResponseHandler<ConnectionListResponse>() {
                @Override
                public void onResponse(ConnectionListResponse connectionListResponse) {
                    List<Connection> connectionList = connectionListResponse.connections;

                    notificationList.clear(); // We don't want duplicates
                    for(Connection connection : connectionList) {
                        String message = connection.username + " would like to be friends.";

                        //Adds a new notification to the list
                        notificationList.add(new Notification(Notification.FRIEND_REQUEST,
                                connection.friendUserID,
                                connection.username,
                                connection.firstname,
                                connection.lastname,
                                message));

                        // Fill the list
                        notificationAdapter = new NotificationAdapter(getActivity(), notificationList);
                        notificationsListView.setAdapter(notificationAdapter);
                    }
                }

                @Override
                public void onError(ErrorModel result) {
                    // TODO: handle error
                }
            });
        } catch (NoUserSecretException e) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }
}