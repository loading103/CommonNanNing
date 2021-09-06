package com.daqsoft.commonnanning.ui.entity;

/**
 * 活动详情实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/26 0026
 * @since JDK 1.8
 */
public class ActivityDetailsEntity {
    /**
     * 活动名称
     */
    String title;
    /**
     * 详细内容
     */
    String content;
    /**
     * 创建时间
     */
    String createTime;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }
}
