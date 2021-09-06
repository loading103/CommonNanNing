package com.daqsoft.commonnanning.ui.robot.entity;

/**
 * 消息
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-11-29.20:30
 * @since JDK 1.8
 * String id, final String type, final String name, final String lat, final String lng, final String phone, final
 * String adress
 */

public class MsgEty {
    private String id;
    private String type;
    private String lat;
    private String lng;
    private String adress;
    private String phone;
    private String content;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MsgEty(String id, String type, String lat, String lng, String adress,
                  String phone, String content, String title) {
        this.id = id;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.adress = adress;
        this.phone = phone;
        this.content = content;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
