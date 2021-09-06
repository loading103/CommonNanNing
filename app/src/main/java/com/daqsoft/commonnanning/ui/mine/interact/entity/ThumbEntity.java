package com.daqsoft.commonnanning.ui.mine.interact.entity;

/**
 * 我的点赞的实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/8/9 0009
 * @since JDK 1.8
 */
public class ThumbEntity  {
    /**
     * 资源类型名称
     */
    private String sourceTypeName;
    /**
     * 内容
     */
    private String content;
    /**
     * 资源id
     */
    private String reId;
    /**
     * 资源名称
     */
    private String title;
    /**
     * 时间
     */
    private String time;
    /**
     * 语言
     */
    private String lang;
    /**
     * 资源封面图
     */
    private String image;
    /**
     * 资源类型
     */
    private String sourceType;
    /**
     * 点赞id
     */
    private String id;
    /**
     * 点赞标题
     */
    private String target;

    /**
     * 站点代码
     */
    private String siteCode;

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReId() {
        return reId;
    }

    public void setReId(String reId) {
        this.reId = reId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}
