package com.qijukeji.entityModel;

import java.util.Date;

/**
 * 活动分享实体对象
 * generated by system on Apr 27, 2017 8:30:07 AM
 */
public class ActivityShare {

    private String uuid;
    /**
     * 用户ID
     */
    private String userID;


    /**
     * 活动ID
     */
    private String activityID;


    /**
     * 分享类型
     */
    private Integer shareType = 0;


    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 开始时间
     */
    private String begindateTime;

    /**
     * 结束时间
     */
    private String enddateTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getBegindateTime() {
        return begindateTime;
    }

    public void setBegindateTime(String begindateTime) {
        this.begindateTime = begindateTime;
    }

    public String getEnddateTime() {
        return enddateTime;
    }

    public void setEnddateTime(String enddateTime) {
        this.enddateTime = enddateTime;
    }
}