package com.daqsoft.commonnanning.ui.service.guide.bean;

/**
 * 导游级别类型
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/8/23 0023
 * @since JDK 1.8
 */
public class GuideTypeEntity {
    /**
     * 类型名称
     */
    private String name;
    /**
     * 类型代码
     */
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GuideTypeEntity{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
