package com.innoapps.eventmanagement.common.sponsors.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.speakers.view.SpeakerView;
import com.innoapps.eventmanagement.common.sponsors.view.SponsorsView;

/**
 * Created by ankit on 3/16/2017.
 */

public interface SponsorsPresenter {

    void callSponsorsList(SponsorsView speakerView, Activity activity);
}
