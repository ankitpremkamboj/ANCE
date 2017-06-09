package com.innoapps.eventmanagement.common.speakers.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit on 3/8/2017.
 */

public class SpeakerModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("speaker_list")
    @Expose
    private ArrayList<SpeakerList> speakerList = null;

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

    public ArrayList<SpeakerList> getSpeakerList() {
        return speakerList;
    }

    public void setSpeakerList(ArrayList<SpeakerList> speakerList) {
        this.speakerList = speakerList;
    }


    public class SpeakerList {

        @SerializedName("speaker_id")
        @Expose
        private String speakerId;
        @SerializedName("speaker_name")
        @Expose
        private String speakerName;
        @SerializedName("speaker_email")
        @Expose
        private String speakerEmail;
        @SerializedName("speaker_photo")
        @Expose
        private String speakerPhoto;
        @SerializedName("speaker_tagline")
        @Expose
        private String speakerTagline;

        public String getSpeakerId() {
            return speakerId;
        }

        public void setSpeakerId(String speakerId) {
            this.speakerId = speakerId;
        }

        public String getSpeakerName() {
            return speakerName;
        }

        public void setSpeakerName(String speakerName) {
            this.speakerName = speakerName;
        }

        public String getSpeakerEmail() {
            return speakerEmail;
        }

        public void setSpeakerEmail(String speakerEmail) {
            this.speakerEmail = speakerEmail;
        }

        public String getSpeakerPhoto() {
            return speakerPhoto;
        }

        public void setSpeakerPhoto(String speakerPhoto) {
            this.speakerPhoto = speakerPhoto;
        }

        public String getSpeakerTagline() {
            return speakerTagline;
        }

        public void setSpeakerTagline(String speakerTagline) {
            this.speakerTagline = speakerTagline;
        }

    }

}
