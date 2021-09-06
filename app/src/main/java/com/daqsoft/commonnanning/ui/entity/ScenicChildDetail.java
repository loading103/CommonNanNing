package com.daqsoft.commonnanning.ui.entity;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-29.16:01
 * @since JDK 1.8
 */

public class ScenicChildDetail {
    /**
     * 描述
     */
    private String describe;
    /**
     * 描述
     */
    private String introduce;
    /**
     * 图片
     */
    private String coverTwoToOne;
    /**
     * 名称
     */
    private String name;

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
