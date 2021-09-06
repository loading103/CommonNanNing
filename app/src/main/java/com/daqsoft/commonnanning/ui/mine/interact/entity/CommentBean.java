package com.daqsoft.commonnanning.ui.mine.interact.entity;

import java.util.List;

/**
 * 评论数据Bean
 *
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2018-7-17
 * @since JDK 1.8.0_171
 */
public class CommentBean {


    /**
     * headPath :
     * target : ssssssss
     * pageview : 0
     * id : 97
     * reId : 56
     * title_app : 测试内容124f
     * time : 2017-11-30 14:14:41
     * name : lingco
     * star : 4
     * comment : fsdfsd
     * useless : 0
     * sourceType : sourceType_8
     * useful : 0
     * pics : 测试内容3glx
     * picArr : ["string1","string2","string3","string4","string5"]
     */
    /**
     * 用户头像
     */
    private String headPath;
    /**
     * 资源名称
     */
    private String target;
    /**
     * 浏览量
     */
    private String pageview;
    /**
     * 记录id
     */
    private String id;
    /**
     * 评论的记录ID
     */
    private String reId;
    /**
     * app评论标题
     */
    private String title_app;
    /**
     * 评论时间
     */
    private String time;
    /**
     * 评论人
     */
    private String name;
    /**
     * 评论星级
     */
    private int star;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 无用数据
     */
    private String useless;
    /**
     * 资源类型
     */
    private String sourceType;
    /**
     * 资源类型名称
     */
    private String sourceTypeName;

    public void setReId(String reId) {
        this.reId = reId;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 有用数
     */

    private String useful;
    /**
     * 被评论记录资源名称
     */
    private String title;
    /**
     * 评论图片，逗号连接
     */
    private String pics;
    /**
     * 评论图片数组
     */
    private List<String> picArr;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPageview() {
        return pageview;
    }

    public void setPageview(String pageview) {
        this.pageview = pageview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReId() {
        return reId;
    }

    public String getTitle_app() {
        return title_app;
    }

    public void setTitle_app(String title_app) {
        this.title_app = title_app;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUseless() {
        return useless;
    }

    public void setUseless(String useless) {
        this.useless = useless;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUseful() {
        return useful;
    }

    public void setUseful(String useful) {
        this.useful = useful;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public List<String> getPicArr() {
        return picArr;
    }

    public void setPicArr(List<String> picArr) {
        this.picArr = picArr;
    }

}
