package com.innoapps.eventmanagement.common.refelentry.view;

import com.innoapps.eventmanagement.common.refelentry.model.RefelEntryModel;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;

import java.util.ArrayList;

/**
 * Created by ankit on 3/17/2017.
 */

public interface RefelEntryView {

    void getRefelEntrySuccessful(ArrayList<RefelEntryModel.WinnerList> refralLists);

    void getRefelEntryUnSuccessful(String message);

    void RefelEntryInternetError();

    void sendRefelTicketNoSuccessful(String message);
}
