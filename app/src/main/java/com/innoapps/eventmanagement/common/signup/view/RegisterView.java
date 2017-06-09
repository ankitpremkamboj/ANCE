package com.innoapps.eventmanagement.common.signup.view;

import com.innoapps.eventmanagement.common.userprofile.model.UserProfileDetailsModel;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileModel;

/**
 * Created by ankit on 3/15/2017.
 */

public interface RegisterView {


    void onNameError(String name);
    void onEmailError(String name);
    void onValidEmailError(String name);

    void onPasswordError(String name);

    void onMobileNumberError(String mobileNumber);

    void onCompanyNameError(String companyName);
    void onProfileImageError(String profileImage);


    void onUserProfileInternetError();

    void onRegistrationSuccessful(String message);


    void onRegistrationUnSuccessful(String msg);
}
