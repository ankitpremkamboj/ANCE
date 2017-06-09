package com.innoapps.eventmanagement.common.refelentry.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.refelentry.view.RefelEntryView;

/**
 * Created by ankit on 3/17/2017.
 */

public interface RefelEntryPresenter {

    void callRefelEntryList(RefelEntryView refelEntryView, Activity activity);

    void callRefelEntryTicketNo(RefelEntryView refelEntryView, Activity activity, String ticketNumber,String userId);


}
