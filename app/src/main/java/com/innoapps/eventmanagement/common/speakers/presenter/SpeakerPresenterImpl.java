package com.innoapps.eventmanagement.common.speakers.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenter;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;
import com.innoapps.eventmanagement.common.speakers.view.SpeakerView;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 3/8/2017.
 */

public class SpeakerPresenterImpl implements SpeakerPresenter {

    Activity activity;
    SpeakerView speakerView;
    UserSession userSession;

    @Override
    public void callGetSpeakerList(SpeakerView speakerView, Activity activity) {
        try {
            this.activity = activity;
            userSession = new UserSession(activity);
            this.speakerView = speakerView;
            ApiAdapter.getInstance(activity);
            getAllFriendList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }


    private void getAllFriendList() {


        Progress.start(activity);


        Call<SpeakerModel> callSpeaker;


        callSpeaker = ApiAdapter.getApiService().getSpeaker("get_all_speakers");

        callSpeaker.enqueue(new Callback<SpeakerModel>() {
            @Override
            public void onResponse(Call<SpeakerModel> call, Response<SpeakerModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    SpeakerModel friendsModel = response.body();

                    ArrayList<SpeakerModel.SpeakerList>  userFriendLists = friendsModel.getSpeakerList();

                    String message = friendsModel.getMsg();


                    if (friendsModel.getStatus() == 1 && friendsModel.getStatusCode() == 1) {
                        speakerView.getSpeakerSuccessful(userFriendLists);


                    } else {
                        speakerView.getSpeakerUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    speakerView.getSpeakerUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<SpeakerModel> call, Throwable t) {
                Progress.stop();
                speakerView.getSpeakerUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }


}
