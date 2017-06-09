package com.innoapps.eventmanagement.common.myblogs.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MyDetailsBlogsModel
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("blog_list")
    @Expose
    private ArrayList<BlogList> blogList = null;

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

    public ArrayList<BlogList> getBlogList() {
        return blogList;
    }

    public void setBlogList(ArrayList<BlogList> blogList) {
        this.blogList = blogList;
    }


    public class BlogList {

        @SerializedName("blog_id")
        @Expose
        private String blogId;
        @SerializedName("blog_title")
        @Expose
        private String blogTitle;
        @SerializedName("blog_photo")
        @Expose
        private String blogPhoto;
        @SerializedName("blog_description")
        @Expose
        private String blogDescription;
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

        public String getBlogId() {
            return blogId;
        }

        public void setBlogId(String blogId) {
            this.blogId = blogId;
        }

        public String getBlogTitle() {
            return blogTitle;
        }

        public void setBlogTitle(String blogTitle) {
            this.blogTitle = blogTitle;
        }

        public String getBlogPhoto() {
            return blogPhoto;
        }

        public void setBlogPhoto(String blogPhoto) {
            this.blogPhoto = blogPhoto;
        }

        public String getBlogDescription() {
            return blogDescription;
        }

        public void setBlogDescription(String blogDescription) {
            this.blogDescription = blogDescription;
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