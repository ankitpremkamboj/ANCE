package com.innoapps.eventmanagement.common.events.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.events.view.EventView;

/**
 * Created by Ankit on 3/6/2017.
 */

public interface EventPresenter {

    void callGetEventsList(EventView eventView, Activity activity, int page);

    void callLike(String eventID, String likeValue);

}
