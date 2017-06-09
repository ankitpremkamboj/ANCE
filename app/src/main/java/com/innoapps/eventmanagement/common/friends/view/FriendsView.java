package com.innoapps.eventmanagement.common.friends.view;

import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;

import java.util.ArrayList;

/**
 * Created by Ankit on 3/8/2017.
 */

public interface FriendsView {

    void getFriendsSuccessfull(ArrayList<FriendsModel.UserList> userFriendLists);

    void getFriendsUnSuccessful(String message);

    void FriendsInternetError();
    void getFriendsRequestSuccessful(String message, FriendAdapter.Holder holder);

}
