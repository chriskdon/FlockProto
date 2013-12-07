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

import ca.brocku.cosc.flock.R;
import ca.brocku.cosc.flock.friends.Friend;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/5/2013
 */
public class FriendsAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final List<Friend> friends;

    public FriendsAdapter(Context context, List<Friend> itemsArrayList) {
        this.context = context;
        this.friends = itemsArrayList;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // 1. Create inflater
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        // 2. Get rowView from inflater
//        View rowView = inflater.inflate(R.layout.friend_row, parent, false);
//
//        // 3. Get the two text view from the rowView
//        TextView labelView = (TextView) rowView.findViewById(R.id.label);
//        TextView valueView = (TextView) rowView.findViewById(R.id.value);
//
//        // 4. Set the text for textView
//        labelView.setText(friends.get(position).fullName);
//        valueView.setText(friends.get(position).username);
//
//        // 5. retrn rowView
//        return rowView;
//    }

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
        friendName.setText(friend.fullName);
        friendUsername.setText(friend.username);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_row_child, null);
        }

        //Bind Controls
        ImageButton routeButton = (ImageButton) convertView.findViewById(R.id.routeFriend_button);
        ImageButton peckButton = (ImageButton) convertView.findViewById(R.id.peckFriend_button);
        ImageButton messageButton = (ImageButton) convertView.findViewById(R.id.messageFriend_button);
        ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.deleteFriend_button);

        //Bind Handlers
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add routing functionality
            }
        });

        peckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add pecking functionality
            }
        });

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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add dialog confirmation
                //TODO: add functionality to delete friend
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
