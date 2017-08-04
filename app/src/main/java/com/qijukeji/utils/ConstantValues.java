package com.qijukeji.utils;

/**
 * Created by Administrator on 2017/4/11.
 */

public class ConstantValues {
    //ip地址
  //  private static final String HOST = "https://www.uzaiuzhai.com/";
    private static final String HOST = "https://qijukeji.cn/";
//        private static final String HOST = "http://192.168.1.146/";
    //项目
    public static final String HOST_URL = HOST + "qijukeji/";
    private static final String HOST_STAFFID = "?staffid=";
    //版本号查询
    public static final String HTTP_VERSION = HOST_URL + "api/useractivity/versionnumber?type=android";
    //查询首页意向/成单信息
    public static final String HTTP_INTENTION = HOST_URL + "api/checkorder/list";
    //扫描二维码
    public static final String SYS_URL = HOST_URL + "api/checkorder/scan";
    //订单详情下一步
    public static final String HTTP_CHECKORDER_NEXT = HOST_URL + "api/checkorder/next";
    //保存顾客信息
    public static final String HTTP_SAVE_URL = HOST_URL + "api/checkorder/updateuserinfo";
    //查询意向/成单订单详细信息
    public static final String HTTP_SELECTYXDDXX = HOST_URL + "api/checkorder/detail";
    //成单
    public static final String HTTP_ORDER_FINISH = HOST_URL + "api/checkorder/finish";
    //查询显示评价列表
    public static final String HTTP_SEL_EVALUATE = HOST_URL + "api/useractivity/cxplbq" + HOST_STAFFID;
    //保存评价
    public static final String HTTP_INS_EVALUATE = HOST_URL + "api/useractivity/bcpl" + HOST_STAFFID;
    //注册  获取品牌
    public static final String HTTP_UUID_PLTENT = HOST_URL + "api/checkorder/sharetenantlist";
    public static final String HTTP_REGISTER_PLTENT = HOST + "admin/tenant/enabledcombolist" + HOST_STAFFID;
    //注册  获取门店
    public static final String HTTP_UUID_STORELIST = HOST_URL + "api/checkorder/sharecompanylist";
    public static final String HTTP_REGISTER_STORELIST = HOST_URL + "api/register/storelist" + HOST_STAFFID;
    //注册
    public static final String HTTP_REGISTER = HOST_URL + "api/useractivity/pltenantuseradd" + HOST_STAFFID;
    //登录
    public static final String HTTP_LOG_IN = HOST_URL + "api/useractivity/userlogin" + HOST_STAFFID;
    //查询全部活动
    public static final String HTTP_SEL_ACTIVITY = HOST_URL + "api/activity/activitylist" + HOST_STAFFID;
    //查询分享次数
    public static final String HTTP_SHARE_TIMES = HOST_URL + "api/useractivity/sharetimes" + HOST_STAFFID;
    //第一次核券人数
    public static final String HTTP_FIRST_TIMES = HOST_URL + "api/useractivity/firstgokan" + HOST_STAFFID;
    //最后一次
    public static final String HTTP_LAST_TIMES = HOST_URL + "api/useractivity/lastgokan" + HOST_STAFFID;
    //结单人数
    public static final String HTTP_OVERORDER_TIMES = HOST_URL + "api/useractivity/oneselfgokan" + HOST_STAFFID;
    //分享次数自增
    public static final String HTTP_SHARE_ADDTIMES = HOST_URL + "api/useractivity/addshare" + HOST_STAFFID;
    //删除客户信息
    public static final String HTTP_HIDDENORDER = HOST_URL + "api/checkorder/delete";
    //转移或共享客户
    public static final String HTTP_SHIFTSTAFF = HOST_URL + "api/checkorder/share";
    //微信Check
    public static final String HTTP_WECHATCHECK = HOST_URL + "api/useractivity/wechatcheck" + HOST_STAFFID;
    //share_card
    public static final String HTTP_CARD_SHARE = HOST_URL + "api/activity/activitywxacode";
    //微信加载
    public static final String HTTP_WECHATINSERT = HOST_URL + "api/useractivity/wechatinsert" + HOST_STAFFID;

}
