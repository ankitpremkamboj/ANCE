package com.innoapps.eventmanagement.common.blogs.presenter;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.events.view.EventView;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.postevents.model.PostEventModel;
import com.innoapps.eventmanagement.common.postevents.view.PostEventsView;
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

public class BlogPresenterImpl implements BlogsPresenter {


    Activity activity;
    BlogsView blogsView;

    UserSession userSession;


    @Override
    public void callGetBlogList(BlogsView blogsView, Activity activity) {
        try {
            this.activity = activity;
            this.blogsView = blogsView;

            ApiAdapter.getInstance(activity);
            getAllBlogList();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void callGetBlogDetails(BlogsView blogsView, Activity activity, String blogId) {

        try {
            this.activity = activity;
            this.blogsView = blogsView;

            ApiAdapter.getInstance(activity);
            getAllBlogDetailsList(blogId);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }


    private void getAllBlogList() {


        Progress.start(activity);


        Call<BlogsModel> callBlogs;


        callBlogs = ApiAdapter.getApiService().blog("get_blog_list");

        callBlogs.enqueue(new Callback<BlogsModel>() {
            @Override
            public void onResponse(Call<BlogsModel> call, Response<BlogsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    BlogsModel blogsModel = response.body();

                    ArrayList<BlogsModel.BlogList> blogList = blogsModel.getBlogList();

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
            public void onFailure(Call<BlogsModel> call, Throwable t) {
                Progress.stop();
                blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }


    private void getAllBlogDetailsList(String blogId) {


        Progress.start(activity);


        Call<DetailsBlogsModel> callBlogs;


        callBlogs = ApiAdapter.getApiService().blogDetails("get_blog_list", blogId);

        callBlogs.enqueue(new Callback<DetailsBlogsModel>() {
            @Override
            public void onResponse(Call<DetailsBlogsModel> call, Response<DetailsBlogsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    DetailsBlogsModel blogsModel = response.body();

                    ArrayList<DetailsBlogsModel.BlogList> blogList = blogsModel.getBlogList();

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
            public void onFailure(Call<DetailsBlogsModel> call, Throwable t) {
                Progress.stop();
                blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }

    @Override
    public void postBlogData(BlogsView blogsView, Activity activity, String blogTitle, String blogDescription, String blogImage) {

        this.blogsView = blogsView;
        this.activity = activity;
        userSession = new UserSession(activity);
        try {
            ApiAdapter.getInstance(activity);
            callBlogApi(blogTitle, blogDescription, blogImage);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }


    }


    private void callBlogApi(String blogTitle, String blogDescription, String blogImage) {


        Progress.start(activity);


        Call<RequestSuccessModel> callBlog;


        //prepare image file
        File file = new File(blogImage);
        RequestBody actionRequest = RequestBody.create(MediaType.parse("text/plain"), "add_blog");

        RequestBody blogTitleRequest = RequestBody.create(MediaType.parse("text/plain"), blogTitle);
        RequestBody blogDescriptionRequest = RequestBody.create(MediaType.parse("text/plain"), blogDescription);

        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userSession.getUserID());


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part imageFileBody = MultipartBody.Part.createFormData("blog_photo", file.getName(), requestBody);


        callBlog = ApiAdapter.getApiService().postBlog(actionRequest, userIdRequest, blogTitleRequest, blogDescriptionRequest, imageFileBody);

        callBlog.enqueue(new Callback<RequestSuccessModel>() {
            @Override
            public void onResponse(Call<RequestSuccessModel> call, Response<RequestSuccessModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    RequestSuccessModel userProfileModel = response.body();

                    String message = userProfileModel.getMsg();

                    if (userProfileModel.getStatus() == 1 && userProfileModel.getStatusCode() == 1) {

                        blogsView.postBlogSuccessful(message);

                    } else {
                        blogsView.getBlogUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RequestSuccessModel> call, Throwable t) {
                Progress.stop();
                blogsView.getBlogUnSuccessful(activity.getString(R.string.server_error));
            }

        });
    }


}
