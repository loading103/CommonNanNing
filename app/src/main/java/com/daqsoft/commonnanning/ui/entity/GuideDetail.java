package com.daqsoft.commonnanning.ui.entity;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-28.14:56
 * @since JDK 1.8
 */

public class GuideDetail {
    /**
     * 导游导览封面图
     */
    private String coverTwoToOne;
    /**
     * 名字
     */
    private String name;

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
