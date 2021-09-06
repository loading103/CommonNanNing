package com.daqsoft.commonnanning.ui.service.guide.bean;

/**
 * 导游列表
 * @author MouJunFeng
 * @time 2018-4-2
 * @since JDK 1.8
 * @version 1.0.0
 */

public class GuideListBean {
    /**
     * 导游ID
     */
    private String id;
    /**
     * 公司
     */
    private String company;
    /**
     * 提供翻译的语种
     */
    private String language;
    /**
     * 证件照
     */
    private String photo;
    /**
     * 名字
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 导游等级
     */
    private String level;
    /**
     * 导游证号
     */
    private String  identification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
