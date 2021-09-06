package com.daqsoft.busquery.bean;

/**
 * 城市搜索
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-6.15:27
 * @since JDK 1.8
 */

public class CitySortEntity {

    /**
     * code : VAP
     * name : 北京北
     * id : 1270711
     */
    /**
     * code
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * id
     */
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
