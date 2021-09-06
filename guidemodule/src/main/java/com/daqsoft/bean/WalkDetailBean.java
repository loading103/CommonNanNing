package com.daqsoft.bean;

import com.daqsoft.http.HttpResultBean;

/**
 * 路线详情
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-5-15
 * @since JDK 1.8
 */

public class WalkDetailBean extends HttpResultBean<WalkDetailBean> {
    /**
     * //		number	@mock=$order(2,3,4)
     */
    private int id;
    /**
     * //	纬度	string	@mock=$order('26.58686','26.58854','26.591641')
     */
    private String latitude;
    /**
     * //	经度	string	@mock=$order('107.982044','107.978738','107.996561')
     */
    private String longitude;
    /**
     * //	线路id	number	@mock=$order(5,5,5)
     */
    private int mapGuideLineId;
    /**
     * //	标注	string	@mock=$order('1','','2')
     */
    private String mark;
    /**
     * //	简介	string	@mock=$order('','','')
     */
    private String summary;
    /**
     * //	排序	number	@mock=$order(1,2,3)
     */
    private int indexOrder;
    /**
     * //	辅助点名称
     */
    private String name;
    /**
     * 是否被选中
     */
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getMapGuideLineId() {
        return mapGuideLineId;
    }

    public void setMapGuideLineId(int mapGuideLineId) {
        this.mapGuideLineId = mapGuideLineId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(int indexOrder) {
        this.indexOrder = indexOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
