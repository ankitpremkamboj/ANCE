package com.innoapps.eventmanagement.common.pollsawards.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 3/23/2017.
 */

public class AwardModel {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("award_list")
    @Expose
    private ArrayList<AwardList> awardList = null;

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

    public ArrayList<AwardList> getAwardList() {
        return awardList;
    }

    public void setAwardList(ArrayList<AwardList> awardList) {
        this.awardList = awardList;
    }


    public class AwardList {

        @SerializedName("award_id")
        @Expose
        private String awardId;
        @SerializedName("award_name")
        @Expose
        private String awardName;
        @SerializedName("award_desc")
        @Expose
        private String awardDesc;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("added_date")
        @Expose
        private String addedDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;

        public String getAwardId() {
            return awardId;
        }

        public void setAwardId(String awardId) {
            this.awardId = awardId;
        }

        public String getAwardName() {
            return awardName;
        }

        public void setAwardName(String awardName) {
            this.awardName = awardName;
        }

        public String getAwardDesc() {
            return awardDesc;
        }

        public void setAwardDesc(String awardDesc) {
            this.awardDesc = awardDesc;
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