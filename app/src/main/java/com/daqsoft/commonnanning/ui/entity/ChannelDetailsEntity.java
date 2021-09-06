package com.daqsoft.commonnanning.ui.entity;

/**
 * 栏目详情实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/25 0025
 * @since JDK 1.8
 */
public class ChannelDetailsEntity {
    /**
     * 内容
     */
    String content;
    String url;
    /**
     * 创建时间
     */
    String createTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }
}
