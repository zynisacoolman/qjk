package com.qijukeji.entityModel;

/**
 * 查询优惠卷
 */
public class SelectCoupon {

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 标题
     */
    private String title;

    /**
     * 品牌id
     */
    private String brandid;

    /**
     * 优惠类型
     * 1:满减
     * 2：直减
     * 3:打折
     */
    private String type;


    /**
     * 优惠金额
     */
    private String discount;


    /**
     * 最低限额
     */
    private String lowerLimitPrice;


    /**
     * 最高限额
     */
    private String upperLimitPrice;


    /**
     * 是否循环
     */
    private String isCircle;


    /**
     * 描述
     */
    private String descriptionconpant;


    /**
     * 背景图片
     */
    private String coverImage;
    private String couponid;
    private String subtitle;
    private String autocashback;
    /**
     * 是否工作日
     */
    private boolean workday;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getLowerLimitPrice() {
        return lowerLimitPrice;
    }

    public void setLowerLimitPrice(String lowerLimitPrice) {
        this.lowerLimitPrice = lowerLimitPrice;
    }

    public String getUpperLimitPrice() {
        return upperLimitPrice;
    }

    public void setUpperLimitPrice(String upperLimitPrice) {
        this.upperLimitPrice = upperLimitPrice;
    }

    public String getIsCircle() {
        return isCircle;
    }

    public void setIsCircle(String isCircle) {
        this.isCircle = isCircle;
    }

    public String getDescriptionconpant() {
        return descriptionconpant;
    }

    public void setDescriptionconpant(String descriptionconpant) {
        this.descriptionconpant = descriptionconpant;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAutocashback() {
        return autocashback;
    }

    public void setAutocashback(String autocashback) {
        this.autocashback = autocashback;
    }

    public boolean getWorkday() {
        return workday;
    }

    public void setWorkday(boolean workday) {
        this.workday = workday;
    }
}