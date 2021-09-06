package com.daqsoft.commonnanning.ui.entity;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-28.16:06
 * @since JDK 1.8
 */

public class ScenicVideo {
    /**
     * 图片
     */
    private String coverpictureTwoToOne;
    /**
     * 名字
     */
    private String name;
    /**
     * 视频播放地址
     */
    private String upload;
    /**
     * ID
     */
    private int id;

    public String getUpload() {
        return upload;
    }

    public int getId() {
        return id;
    }

    public String getCoverpictureTwoToOne() {
        return coverpictureTwoToOne;
    }

    public void setCoverpictureTwoToOne(String coverpictureTwoToOne) {
        this.coverpictureTwoToOne = coverpictureTwoToOne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
