package com.innoapps.eventmanagement.common.blogs;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.adapter.BlogsAdapter;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogPresenterImpl;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogsPresenter;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.events.adapter.EventAdapter;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ankit on 3/20/2017.
 */

public class BlogsFragment extends Fragment implements BlogsView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    BlogsPresenter blogsPresenter;
    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blogs_fragment, container, false);

        ButterKnife.inject(this, view);
        blogsPresenter = new BlogPresenterImpl();
        blogsPresenter.callGetBlogList(this, getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void getBlogSuccessfull(ArrayList<BlogsModel.BlogList> blogLists) {

        BlogsAdapter blogsAdapter = new BlogsAdapter(getActivity(), blogLists, this);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFriend.setHasFixedSize(true);
        recyclerViewFriend.setItemViewCacheSize(20);
        recyclerViewFriend.setDrawingCacheEnabled(true);
        recyclerViewFriend.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewFriend.setAdapter(blogsAdapter);
        //blogsAdapter.notifyDataSetChanged();


    }

    @Override
    public void getBlogDetailsSuccessfull(ArrayList<DetailsBlogsModel.BlogList> blogLists) {

    }


    @Override
    public void getBlogUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);

    }

    @Override
    public void postBlogSuccessful(String message) {

    }

    @Override
    public void blogInternetError() {

    }

}
