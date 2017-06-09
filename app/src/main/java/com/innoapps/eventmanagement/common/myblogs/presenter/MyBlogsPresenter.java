package com.innoapps.eventmanagement.common.myblogs.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.myblogs.view.MyBlogsView;

/**
 * Created by ankit on 3/20/2017.
 */

public interface MyBlogsPresenter {

    void callGetBlogList(MyBlogsView blogsView, Activity activity);
    void callGetBlogDetails(MyBlogsView blogsView, Activity activity, String blogId);

}
