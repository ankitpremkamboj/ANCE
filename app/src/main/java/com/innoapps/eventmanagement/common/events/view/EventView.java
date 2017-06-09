package com.innoapps.eventmanagement.common.events.view;

import com.innoapps.eventmanagement.common.events.model.EventsModel;

import java.util.ArrayList;

/**
 * Created by Ankit on 3/6/2017.
 */

public interface EventView {

    void getEventSuccessfull(ArrayList<EventsModel.EventList> eventLists);

    void getEventUnSuccessful(String message);

    void eventInternetError();


}
