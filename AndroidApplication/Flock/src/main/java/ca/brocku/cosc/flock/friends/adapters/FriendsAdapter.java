package ca.brocku.cosc.flock.friends.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.brocku.cosc.flock.R;
import ca.brocku.cosc.flock.friends.Friend;

/**
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/5/2013
 */
public class FriendsAdapter extends ArrayAdapter<Friend> {
    private final Context context;
    private final List<Friend> itemsArrayList;

    public FriendsAdapter(Context context, List<Friend> itemsArrayList) {
        super(context, R.layout.friend_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.friend_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).title);
        valueView.setText(itemsArrayList.get(position).description);

        // 5. retrn rowView
        return rowView;
    }
}
