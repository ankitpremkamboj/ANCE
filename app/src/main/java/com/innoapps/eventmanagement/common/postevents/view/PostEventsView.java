package com.innoapps.eventmanagement.common.postevents.view;

import com.innoapps.eventmanagement.common.userprofile.model.UserProfileDetailsModel;

/**
 * Created by ankit on 3/16/2017.
 */

public interface PostEventsView {

    void onEventTitleError(String title);
    void onEventDescriptionError(String description);
    void onEventImageError(String eventImage);
   // void onLocationError();
    void onPostEventsInternetError();
    void onPostEventsSuccessful(String message);
    void onPostEventsUnSuccessful(String msg);
}
