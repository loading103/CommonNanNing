package com.daqsoft.commonnanning.ui.entity;

/**
 * 键值
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-30.15:42
 * @since JDK 1.8
 */

public class KeyValue {
    /**
     * 名字
     */
    private String name;
    /**
     * 图片
     */
    private Integer img;
    /**
     * 编码
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
