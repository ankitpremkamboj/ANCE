package com.innoapps.eventmanagement.common.friends.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.events.adapter.EventAdapter;
import com.innoapps.eventmanagement.common.friends.FriendsActivity;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;

/**
 * Created by ankit on 3/15/2017.
 */

public interface FriendsPresenter {

    void callSendFriendRequest(String userID, String active_value, FriendAdapter.Holder holder);

    void callGetFriendList(FriendsView friendsView, Activity activity,String searchValue);

}
