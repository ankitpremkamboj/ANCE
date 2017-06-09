package com.innoapps.eventmanagement.common.myfriendevent;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.events.presenter.EventPresenterImpl;
import com.innoapps.eventmanagement.common.myfriendevent.adapter.MyFriendEventAdapter;
import com.innoapps.eventmanagement.common.myfriendevent.model.MyFriendEventModel;
import com.innoapps.eventmanagement.common.myfriendevent.presenter.MyFriendEventPresenter;
import com.innoapps.eventmanagement.common.myfriendevent.presenter.MyFriendEventPresenterImpl;
import com.innoapps.eventmanagement.common.myfriendevent.view.MyFriendEventView;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ankit on 3/29/2017.
 */

public class MyFriendEventActivity extends AppCompatActivity implements MyFriendEventView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    @InjectView(R.id.recyclerViewEvent)
    RecyclerView recyclerViewEvent;
    /* @InjectView(R.id.swipeToRefresh)
     SwipeRefreshLayout swipeToRefresh;*/
    MyFriendEventPresenter eventPresenter;
    @InjectView(R.id.img_search)
    ImageView img_search;
    MyFriendEventAdapter eventAdapter;

    LinearLayoutManager layoutManager;

    @InjectView(R.id.img_back)
    ImageView img_back;

    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;


    ArrayList<MyFriendEventModel.EventList> eventLisdata;

    boolean isEndReached;
    boolean mIsLoading;
    int page = 0;
    boolean isRefreshingData = false;
    //SwipeRefreshLayout
    String friendID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfriend_event_post);
        ButterKnife.inject(this);
        img_search.setVisibility(View.GONE);
        txt_header_title.setText("My Post");
        eventPresenter = new MyFriendEventPresenterImpl();
        intentGet();
        callApi();

       /* swipeToRefresh.setColorSchemeResources(
                R.color.color_black,
                R.color.color_black,
                R.color.color_black);

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshingData = true;
                callApi();

            }
        });
*/

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    private void initializeRecyclerView() {
        // eventLisdata = new ArrayList<>();

        recyclerViewEvent.setHasFixedSize(true);
        recyclerViewEvent.setItemViewCacheSize(20);
        recyclerViewEvent.setDrawingCacheEnabled(true);
        recyclerViewEvent.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEvent.setLayoutManager(layoutManager);
        // eventAdapter = new NewAdapter(getActivity(), eventLisdata, eventPresenter, recyclerViewEvent, layoutManager);
        eventAdapter = new MyFriendEventAdapter(this, eventLisdata, eventPresenter);
        recyclerViewEvent.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvent.setAdapter(eventAdapter);
        // recyclerViewEvent.addOnScrollListener(mRecyclerViewOnScrollListener);


       /* eventAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
             *//* wallpaperImagesList.add(null);
                mAdapter.notifyItemInserted(wallpaperImagesList.size() - 1);
                ++pageNumber;

                getWebServiceData();*//*

                getPaginationData(false);
                eventAdapter.setLoaded();
            }
        });
*/
    }

    private void intentGet() {
        if (getIntent() != null) {
            friendID = getIntent().getStringExtra("FriendID");
        }
    }

    private void callApi() {

        page = 1;
        eventPresenter.callGetFriendEventsList(this, this, friendID);
    }


    @Override
    public void getFriendEventSuccessful(ArrayList<MyFriendEventModel.EventList> eventLists) {
        this.eventLisdata = eventLists;
        Log.e("Data", "dataaaaa");
        initializeRecyclerView();
      /*  if (isRefreshingData) {
            if (eventAdapter != null)
                eventAdapter.clearData();
            isRefreshingData = false;
        }
        if (swipeToRefresh.isRefreshing())
            swipeToRefresh.setRefreshing(false);*/

       /* ArrayList<MyFriendEventModel.EventList> arrayListJobs = eventLists;
        if (arrayListJobs != null) {
            if (arrayListJobs.size() < 10) {
                isEndReached = true;
            } else {
                isEndReached = false;
            }
            eventAdapter.updateData(arrayListJobs);
        }*/

     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsLoading = false;
                // eventAdapter.setLoaded();
            }
        }, 1000);
*/

    }

    @Override
    public void getFriendEventUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);

    }

    @Override
    public void eventInternetError() {

    }
}
