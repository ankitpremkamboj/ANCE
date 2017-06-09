package com.innoapps.eventmanagement.common.userprofile.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.signup.view.RegisterView;
import com.innoapps.eventmanagement.common.userprofile.view.UserProfileView;

/**
 * Created by Ankit on 3/6/2017.
 */

public interface UserProfilePresenter {
    //this, this, name, email, mobile, company_name, password, profile_image
    void validateUserProfile(Activity activity, UserProfileView userProfileView, String name, String mobileNumber, String companyName, String profile_image);

    void fetchUserProfileDetails(Activity activity, UserProfileView userProfileView);

}
