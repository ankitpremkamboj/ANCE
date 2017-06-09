package com.innoapps.eventmanagement.common.myfriends.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.myfriends.view.MyFriendsView;

/**
 * Created by ankit on 3/15/2017.
 */

public interface MyFriendsPresenter {

    void callSendFriendRequest(String userID, String active_value);

    void callGetMyFriendList(MyFriendsView friendsView, Activity activity);

}
