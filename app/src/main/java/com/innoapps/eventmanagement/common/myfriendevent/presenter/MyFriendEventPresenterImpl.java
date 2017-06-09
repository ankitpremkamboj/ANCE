package com.innoapps.eventmanagement.common.myfriendevent.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.myfriendevent.model.MyFriendEventModel;
import com.innoapps.eventmanagement.common.myfriendevent.view.MyFriendEventView;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/29/2017.
 */

public class MyFriendEventPresenterImpl implements MyFriendEventPresenter {
    Activity activity;
    MyFriendEventView eventView;

    UserSession userSession;

    @Override
    public void callGetFriendEventsList(MyFriendEventView eventView, Activity activity, String friendID) {
        try {
            this.activity = activity;
            this.eventView = eventView;
            ApiAdapter.getInstance(activity);
            getAllEventList(friendID);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void callLike(String eventID, String likeValue) {

        try {

            ApiAdapter.getInstance(activity);
            callEventLike(eventID, likeValue);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void callEventLike(String eventID, String likeValue) {

        userSession = new UserSession(activity);
        //   Progress.start(activity);


        Call<RequestSuccessModel> callEvent;


        callEvent = ApiAdapter.getApiService().eventlike("like_feed", userSession.getUserID(), eventID, likeValue);

        callEvent.enqueue(new Callback<RequestSuccessModel>() {
            @Override
            public void onResponse(Call<RequestSuccessModel> call, Response<RequestSuccessModel> response) {

                //  Progress.stop();
                try {
                    //getting whole data from response
                    RequestSuccessModel eventsModel = response.body();

                    // ArrayList<EventsModel.EventList> eventLists = eventsModel.getEventList();

                    String message = eventsModel.getMsg();


                    if (eventsModel.getStatus() == 1 && eventsModel.getStatusCode() == 1) {
                        // eventView.getEventSuccessfull(eventLists);
                        // getAllEventList();
                    } else {
                        eventView.getFriendEventUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    eventView.getFriendEventUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RequestSuccessModel> call, Throwable t) {
                // Progress.stop();
                eventView.getFriendEventUnSuccessful(activity.getString(R.string.server_error));

            }

        });

    }

    private void getAllEventList(String friendID) {

        userSession = new UserSession(activity);
        Progress.start(activity);


        Call<MyFriendEventModel> callEvent;


        callEvent = ApiAdapter.getApiService().friendEvent("get_user_event_list",friendID);

        callEvent.enqueue(new Callback<MyFriendEventModel>() {
            @Override
            public void onResponse(Call<MyFriendEventModel> call, Response<MyFriendEventModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    MyFriendEventModel eventsModel = response.body();

                    ArrayList<MyFriendEventModel.EventList> eventLists = eventsModel.getEventList();

                    String message = eventsModel.getMsg();


                    if (eventsModel.getStatus() == 1 && eventsModel.getStatusCode() == 1) {
                        eventView.getFriendEventSuccessful(eventLists);


                    } else {
                        eventView.getFriendEventUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    eventView.getFriendEventUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<MyFriendEventModel> call, Throwable t) {
                Progress.stop();
                eventView.getFriendEventUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}