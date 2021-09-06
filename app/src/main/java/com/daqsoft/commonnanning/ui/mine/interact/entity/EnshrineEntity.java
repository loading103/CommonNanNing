package com.daqsoft.commonnanning.ui.mine.interact.entity;

/**
 * 我的收藏的实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/8/9 0009
 * @since JDK 1.8
 */

public class EnshrineEntity {


    /**
     * id : 114
     * reId : 932541550912835584
     * content : 黔东南皇朝酒店
     * target : 黔东南皇朝酒店
     * time : 2017-11-29 13:54:37
     * name :
     * byName :
     * price :
     * sourceType : sourceType_2
     * image :
     * title:
     */
    /**
     * 记录id
     */
    private String id;
    /**
     * 收藏记录的ID(资源id)
     */
    private String reId;
    /**
     * 收藏的内容
     */
    private String content;
    /**
     * 收藏的标题
     */
    private String target;
    /**
     * 收藏时间
     */
    private String time;
    /**
     * 收藏人
     */
    private String name;
    /**
     * 被收藏人
     */
    private String byName;
    /**
     * 资源类型
     */
    private String sourceType;
    /**
     * 价格
     */
    private String price;
    /**
     * 封面图
     */
    private String image;
    /**
     * 资源名称
     */
    private String title;

    /**
     * 	资源类型名称
     */
    private String sourceTypeName;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReId() {
        return reId;
    }

    public void setReId(String reId) {
        this.reId = reId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "EnshrineEntity{" +
                "id=" + id +
                ", reId=" + reId +
                ", content='" + content + '\'' +
                ", target='" + target + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", byName='" + byName + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", sourceTypeName='" + sourceTypeName + '\'' +
                '}';
    }
}
