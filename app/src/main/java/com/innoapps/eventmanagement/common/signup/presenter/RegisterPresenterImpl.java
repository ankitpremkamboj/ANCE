package com.innoapps.eventmanagement.common.signup.presenter;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.signup.model.RegisterUser;
import com.innoapps.eventmanagement.common.signup.view.RegisterView;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileModel;
import com.innoapps.eventmanagement.common.userprofile.view.UserProfileView;
import com.innoapps.eventmanagement.common.utils.UserSession;
import com.innoapps.eventmanagement.common.utils.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/15/2017.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    RegisterView registerView;
    Activity activity;

    public RegisterPresenterImpl(RegisterView registerView, Activity activity) {
        this.registerView = registerView;
        this.activity = activity;
        //  this.sliderProfileUpdate = sliderProfileUpdate;
        // loginIntracter = new LoginIntracterImpl();
    }

    //Activity activity, UserProfileView userProfileView, String name,String email, String mobileNumber, String companyName, String password,String profile_image
    @Override
    public void validateRegister(Activity activity, RegisterView registerView, String name, String email, String mobileNumber, String companyName, String password, String profile_image) {
        if (validateCredential(activity, registerView, name, email, mobileNumber, companyName, password, profile_image)) {
            //  name, email, mobile, company_name, password, cameraImageFilePath
            callRegisterApi(activity, registerView, name, email, mobileNumber, companyName, password, profile_image);


        }
    }


    public boolean validateCredential(Activity activity, RegisterView registerView, String name, String email, String mobileNumber, String companyName, String password, String profile_image) {

        if (TextUtils.isEmpty(name)) {
            registerView.onNameError(activity.getString(R.string.empty_name));
            return false;
        } else if (TextUtils.isEmpty(email)) {
            registerView.onEmailError(activity.getString(R.string.empty_email));

            return false;
        } else if (!Utils.emailValidation(email)) {
            registerView.onValidEmailError(activity.getString(R.string.invalid_email));

            return false;
        } else if (TextUtils.isEmpty(companyName)) {
            registerView.onCompanyNameError(activity.getString(R.string.empty_company_name));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            registerView.onPasswordError(activity.getString(R.string.empty_password));
            return false;
        } else {
            return true;
        }
    }

    private boolean phoneValidation(String mobileNumber, RegisterView userProfileView) {
        return true;
    }

    private void callRegisterApi(Activity activity, RegisterView userProfileView, String name, String email, String mobileNumber, String companyName, String password, String profile_image) {
        try {
            ApiAdapter.getInstance(activity);
            callRegisterMethod(activity, userProfileView, name, email, mobileNumber, companyName, password, profile_image);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void callRegisterMethod(final Activity activity, final RegisterView userProfileView, String name, String email, String mobileNumber, String companyName, String password, String profile_image) {


        Progress.start(activity);


        Call<RegisterUser> callLogin;

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = telephonyManager.getDeviceId();
        File file;
        RequestBody requestBody;
        MultipartBody.Part imageFileBody;
        //prepare image file

        RequestBody actionRequest = RequestBody.create(MediaType.parse("text/plain"), "user_register");

        RequestBody nameRequest = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody mobileNumberRequest = RequestBody.create(MediaType.parse("text/plain"), mobileNumber);
        RequestBody companyNameRequest = RequestBody.create(MediaType.parse("text/plain"), companyName);
        RequestBody passwordRequest = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody androidRequest = RequestBody.create(MediaType.parse("text/plain"), "android");
        RequestBody deviceIdRequest = RequestBody.create(MediaType.parse("text/plain"), deviceid);
        RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), email);

        if (profile_image.equalsIgnoreCase("")) {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            imageFileBody = MultipartBody.Part.createFormData("profile_pic", "path", requestBody);
        } else {
            file = new File(profile_image);
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageFileBody = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestBody);
        }


//@Part("action") RequestBody action, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("mobile")
// RequestBody mobile, @Part("company_name") RequestBody company_name, @Part("password") RequestBody password, @Part MultipartBody.Part imageFile,
// @Part("device_key") RequestBody device_key, @Part("device_id") RequestBody device_i
        // callLogin = ApiAdapter.getApiService().register("user_register", name, email, mobileNumber, companyName, password, imageFileBody, "android", deviceid);

        callLogin = ApiAdapter.getApiService().register(actionRequest, nameRequest, emailRequest, mobileNumberRequest, companyNameRequest, passwordRequest, imageFileBody, androidRequest, deviceIdRequest);

        callLogin.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    RegisterUser userProfileModel = response.body();

                    String message = userProfileModel.getMsg();

                    if (userProfileModel.getStatus() == 1 && userProfileModel.getStatusCode() == 1) {

                        UserSession userSession = new UserSession(activity);
                        //  userSession.createUserSession(loginResult.getUserId(), loginResult.getName(), loginResult.getEmail(), loginResult.getMobile(), loginResult.getTokenId(), loginResult.getImage());
                        userSession.createUserID(String.valueOf(userProfileModel.getUserId()));
                        //   onLoginFinshedListener.onLoginSuccess(loginResult);
                        userProfileView.onRegistrationSuccessful(message);


                    } else {
                        registerView.onRegistrationUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    registerView.onRegistrationUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                Progress.stop();
                registerView.onRegistrationUnSuccessful(activity.getString(R.string.server_error));

            }

        });
    }


}
