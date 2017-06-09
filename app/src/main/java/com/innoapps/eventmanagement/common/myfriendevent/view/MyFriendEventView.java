package com.innoapps.eventmanagement.common.myfriendevent.view;

import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.myfriendevent.model.MyFriendEventModel;

import java.util.ArrayList;

/**
 * Created by ankit on 3/29/2017.
 */

public interface MyFriendEventView {

    void getFriendEventSuccessful(ArrayList<MyFriendEventModel.EventList> eventLists);

    void getFriendEventUnSuccessful(String message);

    void eventInternetError();
}
