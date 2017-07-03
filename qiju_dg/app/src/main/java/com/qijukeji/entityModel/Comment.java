package com.qijukeji.entityModel;

/**
 * Created by Administrator on 2017/5/3.
 */
public class Comment {
    private String uuid;
    private String userid;
    private String staffid;
    private String checkorderid;
    private String content;
    private String updatedtime;
    private String createdtime;

    public Comment() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getCheckorderid() {
        return checkorderid;
    }

    public void setCheckorderid(String checkorderid) {
        this.checkorderid = checkorderid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(String updatedtime) {
        this.updatedtime = updatedtime;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }
}
