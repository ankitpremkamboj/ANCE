package com.innoapps.eventmanagement.common.agenda.model;

/**
 * Created by ankit on 3/16/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AgendaModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("agenda_list")
    @Expose
    private ArrayList<AgendaList> agendaList = null;

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

    public ArrayList<AgendaList> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(ArrayList<AgendaList> agendaList) {
        this.agendaList = agendaList;
    }


    public class AgendaList {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("data")
        @Expose
        private ArrayList<Datum> data = null;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public ArrayList<Datum> getData() {
            return data;
        }

        public void setData(ArrayList<Datum> data) {
            this.data = data;
        }


        public class Datum {

            @SerializedName("agenda_id")
            @Expose
            private String agendaId;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("agenda_title")
            @Expose
            private String agendaTitle;
            @SerializedName("from_time")
            @Expose
            private String fromTime;
            @SerializedName("to_time")
            @Expose
            private String toTime;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("added_date")
            @Expose
            private String addedDate;

            public String getAgendaId() {
                return agendaId;
            }

            public void setAgendaId(String agendaId) {
                this.agendaId = agendaId;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getAgendaTitle() {
                return agendaTitle;
            }

            public void setAgendaTitle(String agendaTitle) {
                this.agendaTitle = agendaTitle;
            }

            public String getFromTime() {
                return fromTime;
            }

            public void setFromTime(String fromTime) {
                this.fromTime = fromTime;
            }

            public String getToTime() {
                return toTime;
            }

            public void setToTime(String toTime) {
                this.toTime = toTime;
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


        }
    }
}
