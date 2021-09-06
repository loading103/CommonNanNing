package com.daqsoft.commonnanning.ui.entity;

import java.io.Serializable;

/**
 * 旅游资讯列表实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/26 0026
 * @since JDK 1.8
 */
public class NewsListEntity implements Serializable {
    /**
     * 封面图
     */
    String cover;
    /**
     * 浏览量
     */
    int viewCount;
    /**
     * 标题
     */
    String title;
    /**
     * 发布时间
     */
    String publishtime;
    /**
     * 摘要
     */
    String summary;
    /**
     * 栏目id
     */
    String channelId;
    /**
     * 栏目代码
     */
    String channelCode;
    /**
     * 内容
     */
    String content;
    /**
     * 创建时间
     */
    String createTime;
    /**
     * ID
     */
    String id;
    String coverTwoToOne;

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCover() {
        return cover;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public String getSummary() {
        return summary;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelCode() {
        return channelCode;
    }
}
