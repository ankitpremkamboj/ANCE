package com.innoapps.eventmanagement.common.sponsors.view;

import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;

import java.util.ArrayList;

/**
 * Created by ankit on 3/16/2017.
 */

public interface SponsorsView {

    void getSponsorsSuccessful(ArrayList<SponsorsModel.SponserList> sponserLists);

    void getSponsorsUnSuccessful(String message);

    void SponsorsInternetError();
}
