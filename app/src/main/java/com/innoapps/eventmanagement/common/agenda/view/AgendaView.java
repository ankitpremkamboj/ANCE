package com.innoapps.eventmanagement.common.agenda.view;

import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;

import java.util.ArrayList;

/**
 * Created by ankit on 3/16/2017.
 */

public interface AgendaView {


    void getAgendaSuccessful(ArrayList<AgendaModel.AgendaList> agendaLists);

    void getAgendaUnSuccessful(String message);

    void AgendaInternetError();
}
