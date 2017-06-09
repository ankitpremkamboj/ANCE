package com.innoapps.eventmanagement.common.userprofile.view;

import com.innoapps.eventmanagement.common.signup.model.RegisterUser;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileDetailsModel;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileModel;

/**
 * Created by Ankit on 3/6/2017.
 */

public interface UserProfileView {


    void onNameError(String name);

    void onMobileNumberError(String mobileNumber);

    void onCompanyNameError(String companyName);
    void onProfileImageError(String profileImage);


    void onUserProfileInternetError();

    void onUserProfileSuccessful(UserProfileModel profileModel);
    void onGetUserProfileSuccessful(UserProfileDetailsModel userProfileDetailsModel);


    void onUserProfileUnSuccessful(String msg);


}
