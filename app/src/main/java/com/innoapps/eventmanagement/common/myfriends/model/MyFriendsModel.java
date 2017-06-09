package com.innoapps.eventmanagement.common.myfriends.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ankit on 3/8/2017.
 */
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyFriendsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("user_friend_list")
    @Expose
    private ArrayList<UserFriendList> userFriendList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<UserFriendList> getUserFriendList() {
        return userFriendList;
    }

    public void setUserFriendList(ArrayList<UserFriendList> userFriendList) {
        this.userFriendList = userFriendList;
    }


    public class UserFriendList {

        @SerializedName("user_friend_id")
        @Expose
        private String userFriendId;
        @SerializedName("user_friend_name")
        @Expose
        private String userFriendName;
        @SerializedName("user_friend_image")
        @Expose
        private String userFriendImage;
        @SerializedName("user_friend_email")
        @Expose
        private String userFriendEmail;
        @SerializedName("user_friend_phone")
        @Expose
        private String userFriendPhone;

        @SerializedName("status")
        @Expose
        private String status;

        public String getUserFriendId() {
            return userFriendId;
        }

        public void setUserFriendId(String userFriendId) {
            this.userFriendId = userFriendId;
        }

        public String getUserFriendName() {
            return userFriendName;
        }

        public void setUserFriendName(String userFriendName) {
            this.userFriendName = userFriendName;
        }

        public String getUserFriendImage() {
            return userFriendImage;
        }

        public void setUserFriendImage(String userFriendImage) {
            this.userFriendImage = userFriendImage;
        }

        public String getUserFriendEmail() {
            return userFriendEmail;
        }

        public void setUserFriendEmail(String userFriendEmail) {
            this.userFriendEmail = userFriendEmail;
        }

        public String getUserFriendPhone() {
            return userFriendPhone;
        }

        public void setUserFriendPhone(String userFriendPhone) {
            this.userFriendPhone = userFriendPhone;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}