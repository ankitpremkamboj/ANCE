package com.innoapps.eventmanagement.common.events.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.events.view.EventView;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 3/6/2017.
 */

public class EventPresenterImpl implements EventPresenter {
    Activity activity;
    EventView eventView;

    UserSession userSession;

    @Override
    public void callGetEventsList(EventView eventView, Activity activity, int page) {
        try {
            this.activity = activity;
            this.eventView = eventView;
            ApiAdapter.getInstance(activity);
            getAllEventList(page);
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
                        eventView.getEventUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    eventView.getEventUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RequestSuccessModel> call, Throwable t) {
                // Progress.stop();
                eventView.getEventUnSuccessful(activity.getString(R.string.server_error));

            }

        });

    }

    private void getAllEventList(int page) {

        userSession = new UserSession(activity);
       //  Progress.start(activity);


        Call<EventsModel> callEvent;


        callEvent = ApiAdapter.getApiService().event("event_list", userSession.getUserID(), String.valueOf(page), "20");

        callEvent.enqueue(new Callback<EventsModel>() {
            @Override
            public void onResponse(Call<EventsModel> call, Response<EventsModel> response) {

                //  Progress.stop();
                try {
                    //getting whole data from response
                    EventsModel eventsModel = response.body();

                    ArrayList<EventsModel.EventList> eventLists = eventsModel.getEventList();

                    String message = eventsModel.getMsg();


                    if (eventsModel.getStatus() == 1 && eventsModel.getStatusCode() == 1) {
                        eventView.getEventSuccessfull(eventLists);


                    } else {
                        eventView.getEventUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    eventView.getEventUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<EventsModel> call, Throwable t) {
                //Progress.stop();
                eventView.getEventUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}
