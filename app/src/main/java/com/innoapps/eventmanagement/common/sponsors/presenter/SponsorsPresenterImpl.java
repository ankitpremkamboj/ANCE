package com.innoapps.eventmanagement.common.sponsors.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;
import com.innoapps.eventmanagement.common.sponsors.view.SponsorsView;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/16/2017.
 */

public class SponsorsPresenterImpl implements SponsorsPresenter {

    Activity activity;
    SponsorsView sponsorsView;

    @Override
    public void callSponsorsList(SponsorsView sponsorsView, Activity activity) {

        try {
            this.activity = activity;
            this.sponsorsView = sponsorsView;
            ApiAdapter.getInstance(activity);
            getAllSponsorsList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void getAllSponsorsList() {


        Progress.start(activity);


        Call<SponsorsModel> callSpeaker;


        callSpeaker = ApiAdapter.getApiService().getSponsors("get_all_sponsers");

        callSpeaker.enqueue(new Callback<SponsorsModel>() {
            @Override
            public void onResponse(Call<SponsorsModel> call, Response<SponsorsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    SponsorsModel sponsorsModel = response.body();

                    ArrayList<SponsorsModel.SponserList> sponserLists = sponsorsModel.getSponserList();

                    String message = sponsorsModel.getMsg();


                    if (sponsorsModel.getStatus() == 1 && sponsorsModel.getStatusCode() == 1) {
                        sponsorsView.getSponsorsSuccessful(sponserLists);


                    } else {
                        sponsorsView.getSponsorsUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    sponsorsView.getSponsorsUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<SponsorsModel> call, Throwable t) {
                Progress.stop();
                sponsorsView.getSponsorsUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }


}
