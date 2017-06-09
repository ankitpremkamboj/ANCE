package com.innoapps.eventmanagement.common.photofeed.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.photofeed.model.PhotoFeedModel;
import com.innoapps.eventmanagement.common.photofeed.view.PhotoFeedView;
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
 * Created by ankit on 4/4/2017.
 */

public class PhotoFeedPresenterImpl implements PhotoFeedPresenter {
    PhotoFeedView photoFeedView;
    Activity activity;
    UserSession userSession;

    @Override
    public void callGetPhotoFeedList(PhotoFeedView photoFeedView, Activity activity) {
        try {
            this.activity = activity;
            this.photoFeedView = photoFeedView;
            ApiAdapter.getInstance(activity);
            getPhotoFeed();
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void callUploadPhotoFeedList(PhotoFeedView photoFeedView, Activity activity, String imagePath) {
        try {
            this.activity = activity;
            this.photoFeedView = photoFeedView;
            ApiAdapter.getInstance(activity);
            uploadPhotoFeed(imagePath);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void uploadPhotoFeed(String imagePath) {

        Progress.start(activity);

        userSession = new UserSession(activity);

        Call<RequestSuccessModel> callPhotoFeed;


        RequestBody actionRequest = RequestBody.create(MediaType.parse("text/plain"), "add_gallery");
        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userSession.getUserID());

        RequestBody imageNameRequest = RequestBody.create(MediaType.parse("text/plain"), "Gallery");

///Uploading for image
        File file;
        RequestBody requestBody;
        MultipartBody.Part imageFileBody = null;
       /* if (!file.exists()) {
        } else {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageFileBody = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestBody);
        }
*/

        if (imagePath.equalsIgnoreCase("")) {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            imageFileBody = MultipartBody.Part.createFormData("gallery_image", "path", requestBody);
        } else {
            file = new File(imagePath);
            if (!file.exists()) {

            } else {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                imageFileBody = MultipartBody.Part.createFormData("gallery_image", file.getName(), requestBody);
            }
        }

        callPhotoFeed = ApiAdapter.getApiService().uploadPhotoFeed(actionRequest, userIdRequest, imageNameRequest, imageFileBody);

        callPhotoFeed.enqueue(new Callback<RequestSuccessModel>() {
            @Override
            public void onResponse(Call<RequestSuccessModel> call, Response<RequestSuccessModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    RequestSuccessModel eventsModel = response.body();


                    String message = eventsModel.getMsg();


                    if (eventsModel.getStatus() == 1 && eventsModel.getStatusCode() == 1) {
                        photoFeedView.getPhotoFeedUploadSuccessful(message);
                    } else {
                        photoFeedView.getPhotoFeedUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    photoFeedView.getPhotoFeedUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RequestSuccessModel> call, Throwable t) {
                Progress.stop();
                photoFeedView.getPhotoFeedUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }

    private void getPhotoFeed() {
        Progress.start(activity);


        Call<PhotoFeedModel> callPhotoFeed;


        callPhotoFeed = ApiAdapter.getApiService().getPhotoFeed("get_gallery_list");

        callPhotoFeed.enqueue(new Callback<PhotoFeedModel>() {
            @Override
            public void onResponse(Call<PhotoFeedModel> call, Response<PhotoFeedModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    PhotoFeedModel eventsModel = response.body();

                    ArrayList<PhotoFeedModel.GalleryList> eventLists = eventsModel.getGalleryList();

                    String message = eventsModel.getMsg();


                    if (eventsModel.getStatus() == 1 && eventsModel.getStatusCode() == 1) {
                        photoFeedView.getPhotoFeedSuccessfull(eventLists);

                    } else {
                        photoFeedView.getPhotoFeedUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    photoFeedView.getPhotoFeedUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<PhotoFeedModel> call, Throwable t) {
                Progress.stop();
                photoFeedView.getPhotoFeedUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}
