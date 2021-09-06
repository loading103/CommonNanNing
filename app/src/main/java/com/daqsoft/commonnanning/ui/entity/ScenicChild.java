package com.daqsoft.commonnanning.ui.entity;

/**
 * 子景点
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-28.15:27
 * @since JDK 1.8
 */

public class ScenicChild {
    /**
     * 子景点ID
     */
    private String id;
    /**
     * 景点名称
     */
    private String name;
    /**
     * 图片
     */
    private String coverTwoToOne;
    /**
     * 图片
     */
    private String coverOneToOne;

    public String getCoverOneToOne() {
        return coverOneToOne;
    }

    public void setCoverOneToOne(String coverOneToOne) {
        this.coverOneToOne = coverOneToOne;
    }

    /**
     * 简介
     */
    private String introduce;

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }
}
