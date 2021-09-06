package com.daqsoft.commonnanning.ui.entity;

/**
 * 功能(身边游tag类型的实体类)
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-12-9.13:54
 * @since JDK 1.8
 */

public class FoundNearTagEty {
    /**
     *  类型
     */
    private int mType;
    /**
     * 名字
     */
    private String name;
    /**
     * 图片
     */
    private int image;
    /**
     * 是否是选择
     */
    private boolean isSceted;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSceted() {
        return isSceted;
    }

    public void setSceted(boolean sceted) {
        isSceted = sceted;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
