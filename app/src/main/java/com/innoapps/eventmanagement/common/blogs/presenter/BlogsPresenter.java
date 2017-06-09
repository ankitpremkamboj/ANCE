package com.innoapps.eventmanagement.common.blogs.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.events.view.EventView;

/**
 * Created by ankit on 3/20/2017.
 */

public interface BlogsPresenter {

    void callGetBlogList(BlogsView blogsView, Activity activity);

    void callGetBlogDetails(BlogsView blogsView, Activity activity,String blogId);
    void postBlogData(BlogsView blogsView,Activity activity,String blogTitle,String blogDescription,String blogImage);

}
