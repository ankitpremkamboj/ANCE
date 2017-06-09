package com.innoapps.eventmanagement.common.refelentry.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.refelentry.model.RefelEntryModel;
import com.innoapps.eventmanagement.common.refelentry.view.RefelEntryView;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/17/2017.
 */

public class RefelEntryPresenterImpl implements RefelEntryPresenter {
    RefelEntryView refelEntryView;
    Activity activity;

    @Override
    public void callRefelEntryList(RefelEntryView refelEntryView, Activity activity) {

        try {
            this.activity = activity;
            this.refelEntryView = refelEntryView;
            ApiAdapter.getInstance(activity);
            getRefelEntryList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void getRefelEntryList() {


        Progress.start(activity);


        Call<RefelEntryModel> callRefel;


        callRefel = ApiAdapter.getApiService().getRefelEntry("get_winner_list");

        callRefel.enqueue(new Callback<RefelEntryModel>() {
            @Override
            public void onResponse(Call<RefelEntryModel> call, Response<RefelEntryModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    RefelEntryModel refelEntryModel = response.body();

                  //  RefelEntryModel.WinnerList refralList = refelEntryModel.getWinnerList();

                    ArrayList<RefelEntryModel.WinnerList> refralList = refelEntryModel.getWinnerList();

                    String message = refelEntryModel.getMsg();


                    if (refelEntryModel.getStatus() == 1 && refelEntryModel.getStatusCode() == 1) {
                        refelEntryView.getRefelEntrySuccessful(refralList);


                    } else {
                        refelEntryView.getRefelEntryUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    refelEntryView.getRefelEntryUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RefelEntryModel> call, Throwable t) {
                Progress.stop();
                refelEntryView.getRefelEntryUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }

    @Override
    public void callRefelEntryTicketNo(RefelEntryView refelEntryView, Activity activity, String ticketNumber,  String userId) {


        try {
            ApiAdapter.getInstance(activity);
            sendRefelEntryTicketNo(ticketNumber, userId);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }


    }

    private void sendRefelEntryTicketNo(String ticketNumber, String userId) {


        Progress.start(activity);


        Call<RefelEntryModel> callRefel;


        callRefel = ApiAdapter.getApiService().sendRefelEntry("add_ticket", ticketNumber,userId);

        callRefel.enqueue(new Callback<RefelEntryModel>() {
            @Override
            public void onResponse(Call<RefelEntryModel> call, Response<RefelEntryModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    RefelEntryModel refelEntryModel = response.body();


                    String message = refelEntryModel.getMsg();


                    if (refelEntryModel.getStatus() == 1 && refelEntryModel.getStatusCode() == 1) {
                      refelEntryView.sendRefelTicketNoSuccessful(message);


                    } else {
                        refelEntryView.getRefelEntryUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    refelEntryView.getRefelEntryUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RefelEntryModel> call, Throwable t) {
                Progress.stop();
                refelEntryView.getRefelEntryUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}
