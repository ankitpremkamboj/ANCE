package com.innoapps.eventmanagement.common.events;

import android.content.ContentValues;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.events.adapter.EventAdapter;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.events.presenter.EventPresenter;
import com.innoapps.eventmanagement.common.events.presenter.EventPresenterImpl;
import com.innoapps.eventmanagement.common.events.view.EventView;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Ankit on 3/5/2017.
 */

public class EventsFragment extends Fragment implements EventView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    @InjectView(R.id.recyclerViewEvent)
    RecyclerView recyclerViewEvent;
    @InjectView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;
    EventPresenter eventPresenter;


    EventAdapter eventAdapter;
    //NewAdapter eventAdapter;

    LinearLayoutManager layoutManager;

    ArrayList<EventsModel.EventList> eventLisdata;

    boolean isEndReached;
    boolean mIsLoading;
    int page = 0;
    boolean isRefreshingData = false;
    //SwipeRefreshLayout
    UserSession userSession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_content, container, false);

        ButterKnife.inject(this, view);
        userSession = new UserSession(getActivity());

        swipeToRefresh.setColorSchemeResources(
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eventPresenter = new EventPresenterImpl();

        initializeRecyclerView();

        callApi();


    }

    private void initializeRecyclerView() {
        eventLisdata = new ArrayList<>();

        recyclerViewEvent.setHasFixedSize(true);
        recyclerViewEvent.setItemViewCacheSize(20);
        recyclerViewEvent.setDrawingCacheEnabled(true);
        recyclerViewEvent.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewEvent.setLayoutManager(layoutManager);
        // eventAdapter = new NewAdapter(getActivity(), eventLisdata, eventPresenter, recyclerViewEvent, layoutManager);
        eventAdapter = new EventAdapter(getActivity(), eventLisdata, eventPresenter);
        recyclerViewEvent.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvent.setAdapter(eventAdapter);
        recyclerViewEvent.addOnScrollListener(mRecyclerViewOnScrollListener);


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

    @Override
    public void getEventSuccessfull(final ArrayList<EventsModel.EventList> eventLists) {
        this.eventLisdata = eventLists;
        if (isRefreshingData) {
            if (eventAdapter != null)
                eventAdapter.clearData();
            isRefreshingData = false;
        }
        if (swipeToRefresh.isRefreshing())
            swipeToRefresh.setRefreshing(false);

        ArrayList<EventsModel.EventList> arrayListJobs = eventLists;
        if (arrayListJobs != null) {
            if (arrayListJobs.size() < 10) {
                isEndReached = true;
            } else {
                isEndReached = false;
            }
            eventAdapter.updateData(arrayListJobs);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsLoading = false;
                // eventAdapter.setLoaded();
            }
        }, 1000);


    }

    @Override
    public void getEventUnSuccessful(String message) {
        //  SnackNotify.showMessage(message, coordinateLayout);
        mIsLoading = false;
        isEndReached = false;
        // progressBar.setVisibility(View.GONE);
        if (isRefreshingData) {
            isRefreshingData = false;
        }
        if (swipeToRefresh.isRefreshing())
            swipeToRefresh.setRefreshing(false);


    }

    @Override
    public void eventInternetError() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (userSession.isRefresh()) {
            isRefreshingData = true;
            callApi();
            userSession.refresh(false);
        }

    }
    // implement paging
    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView,
                                         int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!mIsLoading && !isEndReached) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    getPaginationData(false);
                }
            }
        }
    };

    private void callApi() {

        page = 1;
        eventPresenter.callGetEventsList(this, getActivity(), page);
    }


    private void getPaginationData(boolean isRetry) {
        mIsLoading = true;
        if (isRetry) {
            if (page == 0) {
                page = 1;
            }
        } else {
            page = page + 1;
        }
        eventPresenter.callGetEventsList(this, getActivity(), page);

    }


}
