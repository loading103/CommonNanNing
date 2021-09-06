package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/*
 * -----------------------------------------------------------------
 * @Description:${todo}
 * @Author 董彧傑
 * @CreateDate 2019-3-20 17:09
 *
 * @version 1.0.1
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class FoodEntity {

    /**
     * id : 857
     * name : 火麻仁炖土鸡
     * summary :
     * 火麻生长在得天独厚的天然氧吧里，采用世代相传的传统工艺进行研制，是人们广为食用的佳肴，被称为“长寿汤，长寿麻”。火麻是绿色食品，健康美食。火麻创沌土鸡具有润肠通便，降．三高”（降血压、降血脂、降血糖），美容养颜，滋养补虚，预防心脑血管疾病及杭瘩防癌等作用。
     * cover : http://file.geeker.com.cn/uploadfile/ptisp/img/1536114947644/火麻仁炖土鸡
     * .png?x-oss-process=image/crop,x_0,y_8,w_601,h_338
     * coverOneToOne : http://file.geeker.com.cn/uploadfile/ptisp/img/1536114947644/火麻仁炖土鸡
     * .png?x-oss-process=image/crop,x_105,y_0,w_401,h_401
     * coverTwoToOne : http://file.geeker.com.cn/uploadfile/ptisp/img/1536114947644/火麻仁炖土鸡
     * .png?x-oss-process=image/crop,x_0,y_7,w_601,h_301
     * coverTwoToThree : http://file.geeker.com.cn/uploadfile/ptisp/img/1536114947644/火麻仁炖土鸡
     * .png?x-oss-process=image/crop,x_170,y_0,w_267,h_401
     * coverFourToThree : http://file.geeker.com.cn/uploadfile/ptisp/img/1536114947644/火麻仁炖土鸡
     * .png?x-oss-process=image/crop,x_33,y_0,w_535,h_401
     * region : 450109
     * commentNum : 0
     * commentLevel : 0
     * viewCount : 4
     * recommend : 0
     * diningNum : 0
     * diningList : []
     */

    private String id;
    private String name;
    private String summary;
    private String cover;
    private String coverOneToOne;
    private String coverTwoToOne;
    private String coverTwoToThree;
    private String coverFourToThree;
    private int region;
    private int commentNum;
    private String commentLevel;
    private int viewCount;
    private int recommend;
    private String diningNum;
    private List<?> diningList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverOneToOne() {
        return coverOneToOne;
    }

    public void setCoverOneToOne(String coverOneToOne) {
        this.coverOneToOne = coverOneToOne;
    }

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }

    public String getCoverTwoToThree() {
        return coverTwoToThree;
    }

    public void setCoverTwoToThree(String coverTwoToThree) {
        this.coverTwoToThree = coverTwoToThree;
    }

    public String getCoverFourToThree() {
        return coverFourToThree;
    }

    public void setCoverFourToThree(String coverFourToThree) {
        this.coverFourToThree = coverFourToThree;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getDiningNum() {
        return diningNum;
    }

    public void setDiningNum(String diningNum) {
        this.diningNum = diningNum;
    }

    public List<?> getDiningList() {
        return diningList;
    }

    public void setDiningList(List<?> diningList) {
        this.diningList = diningList;
    }
}
