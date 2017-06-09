package com.innoapps.eventmanagement.common.events.model;

/**
 * Created by ankit on 3/15/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("event_list")
    @Expose
    private ArrayList<EventList> eventList = null;

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

    public ArrayList<EventList> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<EventList> eventList) {
        this.eventList = eventList;
    }


    public class EventList {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("event_name")
        @Expose
        private String eventName;
        @SerializedName("event_description")
        @Expose
        private String eventDescription;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("added_date")
        @Expose
        private String addedDate;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_phone")
        @Expose
        private String userPhone;
        @SerializedName("total_favorite")
        @Expose
        private String totalFavorite;

        @SerializedName("total_like")
        @Expose
        private Integer totalLike;
        @SerializedName("like_val")
        @Expose
        private Integer likeVal;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public void setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddedDate() {
            return addedDate;
        }

        public void setAddedDate(String addedDate) {
            this.addedDate = addedDate;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getTotalFavorite() {
            return totalFavorite;
        }

        public void setTotalFavorite(String totalFavorite) {
            this.totalFavorite = totalFavorite;
        }

        public Integer getTotalLike() {
            return totalLike;
        }

        public void setTotalLike(Integer totalLike) {
            this.totalLike = totalLike;
        }

        public Integer getLikeVal() {
            return likeVal;
        }

        public void setLikeVal(Integer likeVal) {
            this.likeVal = likeVal;
        }

    }

}