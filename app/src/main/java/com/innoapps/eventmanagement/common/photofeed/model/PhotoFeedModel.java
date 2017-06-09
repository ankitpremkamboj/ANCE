package com.innoapps.eventmanagement.common.photofeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ankit on 4/4/2017.
 */

public class PhotoFeedModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("gallery_list")
    @Expose
    private ArrayList<GalleryList> galleryList = null;

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

    public ArrayList<GalleryList> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(ArrayList<GalleryList> galleryList) {
        this.galleryList = galleryList;
    }


    public class GalleryList {

        @SerializedName("gallery_id")
        @Expose
        private String galleryId;
        @SerializedName("gallery_name")
        @Expose
        private String galleryName;
        @SerializedName("gallery_image")
        @Expose
        private String galleryImage;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("added_date")
        @Expose
        private String addedDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;

        public String getGalleryId() {
            return galleryId;
        }

        public void setGalleryId(String galleryId) {
            this.galleryId = galleryId;
        }

        public String getGalleryName() {
            return galleryName;
        }

        public void setGalleryName(String galleryName) {
            this.galleryName = galleryName;
        }

        public String getGalleryImage() {
            return galleryImage;
        }

        public void setGalleryImage(String galleryImage) {
            this.galleryImage = galleryImage;
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

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

    }


}