package com.innoapps.eventmanagement.common.agenda.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;
import com.innoapps.eventmanagement.common.agenda.view.AgendaView;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/16/2017.
 */

public class AgendaPresenterImpl implements AgendaPresenter {
    AgendaView agendaView;
    Activity activity;

    @Override
    public void callAgendaList(AgendaView agendaView, Activity activity) {

        try {
            this.activity = activity;
            this.agendaView = agendaView;
            ApiAdapter.getInstance(activity);
            getAllAgendaList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }
    }


    private void getAllAgendaList() {


        Progress.start(activity);


        Call<AgendaModel> callAgenda;


        callAgenda = ApiAdapter.getApiService().getAgenda("get_agenda_list");

        callAgenda.enqueue(new Callback<AgendaModel>() {
            @Override
            public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    AgendaModel sponsorsModel = response.body();

                    ArrayList<AgendaModel.AgendaList> sponserLists = sponsorsModel.getAgendaList();
                    //ArrayList<AgendaModel.AgendaList.Datum>  datumArrayList=sponserLists.get

                    String message = sponsorsModel.getMsg();


                    if (sponsorsModel.getStatus() == 1 && sponsorsModel.getStatusCode() == 1) {
                        agendaView.getAgendaSuccessful(sponserLists);

                    } else {
                        agendaView.getAgendaUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    agendaView.getAgendaUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<AgendaModel> call, Throwable t) {
                Progress.stop();
                agendaView.getAgendaUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}
