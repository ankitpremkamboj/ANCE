package com.innoapps.eventmanagement.common.forgotpassword;

/**
 * Created by ankit on 3/15/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPwdModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("otp_deatils")
    @Expose
    private OtpDeatils otpDeatils;

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

    public OtpDeatils getOtpDeatils() {
        return otpDeatils;
    }

    public void setOtpDeatils(OtpDeatils otpDeatils) {
        this.otpDeatils = otpDeatils;
    }




public class OtpDeatils {

    @SerializedName("otp_no")
    @Expose
    private Integer otpNo;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
}