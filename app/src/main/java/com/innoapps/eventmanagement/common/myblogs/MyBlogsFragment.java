package com.innoapps.eventmanagement.common.myblogs;


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
import com.innoapps.eventmanagement.common.myblogs.adapter.MyBlogsAdapter;
import com.innoapps.eventmanagement.common.myblogs.model.MyBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.model.MyDetailsBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.presenter.MyBlogPresenterImpl;
import com.innoapps.eventmanagement.common.myblogs.presenter.MyBlogsPresenter;
import com.innoapps.eventmanagement.common.myblogs.view.MyBlogsView;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/20/2017.
 */

public class MyBlogsFragment extends Fragment implements MyBlogsView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    MyBlogsPresenter myBlogsPresenter;
    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_blogs_fragment, container, false);

        ButterKnife.inject(this, view);
        myBlogsPresenter = new MyBlogPresenterImpl();
        myBlogsPresenter.callGetBlogList(this, getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void getBlogSuccessfull(ArrayList<MyBlogsModel.BlogList> blogLists) {

        MyBlogsAdapter blogsAdapter = new MyBlogsAdapter(getActivity(), blogLists, this);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFriend.setHasFixedSize(true);
        recyclerViewFriend.setItemViewCacheSize(20);
        recyclerViewFriend.setDrawingCacheEnabled(true);
        recyclerViewFriend.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewFriend.setAdapter(blogsAdapter);



    }

    @Override
    public void getBlogDetailsSuccessfull(ArrayList<MyDetailsBlogsModel.BlogList> blogLists) {

    }


    @Override
    public void getBlogUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);

    }



    @Override
    public void blogInternetError() {
    }

}
