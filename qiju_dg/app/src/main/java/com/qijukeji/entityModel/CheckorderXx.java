package com.qijukeji.entityModel;

/**
 * Created by Administrator on 2017/5/2.
 */
public class CheckorderXx {
    private String uuid;
    private String checkorderid;
    private String activityid;
    private String couponid;
    private String userphone;
    private String username;
    private String useraddressvillage;
    private String useraddressunit;
    private String totalmoney;
    private String paymoney;
    private Double discount;
    private String subtitle;
    private String descriptionhd;
    private String descriptionconpant;
    private String type;
    private Double lowerlimitprice;
    private Double upperlimitprice;
    private String coverimage;
    private Boolean isworkday;
    private String iscircle;
    private String remark;
    private String content;
    private String userid;
    private String status;
    private String title;
    private String ordernumber;
    private String autocashback;

    public String getAutocashback() {
        return autocashback;
    }

    public void setAutocashback(String autocashback) {
        this.autocashback = autocashback;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getDescriptionconpant() {
        return descriptionconpant;
    }

    public void setDescriptionconpant(String descriptionconpant) {
        this.descriptionconpant = descriptionconpant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionhd() {
        return descriptionhd;
    }

    public void setDescriptionhd(String descriptionhd) {
        this.descriptionhd = descriptionhd;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCheckorderid() {
        return checkorderid;
    }

    public void setCheckorderid(String checkorderid) {
        this.checkorderid = checkorderid;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLowerlimitprice() {
        return lowerlimitprice;
    }

    public void setLowerlimitprice(Double lowerlimitprice) {
        this.lowerlimitprice = lowerlimitprice;
    }

    public Double getUpperlimitprice() {
        return upperlimitprice;
    }

    public void setUpperlimitprice(Double upperlimitprice) {
        this.upperlimitprice = upperlimitprice;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public Boolean getIsworkday() {
        return isworkday;
    }

    public void setIsworkday(Boolean isworkday) {
        this.isworkday = isworkday;
    }

    public String getIscircle() {
        return iscircle;
    }

    public void setIscircle(String iscircle) {
        this.iscircle = iscircle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CheckorderXx{" +
                "uuid='" + uuid + '\'' +
                ", checkorderid='" + checkorderid + '\'' +
                ", activityid='" + activityid + '\'' +
                ", couponid='" + couponid + '\'' +
                ", userphone='" + userphone + '\'' +
                ", username='" + username + '\'' +
                ", useraddressvillage='" + useraddressvillage + '\'' +
                ", useraddressunit='" + useraddressunit + '\'' +
                ", totalmoney='" + totalmoney + '\'' +
                ", paymoney='" + paymoney + '\'' +
                ", discount=" + discount +
                ", subtitle='" + subtitle + '\'' +
                ", descriptionhd='" + descriptionhd + '\'' +
                ", descriptionconpant='" + descriptionconpant + '\'' +
                ", type='" + type + '\'' +
                ", lowerlimitprice=" + lowerlimitprice +
                ", upperlimitprice=" + upperlimitprice +
                ", coverimage='" + coverimage + '\'' +
                ", isworkday=" + isworkday +
                ", iscircle='" + iscircle + '\'' +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", userid='" + userid + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
