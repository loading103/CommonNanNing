package com.daqsoft.commonnanning.ui.entity;

/**
 * 720全景
 * @author MouJunFeng
 * @time 2018-4-2
 * @since JDK 1.8
 * @version 1.0.0
 */

public class PanoramaListBean {
    /**
     * 全景路径
     */
    private String url;

    public String getCoverpictureOneToOne() {
        return coverpictureOneToOne;
    }

    public void setCoverpictureOneToOne(String coverpictureOneToOne) {
        this.coverpictureOneToOne = coverpictureOneToOne;
    }

    /**
     * 封面图
     */
    private String coverpictureOneToOne;
    private String coverpictureTowToOne;

    public String getCoverpictureTowToOne() {
        return coverpictureTowToOne;
    }

    public void setCoverpictureTowToOne(String coverpictureTowToOne) {
        this.coverpictureTowToOne = coverpictureTowToOne;
    }

    /**
     * 全景id
     */
    private int id;
    /**
     * 全景名称
     */
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
