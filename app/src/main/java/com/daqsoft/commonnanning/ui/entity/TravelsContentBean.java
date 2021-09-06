package com.daqsoft.commonnanning.ui.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 游记添加 bean对象
 * 主要是对ListView数据封装
 * 这里不是把这个歌listView当作一个bean对象 是吧listView的一个item当作一个 bean对象
 * 一个item可能是段落标题 标题 图片 等
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-23
 * @since JDK 1.8
 */

public class TravelsContentBean implements Serializable {
    /**
     * 索引
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 断落
     */
    private String stage;
    /**
     * 文本
     */
    private String text;
    /**
     * 图片
     */
    private String img;
    /**
     * 地址
     */
    private String imgAdr;
    /**
     * 是否执行过
     */
    public boolean isStartAnimation = false;
    /**
     * 是否是标题
     */
    public boolean isTitle;
    /**
     * 是否是文本
     */
    public boolean isText;
    /**
     * 是否是段落
     */
    public boolean isStage;
    /**
     * 是否是图片
     */
    public boolean isImg;
    /**
     * 是否是实用信息
     */
    public boolean isInformation;
    /**
     * 是否修改过
     */
    public boolean isupdate = false;
    /**
     * 是否删除过
     */
    public boolean isDelete = false;
    /**
     * 是否显示
     */
    public boolean showprompt = true;
    /**
     * 是否是封面图
     */
    public boolean isCoverImage ;
    //这个list对应的是listView的list数据绑定
    private List<TravelsContentBean> list;

    public boolean isCoverImage() {
        return isCoverImage;
    }

    public void setCoverImage(boolean coverImage) {
        isCoverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<TravelsContentBean> getList() {
        return list;
    }

    public void setList(List<TravelsContentBean> list) {
        this.list = list;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public boolean isStage() {
        return isStage;
    }

    public boolean isText() {
        return isText;
    }

    public boolean isImg() {
        return isImg;
    }

    public String getImgAdr() {
        return imgAdr;
    }

    public void setImgAdr(String imgAdr) {
        this.imgAdr = imgAdr;
    }

    public boolean showprompt() {
        return showprompt;
    }

    public boolean isInformation() {
        return isInformation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 通过返回的数据设置当前bean是什么类型
     *
     * @param type
     */
    //类型（1：段落文字，2：图片，3：段落标题）
    public void setType(int type) {
        if (type == 0) {
            isTitle = true;
        }
        if (type == 1)
            isText = true;
        if (type == 2)
            isImg = true;
        if (type == 3)
            isStage = true;
    }

    public void setTypeContent(int type, String value) {
        if (type == 0)
            setTitle(value);
        if (type == 1)
            setText(value);
        if (type == 2)
            setImg(value);
        if (type == 3)
            setStage(value);
    }


    public String getValue() {
        if (isTitle) {
            return title;
        }
        if (isImg) {
            return img;
        }
        if (isStage) {
            return stage;
        }
        if (isText) {
            return text;
        }
        return "";
    }

    /**
     * 上传类型
     *
     * @return
     */
    public int loadTye() {
        if (isImg) {
            return 2;
        }
        if (isStage) {
            return 3;
        }
        if (isText) {
            return 1;
        }
        return 0;
    }
}
