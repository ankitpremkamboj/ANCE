package com.innoapps.eventmanagement.common.postevents.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.postevents.view.PostEventsView;
import com.innoapps.eventmanagement.common.signup.view.RegisterView;

/**
 * Created by ankit on 3/16/2017.
 */

public interface PostEventsPresenter {

    void validatePostEvent(Activity activity, PostEventsView postEventsView, String event_title, String description,  String eventImage, String latitude, String longitude);

}
