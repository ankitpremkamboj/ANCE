package com.innoapps.eventmanagement.common.pollsawards.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.pollsawards.model.AwardModel;
import com.innoapps.eventmanagement.common.pollsawards.view.AwardsView;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/23/2017.
 */

public class AwardsPresenterImpl implements AwardPresenter {

    AwardsView awardsView;
    Activity activity;

    @Override
    public void callGetAwardList(AwardsView awardsView, Activity activity) {

        try {
            this.activity = activity;
            this.awardsView = awardsView;

            ApiAdapter.getInstance(activity);
            getAllAwardList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void getAllAwardList() {


        Progress.start(activity);


        Call<AwardModel> callBlogs;


        callBlogs = ApiAdapter.getApiService().award("get_all_awards");

        callBlogs.enqueue(new Callback<AwardModel>() {
            @Override
            public void onResponse(Call<AwardModel> call, Response<AwardModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    AwardModel blogsModel = response.body();

                    ArrayList<AwardModel.AwardList> awardList = blogsModel.getAwardList();

                    String message = blogsModel.getMsg();


                    if (blogsModel.getStatus() == 1 && blogsModel.getStatusCode() == 1) {
                        awardsView.getAwardsSuccessfull(awardList);


                    } else {
                        awardsView.getAwardsUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    awardsView.getAwardsUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<AwardModel> call, Throwable t) {
                Progress.stop();
                awardsView.getAwardsUnSuccessful(activity.getString(R.string.server_error));

            }

        });

    }
}
