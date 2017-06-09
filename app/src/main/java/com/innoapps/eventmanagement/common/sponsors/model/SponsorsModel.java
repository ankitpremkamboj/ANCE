package com.innoapps.eventmanagement.common.sponsors.model;

/**
 * Created by ankit on 3/16/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class SponsorsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("sponser_list")
    @Expose
    private ArrayList<SponserList> sponserList = null;

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

    public ArrayList<SponserList> getSponserList() {
        return sponserList;
    }

    public void setSponserList(ArrayList<SponserList> sponserList) {
        this.sponserList = sponserList;
    }


    public class SponserList {

        @SerializedName("sponser_id")
        @Expose
        private String sponserId;
        @SerializedName("sponser_name")
        @Expose
        private String sponserName;
        @SerializedName("sponser_email")
        @Expose
        private String sponserEmail;
        @SerializedName("sponser_photo")
        @Expose
        private String sponserPhoto;
        @SerializedName("sponser_tagline")
        @Expose
        private String sponserTagline;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("added_date")
        @Expose
        private String addedDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;

        public String getSponserId() {
            return sponserId;
        }

        public void setSponserId(String sponserId) {
            this.sponserId = sponserId;
        }

        public String getSponserName() {
            return sponserName;
        }

        public void setSponserName(String sponserName) {
            this.sponserName = sponserName;
        }

        public String getSponserEmail() {
            return sponserEmail;
        }

        public void setSponserEmail(String sponserEmail) {
            this.sponserEmail = sponserEmail;
        }

        public String getSponserPhoto() {
            return sponserPhoto;
        }

        public void setSponserPhoto(String sponserPhoto) {
            this.sponserPhoto = sponserPhoto;
        }

        public String getSponserTagline() {
            return sponserTagline;
        }

        public void setSponserTagline(String sponserTagline) {
            this.sponserTagline = sponserTagline;
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