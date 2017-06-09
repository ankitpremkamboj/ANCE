package com.innoapps.eventmanagement.common.myfriends.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenter;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.myfriends.model.MyFriendsModel;
import com.innoapps.eventmanagement.common.myfriends.view.MyFriendsView;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 3/8/2017.
 */

public class MyFriendsPresenterImpl implements MyFriendsPresenter {

    Activity activity;
    MyFriendsView friendsView;
    UserSession userSession;

    @Override
    public void callGetMyFriendList(MyFriendsView friendsView, Activity activity) {
        try {
            userSession = new UserSession(activity);
            this.activity = activity;
            this.friendsView = friendsView;
            ApiAdapter.getInstance(activity);
            getAllFriendList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void callSendFriendRequest(String friendId, String activeValue) {
        try {
            userSession = new UserSession(activity);
            this.activity = activity;
            this.friendsView = friendsView;
            ApiAdapter.getInstance(activity);
            sendFriendRequest(friendId,activeValue);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }


    private void getAllFriendList() {


        Progress.start(activity);


        Call<MyFriendsModel> callFriend;


        callFriend = ApiAdapter.getApiService().getMyFriend("get_user_frnd_list", userSession.getUserID());

        callFriend.enqueue(new Callback<MyFriendsModel>() {
            @Override
            public void onResponse(Call<MyFriendsModel> call, Response<MyFriendsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    MyFriendsModel friendsModel = response.body();

                    ArrayList<MyFriendsModel.UserFriendList>  userFriendLists = friendsModel.getUserFriendList();

                    String message = friendsModel.getMsg();


                    if (friendsModel.getStatus() == 1 && friendsModel.getStatusCode() == 1) {
                        friendsView.getFriendsSuccessfull(userFriendLists);


                    } else {
                        friendsView.getFriendsUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    friendsView.getFriendsUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<MyFriendsModel> call, Throwable t) {
                Progress.stop();
                friendsView.getFriendsUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }



    public void sendFriendRequest(String friendId, String activeValue) {


        Progress.start(activity);


        Call<RequestSuccessModel> callFriendRequest;


        callFriendRequest = ApiAdapter.getApiService().sendFriendRequest("action_user_friend_request", userSession.getUserID(),activeValue,friendId);

        callFriendRequest.enqueue(new Callback<RequestSuccessModel>() {
            @Override
            public void onResponse(Call<RequestSuccessModel> call, Response<RequestSuccessModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    RequestSuccessModel friendsModel = response.body();



                    String message = friendsModel.getMsg();


                    if (friendsModel.getStatus() == 1 && friendsModel.getStatusCode() == 1) {
                        friendsView.getFriendsRequestSuccessful(message);


                    } else {
                        friendsView.getFriendsUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    friendsView.getFriendsUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RequestSuccessModel> call, Throwable t) {
                Progress.stop();
                friendsView.getFriendsUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }


}
