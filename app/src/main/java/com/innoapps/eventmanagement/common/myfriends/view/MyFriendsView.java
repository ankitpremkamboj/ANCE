package com.innoapps.eventmanagement.common.myfriends.view;

import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.myfriends.model.MyFriendsModel;

import java.util.ArrayList;

/**
 * Created by Ankit on 3/8/2017.
 */

public interface MyFriendsView {

    void getFriendsSuccessfull(ArrayList<MyFriendsModel.UserFriendList> userFriendLists);

    void getFriendsUnSuccessful(String message);

    void FriendsInternetError();
    void getFriendsRequestSuccessful(String message);

}
