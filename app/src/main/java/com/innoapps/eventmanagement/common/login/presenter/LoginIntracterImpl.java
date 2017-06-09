package com.innoapps.eventmanagement.common.login.presenter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.Config;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.login.model.LoginUser;
import com.innoapps.eventmanagement.common.login.view.LoginView;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.UserSession;
import com.innoapps.eventmanagement.common.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Braintech on 6/15/2016.
 */
public class LoginIntracterImpl implements LoginIntracter {

    LoginView loginView;

    @Override
    public void validate(String email, String pwd, Activity activity, OnLoginFinshedListener onLoginFinshedListener, LoginView loginView) {
        this.loginView = loginView;
        if (validateCredential(email, pwd, activity, onLoginFinshedListener)) {

            mLoginRecuriter(email, pwd, activity, onLoginFinshedListener);


        }
    }

    public boolean validateCredential(String email, String pwd, Activity activity, OnLoginFinshedListener onLoginFinshedListener) {

        if (TextUtils.isEmpty(email)) {
            onLoginFinshedListener.onEmailError(activity.getString(R.string.empty_email));
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            onLoginFinshedListener.onPasswordError(activity.getString(R.string.empty_password));
            return false;
        } else {
            return true;
        }
    }

    //Calling Asynchronous method to call Login Service

    public void mLoginRecuriter(String email, String pwd, Activity activity, OnLoginFinshedListener onLoginFinshedListener) {


        try {
            ApiAdapter.getInstance(activity);
            callLoginMethod(email, pwd, activity, onLoginFinshedListener);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void callLoginMethod(String email, String pwd, final Activity activity, final OnLoginFinshedListener onLoginFinshedListener) {

        Progress.start(activity);


        Call<LoginUser> callLogin;

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = telephonyManager.getDeviceId();
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);


        callLogin = ApiAdapter.getApiService().login("user_login", email, pwd, regId, "android");

        callLogin.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    LoginUser loginItem = response.body();

                    String message = loginItem.getMsg();

                    if (loginItem.getStatus() == 1 && loginItem.getStatusCode() == 1) {

                        //getting data from Login Item
                        LoginUser.UserDeatils loginResult = loginItem.getUserDeatils();
                        //Saving result in userSession
                        UserSession userSession = new UserSession(activity);
                        //  userSession.createUserSession(loginResult.getUserId(), loginResult.getName(), loginResult.getEmail(), loginResult.getMobile(), loginResult.getTokenId(), loginResult.getImage());
                        userSession.createUserID(loginResult.getUserId());
                        //   onLoginFinshedListener.onLoginSuccess(loginResult);
                        loginView.onLoginSuccessful(loginResult);


                    } else {
                        loginView.onLoginUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    loginView.onLoginUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Progress.stop();
                loginView.onLoginUnSuccessful(activity.getString(R.string.server_error));

            }

        });
    }

}
