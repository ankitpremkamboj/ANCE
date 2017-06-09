package com.innoapps.eventmanagement.common.signup.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.signup.view.RegisterView;
import com.innoapps.eventmanagement.common.userprofile.view.UserProfileView;

/**
 * Created by ankit on 3/15/2017.
 */

public interface RegisterPresenter {

    void validateRegister(Activity activity, RegisterView  registerView, String name, String email, String mobileNumber, String companyName, String password, String profile_image);
}
