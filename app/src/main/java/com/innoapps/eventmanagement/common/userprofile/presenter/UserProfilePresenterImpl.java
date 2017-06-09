package com.innoapps.eventmanagement.common.userprofile.presenter;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.navigation.NavigationActivity;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.signup.model.RegisterUser;
import com.innoapps.eventmanagement.common.signup.presenter.RegisterPresenter;
import com.innoapps.eventmanagement.common.signup.view.RegisterView;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileDetailsModel;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileModel;
import com.innoapps.eventmanagement.common.userprofile.view.UserProfileView;
import com.innoapps.eventmanagement.common.utils.UserSession;
import com.innoapps.eventmanagement.common.utils.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 3/6/2017.
 */

public class UserProfilePresenterImpl implements UserProfilePresenter {
    UserProfileView userProfileView;
    Activity activity;
    UserSession userSession;
    SliderProfileUpdate sliderProfileUpdate;

    public UserProfilePresenterImpl(UserProfileView userProfileView, Activity activity) {
        this.userProfileView = userProfileView;
        this.activity = activity;
        this.sliderProfileUpdate = sliderProfileUpdate;
        userSession = new UserSession(activity);
        // loginIntracter = new LoginIntracterImpl();
    }

    @Override
    public void validateUserProfile(Activity activity, UserProfileView userProfileView, String name, String mobileNumber, String companyName, String profile_image) {
        if (validateCredential(activity, userProfileView, name, mobileNumber, companyName, profile_image)) {
            //  name, email, mobile, company_name, password, cameraImageFilePath
            callUserProfileApi(activity, userProfileView, name, mobileNumber, companyName, profile_image);


        }
    }

    @Override
    public void fetchUserProfileDetails(Activity activity, UserProfileView userProfileView) {


        try {
            ApiAdapter.getInstance(activity);
            getUserProfileAllData(activity, userProfileView);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void callUserProfileApi(Activity activity, UserProfileView userProfileView, String name, String mobileNumber, String companyName, String profile_image) {
        try {
            ApiAdapter.getInstance(activity);
            callUserProfileMethod(activity, userProfileView, name, mobileNumber, companyName, profile_image);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void callUserProfileMethod(final Activity activity, final UserProfileView userProfileView, String name, String mobileNumber, String companyName, String profile_image) {


        Progress.start(activity);


        Call<UserProfileModel> callLogin;

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = telephonyManager.getDeviceId();

        //prepare image file


        RequestBody actionRequest = RequestBody.create(MediaType.parse("text/plain"), "update_user_profile_detail");
        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userSession.getUserID());

        RequestBody nameRequest = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody mobileNumberRequest = RequestBody.create(MediaType.parse("text/plain"), mobileNumber);
        RequestBody companyNameRequest = RequestBody.create(MediaType.parse("text/plain"), companyName);

///Uploading for image
        File file = new File(profile_image);
        RequestBody requestBody;
        MultipartBody.Part imageFileBody = null;
        if (!file.exists()) {
            // requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // imageFileBody = MultipartBody.Part.createFormData("profile_pic", profile_image, requestBody);

        } else {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageFileBody = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestBody);
        }

        // callLogin = ApiAdapter.getApiService().register("user_register", name, email, mobileNumber, companyName, password, imageFileBody, "android", deviceid);
        callLogin = ApiAdapter.getApiService().updateUserProfile(userIdRequest, actionRequest, nameRequest, mobileNumberRequest, companyNameRequest, imageFileBody);

        callLogin.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    UserProfileModel userProfileModel = response.body();

                    String message = userProfileModel.getMsg();

                    if (userProfileModel.getStatus() == 1 && userProfileModel.getStatusCode() == 1) {


                        //   onLoginFinshedListener.onLoginSuccess(loginResult);
                        userProfileView.onUserProfileSuccessful(userProfileModel);
                       // ((NavigationActivity)getUserProfileAllData();


                    } else {
                        userProfileView.onUserProfileUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    userProfileView.onUserProfileUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                Progress.stop();
                userProfileView.onUserProfileUnSuccessful(activity.getString(R.string.server_error));

            }

        });
    }


    public boolean validateCredential(Activity activity, UserProfileView userProfileView, String name, String mobileNumber, String companyName, String profile_image) {

        if (TextUtils.isEmpty(name)) {
            userProfileView.onNameError(activity.getString(R.string.empty_name));
            return false;
        } else if (!phoneValidation(mobileNumber, userProfileView)) {
            //  registerView.onMobileNumberError(activity.getString(R.string.empty_mobile));
            return false;
        } else if (TextUtils.isEmpty(companyName)) {
            userProfileView.onCompanyNameError(activity.getString(R.string.empty_company_name));
            return false;
        } else if (TextUtils.isEmpty(profile_image)) {
            userProfileView.onProfileImageError(activity.getString(R.string.empty_profile_image));

            return false;
        } else {
            return true;
        }
    }


    // phone password Validation
    private boolean phoneValidation(String mobile, UserProfileView userProfileView) {

        if (mobile.length() > 0) {
            return true;
        } else {
            userProfileView.onMobileNumberError(activity.getString(R.string.empty_mobile));
            return false;
        }
    }


    private void getUserProfileAllData(final Activity activity, final UserProfileView userProfileView) {


        Progress.start(activity);


        Call<UserProfileDetailsModel> callUserProfileData;


        callUserProfileData = ApiAdapter.getApiService().getUserProfileData("user_profile_detail", userSession.getUserID());

        callUserProfileData.enqueue(new Callback<UserProfileDetailsModel>() {
            @Override
            public void onResponse(Call<UserProfileDetailsModel> call, Response<UserProfileDetailsModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    UserProfileDetailsModel userProfileDetailsModel = response.body();

                    String message = userProfileDetailsModel.getMsg();

                    if (userProfileDetailsModel.getStatus() == 1 && userProfileDetailsModel.getStatusCode() == 1) {
                        //   onLoginFinshedListener.onLoginSuccess(loginResult);
                        userProfileView.onGetUserProfileSuccessful(userProfileDetailsModel);
                        //  sliderProfileUpdate.onUserProfileSuccessful(userProfileDetailsModel);


                    } else {
                        userProfileView.onUserProfileUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    userProfileView.onUserProfileUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<UserProfileDetailsModel> call, Throwable t) {
                Progress.stop();
                userProfileView.onUserProfileUnSuccessful(activity.getString(R.string.server_error));

            }

        });
    }


}
