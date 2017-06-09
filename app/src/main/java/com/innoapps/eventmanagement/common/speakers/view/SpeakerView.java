package com.innoapps.eventmanagement.common.speakers.view;

import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;

import java.util.ArrayList;

/**
 * Created by Ankit on 3/8/2017.
 */

public interface SpeakerView {

    void getSpeakerSuccessful(ArrayList<SpeakerModel.SpeakerList> speakerLists);

    void getSpeakerUnSuccessful(String message);

    void SpeakerInternetError();
}
