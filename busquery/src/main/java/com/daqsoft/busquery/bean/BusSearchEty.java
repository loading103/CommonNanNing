package com.daqsoft.busquery.bean;

/**
 * 公交查询字段
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-1-2.16:36
 * @since JDK 1.8
 */

public class BusSearchEty {
    private String name;
    /**
     * 地址
     */
    private String adress;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    // 0:表示起点1:表示终点
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
