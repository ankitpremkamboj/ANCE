package com.innoapps.eventmanagement.common.myblogs.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogsPresenter;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.myblogs.model.MyBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.model.MyDetailsBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.view.MyBlogsView;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/20/2017.
 */

public class MyBlogPresenterImpl implements MyBlogsPresenter {


    Activity activity;
    MyBlogsView blogsView;

    UserSession userSession;


    @Override
    public void callGetBlogList(MyBlogsView blogsView, Activity activity) {
        try {
            this.activity = activity;
            this.blogsView = blogsView;

            ApiAdapter.getInstance(activity);
            getAllMyBlogList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void callGetBlogDetails(MyBlogsView blogsView, Activity activity, String blogId) {

        try {
            this.activity = activity;
            this.blogsView = blogsView;
            ApiAdapter.getInstance(activity);
            getAllBlogDetailsList(blogId);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }


    private void getAllMyBlogList() {


        Progress.start(activity);
        userSession = new UserSession(activity);

        Call<MyBlogsModel> callBlogs;

        callBlogs = ApiAdapter.getApiService().myblog("get_my_blog_list", userSession.getUserID());

        callBlogs.enqueue(new Callback<MyBlogsModel>() {
            @Override
            public void onResponse(Call<MyBlogsModel> call, Response<MyBlogsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    MyBlogsModel blogsModel = response.body();

                    ArrayList<MyBlogsModel.BlogList> blogList = blogsModel.getBlogList();

                    String message = blogsModel.getMsg();


                    if (blogsModel.getStatus() == 1 && blogsModel.getStatusCode() == 1) {
                         blogsView.getBlogSuccessfull(blogList);


                    } else {
                        blogsView.getBlogUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<MyBlogsModel> call, Throwable t) {
                Progress.stop();
                blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }


    private void getAllBlogDetailsList(String blogId) {


        Progress.start(activity);
        userSession = new UserSession(activity);

        Call<MyDetailsBlogsModel> callBlogs;


        callBlogs = ApiAdapter.getApiService().myblogDetails("get_my_blog_list", userSession.getUserID(), blogId);

        callBlogs.enqueue(new Callback<MyDetailsBlogsModel>() {
            @Override
            public void onResponse(Call<MyDetailsBlogsModel> call, Response<MyDetailsBlogsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    MyDetailsBlogsModel blogsModel = response.body();

                    ArrayList<MyDetailsBlogsModel.BlogList> blogList = blogsModel.getBlogList();

                    String message = blogsModel.getMsg();


                    if (blogsModel.getStatus() == 1 && blogsModel.getStatusCode() == 1) {
                        blogsView.getBlogDetailsSuccessfull(blogList);


                    } else {
                        blogsView.getBlogUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<MyDetailsBlogsModel> call, Throwable t) {
                Progress.stop();
                blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}
