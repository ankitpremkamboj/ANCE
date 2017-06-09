package com.innoapps.eventmanagement.common.myfriendevent.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.events.view.EventView;
import com.innoapps.eventmanagement.common.myfriendevent.view.MyFriendEventView;

/**
 * Created by ankit on 3/29/2017.
 */

public interface MyFriendEventPresenter {

    void callGetFriendEventsList(MyFriendEventView myFriendEventView, Activity activity, String friendID);

    void callLike(String eventID, String likeValue);
}
