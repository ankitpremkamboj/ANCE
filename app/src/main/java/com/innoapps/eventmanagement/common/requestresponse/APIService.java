package com.innoapps.eventmanagement.common.requestresponse;


import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.forgotpassword.ForgetPwdModel;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.login.model.LoginUser;
import com.innoapps.eventmanagement.common.myblogs.model.MyBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.model.MyDetailsBlogsModel;
import com.innoapps.eventmanagement.common.myfriendevent.model.MyFriendEventModel;
import com.innoapps.eventmanagement.common.myfriends.model.MyFriendsModel;
import com.innoapps.eventmanagement.common.photofeed.model.PhotoFeedModel;
import com.innoapps.eventmanagement.common.pollsawards.model.AwardModel;
import com.innoapps.eventmanagement.common.postevents.model.PostEventModel;
import com.innoapps.eventmanagement.common.refelentry.model.RefelEntryModel;
import com.innoapps.eventmanagement.common.signup.model.RegisterUser;
import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;
import com.innoapps.eventmanagement.common.speakers.view.SpeakerView;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileDetailsModel;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Braintech on 18-May-16.
 */
public interface APIService {

    //----------------------------------------------------------------Login------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<LoginUser> login(@Field("action") String action, @Field("user_name") String username, @Field("password") String password, @Field("device_token") String token, @Field("device_key") String deviceKey);

    /*------------------------------------Register--------------------------------------*/
    @POST("api.php")
    //@FormUrlEncoded
    @Multipart
    Call<RegisterUser> register(@Part("action") RequestBody action, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("mobile") RequestBody mobile, @Part("company_name") RequestBody company_name, @Part("password") RequestBody password, @Part MultipartBody.Part imageFile, @Part("device_key") RequestBody device_key, @Part("device_id") RequestBody device_id);

    /*------------------------------------Update User Profile--------------------------------------*/
    @POST("api.php")
    //@FormUrlEncoded
    @Multipart
    Call<UserProfileModel> updateUserProfile(@Part("user_id") RequestBody userID, @Part("action") RequestBody action, @Part("name") RequestBody name, @Part("mobile") RequestBody mobile, @Part("company_name") RequestBody company_name, @Part MultipartBody.Part imageFile);

    //actionRequest, nameRequest, mobileNumberRequest, companyNameRequest, imageFileBody, userIdRequest
    //----------------------------------------------------------------Get User profile data------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<UserProfileDetailsModel> getUserProfileData(@Field("action") String action, @Field("user_id") String userId);


    //----------------------------------------------------------------Get Event List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<EventsModel> event(@Field("action") String action, @Field("user_id") String userId, @Field("page") String page, @Field("max") String max);

    //----------------------------------------------------------------Get My Friend Event List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<MyFriendEventModel> friendEvent(@Field("action") String action, @Field("user_id") String userId);

    //----------------------------------------------------------------Get Event like------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<RequestSuccessModel> eventlike(@Field("action") String action, @Field("user_id") String userId, @Field("event_id") String eventId, @Field("like_val") String likeValue);

    //----------------------------------------------------------------Get Blog List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<BlogsModel> blog(@Field("action") String action);

    //----------------------------------------------------------------Get Award List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<AwardModel> award(@Field("action") String action);

    //----------------------------------------------------------------Get My Blog List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<MyBlogsModel> myblog(@Field("action") String action, @Field("user_id") String userID);

    //----------------------------------------------------------------Get Details Blog List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<DetailsBlogsModel> blogDetails(@Field("action") String action, @Field("blog_id") String blogid);


    //----------------------------------------------------------------Get My Details Blog List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<MyDetailsBlogsModel> myblogDetails(@Field("action") String action, @Field("user_id") String userID, @Field("blog_id") String blogid);

    //----------------------------------------------------------------Get Friend  List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<FriendsModel> getFriend(@Field("action") String action, @Field("user_id") String userId, @Field("search_text") String search);


    //----------------------------------------------------------------Get MyFriend  List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<MyFriendsModel> getMyFriend(@Field("action") String action, @Field("user_id") String userId);


    //----------------------------------------------------------------Send Friend Request------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<RequestSuccessModel> sendFriendRequest(@Field("action") String action, @Field("user_id") String userId, @Field("act_val") String activeValue, @Field("friend_id") String friendId);


    //----------------------------------------------------------------Get Friend  List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<SpeakerModel> getSpeaker(@Field("action") String action);

    //----------------------------------------------------------------Get Sponsors  List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<SponsorsModel> getSponsors(@Field("action") String action);


    //----------------------------------------------------------------Get Refel Entry  List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<RefelEntryModel> getRefelEntry(@Field("action") String action);

    //----------------------------------------------------------------Get Refel Entry  List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<RefelEntryModel> sendRefelEntry(@Field("action") String action, @Field("ticket_no") String ticketNumber, @Field("user_id") String userID);

    //----------------------------------------------------------------Get Agenda  List------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<AgendaModel> getAgenda(@Field("action") String action);

    //search_text
    /*------------------------------------Post Event--------------------------------------*/
    @POST("api.php")
    //@FormUrlEncoded
    @Multipart
    Call<PostEventModel> postEvent(@Part("action") RequestBody action, @Part("user_id") RequestBody userID, @Part("event_title") RequestBody eventTitle, @Part("event_description") RequestBody eventDescription, @Part MultipartBody.Part imageFile, @Part("latitude") RequestBody lat, @Part("longitude") RequestBody lng);

    /*------------------------------------Post Blog--------------------------------------*/
    @POST("api.php")
    @Multipart
    Call<RequestSuccessModel> postBlog(@Part("action") RequestBody action, @Part("user_id") RequestBody userID, @Part("blog_title") RequestBody blogTitle, @Part("blog_description") RequestBody blogDescription, @Part MultipartBody.Part imageFile);

    //----------------------------------------------------------------Forgot Password-----------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<ForgetPwdModel> forgotPassword(@Field("action") String action, @Field("email") String email);


    //----------------------------------------------------------------Change Password-----------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<RequestSuccessModel> changePassword(@Field("action") String action, @Field("password") String newpassword, @Field("user_id") String userID);

    //----------------------------------------------------------------GetPhoto Feed------------------------------------------------------------------------------------------------------------//
    @POST("api.php")
    @FormUrlEncoded
    Call<PhotoFeedModel> getPhotoFeed(@Field("action") String action);

    /*------------------------------------Post Blog--------------------------------------*/
    @POST("api.php")
    @Multipart
    Call<RequestSuccessModel> uploadPhotoFeed(@Part("action") RequestBody action, @Part("user_id") RequestBody userID, @Part("gallery_name") RequestBody galleryName, @Part MultipartBody.Part imageFile);


}
