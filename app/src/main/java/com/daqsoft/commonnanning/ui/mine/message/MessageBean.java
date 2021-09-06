package com.daqsoft.commonnanning.ui.mine.message;

/**
 * 我的消息提示的列表实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/12/16 0016
 * @since JDK 1.8
 */
public class MessageBean {
    /**
     * 创建时间
     */
    private String createtime;

    private boolean disable;
    /**
     * 消息ID
     */
    private int id;
    /**
     * 1：系统消息（目前只有系统消息） 2: 系统公告
     */
    private int type;
    /**
     * 内容
     */
    private String content;
    /**
     * 	会员ID
     */
    private long memberid;
    /**
     * （0：已读，1：未读）
     */
    private int status;
    /**
     * 标题
     */
    private String title;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMemberid() {
        return memberid;
    }

    public void setMemberid(long memberid) {
        this.memberid = memberid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
