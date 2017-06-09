package com.innoapps.eventmanagement.common.login.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.login.model.LoginUser;
import com.innoapps.eventmanagement.common.login.view.LoginView;


/**
 * Created by Braintech on 6/15/2016.
 */
public class LoginPresenterImpl implements LoginPresenter ,LoginIntracter.OnLoginFinshedListener{

    LoginView loginView;
    Activity activity;

    LoginIntracter loginIntracter;

    public LoginPresenterImpl(LoginView loginView, Activity activity) {
        this.loginView = loginView;
        this.activity = activity;

        loginIntracter = new LoginIntracterImpl();
    }


    //Validate simple Login
    @Override
    public void validateSimpleLogin(String email, String pwd) {
        loginIntracter.validate(email, pwd, activity, this,loginView);
    }



    @Override
    public void onEmailError(String msg) {
        loginView.onEmailError(msg);
    }


    @Override
    public void onPasswordError(String msg) {
        loginView.onPasswordError(msg);
    }

    @Override
    public void onLoginSuccess(LoginUser.UserDeatils userDeatils) {
        loginView.onLoginSuccessful(userDeatils);
    }

    @Override
    public void onLoginUnSuccessful(String msg) {
        loginView.onLoginUnSuccessful(msg);
    }

    @Override
    public void onLoginInternetError() {
        loginView.onLoginInternetError();
    }
}
