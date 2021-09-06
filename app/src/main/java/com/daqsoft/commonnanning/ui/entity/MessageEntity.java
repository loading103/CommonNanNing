package com.daqsoft.commonnanning.ui.entity;

/**
 * 留言列表实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */

public class MessageEntity {


    /**
     * id : 8
     * title : 具体
     * content : T恤具体
     * lyState : 0
     * createTime : 2018-01-15 20:27:01
     */
    /**
     * ID
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 留言状态
     */
    private String lyState;
    /**
     * 创建时间
     */
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLyState() {
        return lyState;
    }

    public void setLyState(String lyState) {
        this.lyState = lyState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
