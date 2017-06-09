package com.innoapps.eventmanagement.common.friends;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenter;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenterImpl;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.helper.SnackNotifyHelper;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FriendsActivity extends AppCompatActivity implements FriendsView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;
    @InjectView(R.id.img_back)
    ImageView img_back;
    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;


    @InjectView(R.id.edt_search)
    EditText edt_search;
    @InjectView(R.id.cross)
    ImageView cross;

    @InjectView(R.id.img_search)
    ImageView img_search;
    FriendsPresenter friendsPresenter;
    String searchValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.inject(this);
        setFont();
        friendsPresenter = new FriendsPresenterImpl();
        friendsPresenter.callGetFriendList(this, FriendsActivity.this, searchValue);

        txt_header_title.setText(getString(R.string.add_remove));
        txt_header_title.setVisibility(View.VISIBLE);

        edt_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //do here your stuff f
                    searchValue = edt_search.getText().toString().trim();
                    callSearchMethod(searchValue);
                    Utils.hideKeyboardIfOpen(FriendsActivity.this);

                    return true;
                }
                return false;
            }
        });


    }

    private void setFont() {
        edt_search.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        txt_header_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
    }

    private void callSearchMethod(String searchValue) {
        friendsPresenter.callGetFriendList(this, FriendsActivity.this, searchValue);
    }

    @OnClick(R.id.img_back)
    public void back() {
        // edt_search.setVisibility(View.GONE);
        // txt_header_title.setVisibility(View.VISIBLE);
        finish();
    }

    @OnClick(R.id.cross)
    public void cross() {
        edt_search.setVisibility(View.GONE);
        txt_header_title.setVisibility(View.VISIBLE);
        img_back.setVisibility(View.VISIBLE);
        cross.setVisibility(View.GONE);
    }

    @OnClick(R.id.img_search)
    public void search() {
        edt_search.setVisibility(View.VISIBLE);
        txt_header_title.setVisibility(View.GONE);
        img_back.setVisibility(View.GONE);
        cross.setVisibility(View.VISIBLE);
        //  finish();
    }

    @Override
    public void getFriendsSuccessfull(ArrayList<FriendsModel.UserList> userFriendLists) {
        Log.e("Friend List Data", "" + userFriendLists.size());

        FriendAdapter friendAdapter = new FriendAdapter(FriendsActivity.this, userFriendLists, friendsPresenter);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(FriendsActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(FriendsActivity.this));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFriend.setHasFixedSize(true);
        recyclerViewFriend.setItemViewCacheSize(20);
        recyclerViewFriend.setDrawingCacheEnabled(true);
        recyclerViewFriend.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
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

    public void getFriendsRequestSuccessful(String message, FriendAdapter.Holder holder) {
        SnackNotifyHelper.showMessage(this, message, coordinateLayout);


    }
}
