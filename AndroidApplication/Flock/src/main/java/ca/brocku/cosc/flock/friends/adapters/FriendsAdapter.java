package ca.brocku.cosc.flock.friends.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.brocku.cosc.flock.FriendsFragment;
import ca.brocku.cosc.flock.R;
import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.ConnectionAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.friends.Friend;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/5/2013
 */
public class FriendsAdapter extends BaseExpandableListAdapter {
    private final FriendsFragment friendsFragment;
    private final Context context;
    private final List<Friend> friends;

    public FriendsAdapter(FriendsFragment friendsFragment, Context context, List<Friend> itemsArrayList) {
        this.friendsFragment = friendsFragment;
        this.context = context;
        this.friends = itemsArrayList;
    }

    @Override
    public int getGroupCount() {
        return friends.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return friends.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Friend friend = (Friend) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_row, null);
        }

        TextView friendName = (TextView) convertView.findViewById(R.id.friend_name);
        TextView friendUsername = (TextView) convertView.findViewById(R.id.friend_username);
        friendName.setText(friend.getFullName());
        friendUsername.setText(friend.username);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_row_child, null);
        }

        //Bind Controls
        ImageButton messageButton = (ImageButton) convertView.findViewById(R.id.messageFriend_button);
        ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.deleteFriend_button);

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                try {
                    sendIntent.setData(Uri.parse("sms:"));
                    context.startActivity(sendIntent);
                } catch(Exception e) {
                    Toast.makeText(context, "Could not find messaging app.", Toast.LENGTH_LONG);
                }
            }
        });

        final View finalConvertView = convertView;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectionAPIAction.removeFriend(new UserDataManager(context).getUserSecret(), friends.get(groupPosition).userID, new APIResponseHandler<GenericSuccessModel>() {
                        @Override
                        public void onResponse(GenericSuccessModel genericSuccessModel) {
                            //finalConvertView.setVisibility(View.GONE);
                            friendsFragment.remove(groupPosition);
                        }

                        @Override
                        public void onError(ErrorModel result) {
                            Toast.makeText(context, "Could not delete user at this time. Try again later.", Toast.LENGTH_SHORT);
                        }
                    });
                } catch (NoUserSecretException e) {
                    Toast.makeText(context, "Could not delete user at this time. Try again later.", Toast.LENGTH_SHORT);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
