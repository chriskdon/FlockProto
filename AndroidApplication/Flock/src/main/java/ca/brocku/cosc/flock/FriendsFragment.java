package ca.brocku.cosc.flock;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.ConnectionAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.ErrorTypes;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.friends.Friend;
import ca.brocku.cosc.flock.friends.adapters.FriendsAdapter;

/**
 * Created by kubasub on 11/18/2013.
 */
public class FriendsFragment extends Fragment {
    private ImageButton addFriendButton;
    private FriendsAdapter friendsAdapter;
    private ExpandableListView friendsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        //Bind Controls
        addFriendButton = (ImageButton) v.findViewById(R.id.add_friend_button);
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

        addFriendButton.setOnClickListener(new AddFriendHandler());

        return v;
    }

    private class AddFriendHandler implements View.OnClickListener {
        private Dialog dialog;
        private Button cancelButton, addButton;
        private EditText usernameInput;
        private TextView errorMessage;

        @Override
        public void onClick(View v) {
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.stub_add_friend);
            dialog.setTitle("Add Friend");
            dialog.show();

            cancelButton = (Button) dialog.findViewById(R.id.cancelFriend_dialogButton);
            addButton = (Button) dialog.findViewById(R.id.addFriend_dialogButton);
            usernameInput = (EditText) dialog.findViewById(R.id.addFriend_usernameInput);
            errorMessage = (TextView) dialog.findViewById(R.id.addFriend_errorMessage);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usernameInput.setText("");
                    dialog.hide();
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = usernameInput.getText().toString();

                    // Make sure username isn't empty
                    if (!username.isEmpty()) {
                        try {
                            ConnectionAPIAction.initiateFriendRequest(new UserDataManager(getActivity()).getUserSecret(),username, new APIResponseHandler<GenericSuccessModel>() {
                                @Override
                                public void onResponse(GenericSuccessModel genericSuccessModel) {
                                    usernameInput.setText("");
                                    dialog.hide();
                                    Toast.makeText(getActivity(), "Friend request sent.", Toast.LENGTH_SHORT);
                                }

                                @Override
                                public void onError(ErrorModel error) {
                                    if (error.errorType == ErrorTypes.ERROR_TYPE_USER) {
                                        errorMessage.setText(error.message);
                                        errorMessage.setVisibility(View.VISIBLE);
                                    } else {
                                        errorMessage.setText("Could not delete at this time. Try again later.");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } catch (NoUserSecretException e) {
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }

                    } else { //inform the user that all fields must be filled in
                        errorMessage.setText("Enter a username to add.");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }
}