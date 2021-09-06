package com.daqsoft.commonnanning.ui.entity;

import java.io.Serializable;

/**
 * 功能模块的实体类
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-20.15:50
 * @since JDK 1.8
 */

public class ApplyModuleEntity implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 图标
     */

    private Object img;
    /**
     * 方法标识
     */

    private String method;
    /**
     * 若是链接，则跳转网页的链接
     */

    private String url;
    /**
     * 功能模块的名字
     */

    private String name;
    /**
     * 背景
     */
    private int bagroundId;

    public int getBagroundId() {
        return bagroundId;
    }

    public void setBagroundId(int bagroundId) {
        this.bagroundId = bagroundId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
