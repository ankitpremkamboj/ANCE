package com.innoapps.eventmanagement.common.login.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.login.model.LoginUser;
import com.innoapps.eventmanagement.common.login.view.LoginView;

/**
 * Created by Braintech on 6/15/2016.
 */
public interface LoginIntracter {

    void validate(String email, String pwd, Activity activity, OnLoginFinshedListener onLoginFinshedListener, LoginView loginView);

    interface OnLoginFinshedListener {

        void onEmailError(String msg);

        void onPasswordError(String msg);

        void onLoginSuccess(LoginUser.UserDeatils userDeatils);

        void onLoginUnSuccessful(String msg);

        void onLoginInternetError();
    }
}
