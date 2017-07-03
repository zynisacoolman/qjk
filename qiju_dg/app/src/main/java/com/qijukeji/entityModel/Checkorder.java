package com.qijukeji.entityModel;

/**
 * 保存顾客参加活动信息
 */
public class Checkorder {
    /**
     * 唯一编号
     */
    private String uuid;

    /**
     * 导购id
     */
    private String staffid;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 用户电话
     */
    private String userphone;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户小区
     */
    private String useraddressvillage;

    /**
     * 用户单元
     */
    private String useraddressunit;

    /**
     * 支付金额
     */

    private String totalmoney;
    /**
     * 总金额
     */
    private String paymoney;
    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 更新时间
     */
    private String updatedtime;

    /**
     * 创建时间
     */
    private String createdtime;
    /**
     * 优惠券id
     */
    private String couponid;
    /**
     * 活动id
     */

    private String activityid;

    private String ordernumber;

    private String orderaction;
    private String source;

    public Checkorder() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderaction() {
        return orderaction;
    }

    public void setOrderaction(String orderaction) {
        this.orderaction = orderaction;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseraddressvillage() {
        return useraddressvillage;
    }

    public void setUseraddressvillage(String useraddressvillage) {
        this.useraddressvillage = useraddressvillage;
    }

    public String getUseraddressunit() {
        return useraddressunit;
    }

    public void setUseraddressunit(String useraddressunit) {
        this.useraddressunit = useraddressunit;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(String paymoney) {
        this.paymoney = paymoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }
}