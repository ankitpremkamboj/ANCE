package com.innoapps.eventmanagement.common.login.view;

import android.app.Activity;

import com.innoapps.eventmanagement.common.login.model.LoginUser;
import com.innoapps.eventmanagement.common.login.presenter.LoginIntracter;

/**
 * Created by Braintech on 6/8/2016.
 */
public interface LoginView {



    void onLoginSuccessful(LoginUser.UserDeatils userDeatils);

    void onLoginUnSuccessful(String msg);

    void onEmailError(String msg);

    void onPasswordError(String msg);

    void onLoginInternetError();



}
