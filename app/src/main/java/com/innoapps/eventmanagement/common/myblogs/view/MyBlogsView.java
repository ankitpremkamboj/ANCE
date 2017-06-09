package com.innoapps.eventmanagement.common.myblogs.view;

import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.model.MyBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.model.MyDetailsBlogsModel;

import java.util.ArrayList;

/**
 * Created by ankit on 3/20/2017.
 */

public interface MyBlogsView {

    void getBlogSuccessfull(ArrayList<MyBlogsModel.BlogList> blogLists);

    void getBlogDetailsSuccessfull(ArrayList<MyDetailsBlogsModel.BlogList> blogLists);

    void getBlogUnSuccessful(String message);

    void blogInternetError();
}
