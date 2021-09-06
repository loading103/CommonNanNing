package com.daqsoft.commonnanning.ui.entity;

/**
 * 首页广告
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-23.11:12
 * @since JDK 1.8
 */

public class IndexBanner {
    private String id;
    private String img;
    private String url;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
