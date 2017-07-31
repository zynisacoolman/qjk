package com.qijukeji.entityModel;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Pllist {
    private String uuid;
    private String checkorderid;
    private String commentid;
    private String commentmarkid;
    private String content;
    private String type;
    private String content1;//我有话说内容
    private String content2;//标签内容



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

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getCommentmarkid() {
        return commentmarkid;
    }

    public void setCommentmarkid(String commentmarkid) {
        this.commentmarkid = commentmarkid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    @Override
    public String toString() {
        return "Pllist{" +
                "uuid='" + uuid + '\'' +
                ", checkorderid='" + checkorderid + '\'' +
                ", commentid='" + commentid + '\'' +
                ", commentmarkid='" + commentmarkid + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", content1='" + content1 + '\'' +
                ", content2='" + content2 + '\'' +
                '}';
    }
}
