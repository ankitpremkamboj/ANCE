package com.innoapps.eventmanagement.common.speakers.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.speakers.view.SpeakerView;

/**
 * Created by Ankit on 3/8/2017.
 */

public interface SpeakerPresenter {


    void callGetSpeakerList(SpeakerView speakerView, Activity activity);
}
