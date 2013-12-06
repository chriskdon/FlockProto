package ca.brocku.cosc.flock;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ca.brocku.cosc.flock.friends.Friend;
import ca.brocku.cosc.flock.friends.adapters.FriendsAdapter;

/**
 * Created by kubasub on 11/18/2013.
 */
public class FriendsFragment extends Fragment {
    private ListView friendsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        friendsList = (ListView) v.findViewById(R.id.friendsList);

        // TODO: Replace with friends list
        ArrayList<Friend> test = new ArrayList<Friend>();
        Friend x = new Friend();
        x.title = "dsf";
        x.description = "DS";

        test.add(x);

        FriendsAdapter adapter = new FriendsAdapter(getActivity(), test);
        friendsList.setAdapter(adapter);

        return v;
    }
}