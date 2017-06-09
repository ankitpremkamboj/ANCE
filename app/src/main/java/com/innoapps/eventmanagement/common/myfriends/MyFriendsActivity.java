package com.innoapps.eventmanagement.common.myfriends;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenter;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenterImpl;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.helper.SnackNotifyHelper;
import com.innoapps.eventmanagement.common.myfriends.adapter.MyFriendAdapter;
import com.innoapps.eventmanagement.common.myfriends.model.MyFriendsModel;
import com.innoapps.eventmanagement.common.myfriends.presenter.MyFriendsPresenter;
import com.innoapps.eventmanagement.common.myfriends.presenter.MyFriendsPresenterImpl;
import com.innoapps.eventmanagement.common.myfriends.view.MyFriendsView;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyFriendsActivity extends AppCompatActivity implements MyFriendsView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;
   @InjectView(R.id.img_back)
    ImageView img_back;
    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;
   /* @InjectView(R.id.input_layout_search)
    TextInputLayout input_layout_search;
    @InjectView(R.id.input_search)
    EditText input_search;*/
    @InjectView(R.id.img_search)
    ImageView img_search;
    MyFriendsPresenter friendsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.inject(this);
        img_search.setVisibility(View.GONE);
        friendsPresenter = new MyFriendsPresenterImpl();
        friendsPresenter.callGetMyFriendList(this, MyFriendsActivity.this);
        txt_header_title.setText(getString(R.string.my_friend));

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @Override
    public void getFriendsSuccessfull(ArrayList<MyFriendsModel.UserFriendList> userFriendLists) {
        Log.e("Friend List Data", "" + userFriendLists.size());

        MyFriendAdapter friendAdapter = new MyFriendAdapter(MyFriendsActivity.this, userFriendLists,friendsPresenter);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(MyFriendsActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(MyFriendsActivity.this));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFriend.setAdapter(friendAdapter);

    }

    @Override
    public void getFriendsUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void FriendsInternetError() {

    }

    @Override

    public void getFriendsRequestSuccessful(String message) {
        SnackNotifyHelper.showMessage(this, message, coordinateLayout);
    }
}
