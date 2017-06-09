package com.innoapps.eventmanagement.common.postevents.presenter;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.postevents.model.PostEventModel;
import com.innoapps.eventmanagement.common.postevents.view.PostEventsView;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.signup.model.RegisterUser;
import com.innoapps.eventmanagement.common.signup.view.RegisterView;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/16/2017.
 */

public class PostEventsPresenterImpl implements PostEventsPresenter {

    PostEventsView postEventsView;
    Activity activity;
    UserSession userSession;

    public PostEventsPresenterImpl(PostEventsView postEventsView, Activity activity) {
        this.postEventsView = postEventsView;
        this.activity = activity;
        //  this.sliderProfileUpdate = sliderProfileUpdate;
        // loginIntracter = new LoginIntracterImpl();
        userSession=new UserSession(activity);
    }
    //Activity activity, UserProfileView userProfileView, String name,String email, String mobileNumber, String companyName, String password,String profile_image


    @Override
    public void validatePostEvent(Activity activity, PostEventsView postEventsView, String event_title, String description,  String eventImage,String latitude, String longitude) {
        if (validateCredential(activity, postEventsView, event_title, description, eventImage,latitude,longitude)) {
            //  name, email, mobile, company_name, password, cameraImageFilePath
            callRegisterApi(activity, postEventsView, event_title, description, eventImage,latitude,longitude);


        }
    }
    public boolean validateCredential(Activity activity, PostEventsView postEventsView, String event_title, String description, String eventImage,String latitude, String longitude) {

        if (TextUtils.isEmpty(event_title)) {
            postEventsView.onEventTitleError(activity.getString(R.string.empty_event_title));
            return false;
        } else if (TextUtils.isEmpty(description)) {
            postEventsView.onEventDescriptionError(activity.getString(R.string.empty_event_description));
            return false;
        } else if (TextUtils.isEmpty(eventImage)) {
            postEventsView.onEventImageError(activity.getString(R.string.empty_profile_image));

            return false;
        }  else if (TextUtils.isEmpty(eventImage)) {
            postEventsView.onEventImageError(activity.getString(R.string.empty_profile_image));

            return false;
        }else {
            return true;
        }
    }



    private void callRegisterApi(Activity activity, PostEventsView postEventsView, String event_title, String description, String eventImage,String latitude, String longitude) {
        try {
            ApiAdapter.getInstance(activity);
            callRegisterMethod(activity, postEventsView, event_title, description, eventImage,latitude,longitude);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void callRegisterMethod(final Activity activity, final PostEventsView postEventsView, String event_title, String description, String eventImage,String latitude, String longitude) {


        Progress.start(activity);


        Call<PostEventModel> callLogin;

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = telephonyManager.getDeviceId();

        //prepare image file
        File file = new File(eventImage);
        RequestBody actionRequest = RequestBody.create(MediaType.parse("text/plain"), "add_event");

        RequestBody eventTitleRequest = RequestBody.create(MediaType.parse("text/plain"), event_title);
        RequestBody eventDescriptionRequest = RequestBody.create(MediaType.parse("text/plain"), description);

        RequestBody latRequest = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody lngRequest = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userSession.getUserID());



        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part imageFileBody = MultipartBody.Part.createFormData("event_pic", file.getName(), requestBody);

//@Part("action") RequestBody action, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("mobile")
// RequestBody mobile, @Part("company_name") RequestBody company_name, @Part("password") RequestBody password, @Part MultipartBody.Part imageFile,
// @Part("device_key") RequestBody device_key, @Part("device_id") RequestBody device_i
        // callLogin = ApiAdapter.getApiService().register("user_register", name, email, mobileNumber, companyName, password, imageFileBody, "android", deviceid);

        callLogin = ApiAdapter.getApiService().postEvent(actionRequest,userIdRequest,eventTitleRequest, eventDescriptionRequest, imageFileBody,latRequest,lngRequest);

        callLogin.enqueue(new Callback<PostEventModel>() {
            @Override
            public void onResponse(Call<PostEventModel> call, Response<PostEventModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    PostEventModel userProfileModel = response.body();

                    String message = userProfileModel.getMsg();

                    if (userProfileModel.getStatus() == 1 && userProfileModel.getStatusCode() == 1) {


                        //   onLoginFinshedListener.onLoginSuccess(loginResult);
                        postEventsView.onPostEventsSuccessful(message);


                    } else {
                        postEventsView.onPostEventsUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    postEventsView.onPostEventsUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<PostEventModel> call, Throwable t) {
                Progress.stop();
                postEventsView.onPostEventsUnSuccessful(activity.getString(R.string.server_error));


            }

        });
    }


}
