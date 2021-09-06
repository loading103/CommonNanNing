package com.daqsoft.commonnanning.ui.mine.interact.entity;


/**
 * 景区类型实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @time 2018-7-16
 * @since JDK 1.8
 */
public class SceneryType {
    /**
     * 姓名
     */
    private String name;
    /**
     * 对应key
     */
    private String skey;
    /**
     * 是否选中的状态
     */
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private String nameKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public SceneryType() {
    }

    public SceneryType(String name, String skey, String nameKey) {
        this.name = name;
        this.skey = skey;
        this.nameKey = nameKey;
    }

    public SceneryType(String name, String skey) {
        this.name = name;
        this.skey = skey;
    }

    @Override
    public String toString() {
        return "SceneryType{" +
                "name='" + name + '\'' +
                ", skey='" + skey + '\'' +
                '}';
    }
}
