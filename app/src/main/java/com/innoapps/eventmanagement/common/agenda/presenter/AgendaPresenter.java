package com.innoapps.eventmanagement.common.agenda.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.agenda.view.AgendaView;
import com.innoapps.eventmanagement.common.sponsors.view.SponsorsView;

/**
 * Created by ankit on 3/16/2017.
 */

public interface AgendaPresenter {

    void callAgendaList(AgendaView agendaView, Activity activity);

}
