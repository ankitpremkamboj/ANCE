package com.innoapps.eventmanagement.common.friends.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 3/8/2017.
 */

public class FriendsPresenterImpl implements FriendsPresenter {

    Activity activity;
    FriendsView friendsView;
    UserSession userSession;

    @Override
    public void callGetFriendList(FriendsView friendsView, Activity activity,String searchValue) {
        try {
            userSession = new UserSession(activity);
            this.activity = activity;
            this.friendsView = friendsView;
            ApiAdapter.getInstance(activity);
            getAllFriendList(searchValue);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void callSendFriendRequest(String friendId, String activeValue, FriendAdapter.Holder holder) {
        try {
            userSession = new UserSession(activity);
            this.activity = activity;
            this.friendsView = friendsView;
            ApiAdapter.getInstance(activity);
            sendFriendRequest(friendId,activeValue,holder);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }


    private void getAllFriendList(String searchValue) {


        Progress.start(activity);


        Call<FriendsModel> callFriend;


        callFriend = ApiAdapter.getApiService().getFriend("search_user_by_name_email", userSession.getUserID(),searchValue);

        callFriend.enqueue(new Callback<FriendsModel>() {
            @Override
            public void onResponse(Call<FriendsModel> call, Response<FriendsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    FriendsModel friendsModel = response.body();

                    ArrayList<FriendsModel.UserList>  userFriendLists = friendsModel.getUserList();

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
            public void onFailure(Call<FriendsModel> call, Throwable t) {
                Progress.stop();
                friendsView.getFriendsUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }



    public void sendFriendRequest(String friendId, String activeValue, final FriendAdapter.Holder holder) {


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
                        friendsView.getFriendsRequestSuccessful(message,holder);


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
