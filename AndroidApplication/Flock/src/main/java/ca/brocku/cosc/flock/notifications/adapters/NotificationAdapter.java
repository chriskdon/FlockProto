package ca.brocku.cosc.flock.notifications.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.brocku.cosc.flock.LoginActivity;
import ca.brocku.cosc.flock.R;
import ca.brocku.cosc.flock.RegisterActivity;
import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.ConnectionAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.notifications.Notification;

/**
 * Created by kubasub on 12/6/2013.
 */
public class NotificationAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<Notification> notificationList;

    public NotificationAdapter(Activity activity, List<Notification> notificationList) {
        this.activity = activity;
        this.notificationList = notificationList;
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * The row that will appear in the list.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Notification notification = notificationList.get(position);
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView;

        int notificationType = notification.getType();

        if(notificationType == Notification.FRIEND_REQUEST) { //make Friend Request view
            rowView = inflater.inflate(R.layout.notification_friend_request_row, null);

            //Bind Controls
            TextView message = (TextView) rowView.findViewById(R.id.notification_friend_request_message);
            Button acceptButton = (Button) rowView.findViewById(R.id.friendRequest_acceptButton);
            Button declineButton = (Button) rowView.findViewById(R.id.friendRequest_declineButton);

            //Bind Handlers
            acceptButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    try {
                        ConnectionAPIAction.responseToFriendRequest(new UserDataManager(activity).getUserSecret(), notification.getFriendUserID(), true, new APIResponseHandler<GenericSuccessModel>() {
                            @Override
                            public void onResponse(GenericSuccessModel genericSuccessModel) {
                                rowView.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(ErrorModel result) {
                                Toast.makeText(activity, "Could not process request. Try again later.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (NoUserSecretException e) {
                        activity.finish();
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                }
            });

            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    try {
                        ConnectionAPIAction.responseToFriendRequest(new UserDataManager(activity).getUserSecret(), notification.getFriendUserID(), false, new APIResponseHandler<GenericSuccessModel>() {
                            @Override
                            public void onResponse(GenericSuccessModel genericSuccessModel) {
                                rowView.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(ErrorModel result) {
                                Toast.makeText(activity, "Could not process request. Try again later.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (NoUserSecretException e) {
                        activity.finish();
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                }
            });

            message.setText(notification.getMessage());
        } else {
            rowView = null;
        }

        return rowView;
    }
}
