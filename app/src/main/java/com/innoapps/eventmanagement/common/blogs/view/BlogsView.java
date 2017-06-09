package com.innoapps.eventmanagement.common.blogs.view;

import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.events.model.EventsModel;

import java.util.ArrayList;

/**
 * Created by ankit on 3/20/2017.
 */

public interface BlogsView {

    void getBlogSuccessfull(ArrayList<BlogsModel.BlogList> blogLists);

    void getBlogDetailsSuccessfull(ArrayList<DetailsBlogsModel.BlogList> blogLists);

    void getBlogUnSuccessful(String message);

    void postBlogSuccessful(String message);

    void blogInternetError();
}
