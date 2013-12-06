package ca.brocku.cosc.flock;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

import ca.brocku.cosc.flock.friends.Friend;
import ca.brocku.cosc.flock.friends.adapters.FriendsAdapter;

/**
 * Created by kubasub on 11/18/2013.
 */
public class FriendsFragment extends Fragment {
    private FriendsAdapter friendsAdapter;
    private ExpandableListView friendsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        friendsList = (ExpandableListView) v.findViewById(R.id.friends_list);

        // TODO: Replace with friends list
        ArrayList<Friend> test = new ArrayList<Friend>();
        Friend x = new Friend(); x.fullName = "John Smith"; x.username = "johnsmith";
        Friend y = new Friend(); y.fullName = "Chris Kellendonk"; y.username = "iswearimnotgay";
        test.add(x); test.add(y);



        friendsAdapter = new FriendsAdapter(getActivity(), test);
        friendsList.setAdapter(friendsAdapter);

        friendsList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int len = friendsAdapter.getGroupCount();

                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        friendsList.collapseGroup(i);
                    }
                }
            }
        });




        return v;
    }
}