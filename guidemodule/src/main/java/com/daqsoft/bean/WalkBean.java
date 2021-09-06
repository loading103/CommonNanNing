package com.daqsoft.bean;

import com.daqsoft.http.HttpResultBean;

/**
 * 线路
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-5-15
 * @since JDK 1.8
 * {"responseTime":1526451444230,"message":"success","code":0,"datas":[{"id":24,"name":"黔东南一日游",
 * "mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},{"id":25,"name":"黔东南二日游",
 * "mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},{"id":26,"name":"黔东南二日游",
 * "mapGuideSetId":4,"summary":"出游人数 :      |满意度 : 98%\r\n目的地 : 中国·四川·阿坝\r\n往返交通 : 汽车往返\r\n旅游报名时间
 * : 省内提前3天，出省提前7天，出国提前20天\r\n发团日期 : 4月11日,4月12日,4月13日...","sourceType":"map_sourceType_line"},
 * {"id":29,"name":"线路1","mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},
 * {"id":30,"name":"线路1","mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},
 * {"id":31,"name":"线路1","mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},
 * {"id":32,"name":"线路2","mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},
 * {"id":33,"name":"线路3","mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},
 * {"id":34,"name":"线路4","mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},
 * {"id":35,"name":"线路5","mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},
 * {"id":36,"name":"线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路",
 * "mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},{"id":37,
 * "name":"线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路","mapGuideSetId":4,"summary":"",
 * "sourceType":"map_sourceType_line"},{"id":41,"name":"线路线路线路线路线路线路线路线路线路线路线路线路线路线路线路",
 * "mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},{"id":42,"name":"手动阀",
 * "mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},{"id":43,"name":"如图",
 * "mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"},{"id":82,"name":"规范",
 * "mapGuideSetId":4,"summary":"个","sourceType":"map_sourceType_line"},{"id":87,"name":"555",
 * "mapGuideSetId":4,"summary":"555","sourceType":"map_sourceType_line"},{"id":132,"name":"测试线路",
 * "mapGuideSetId":4,"summary":"","sourceType":"map_sourceType_line"}]}
 */

public class WalkBean extends HttpResultBean<WalkBean> {
    /**
     * 地图id	number	@mock=$order(2,2)
     */
    private int mapGuideSetId;

    /**
     * number	@mock=$order(5,18)
     */
    private int id;

    /**
     * 简介	string	@mock=$order('全景线路全景线路全景线路全景线路全景线路','包你爽')
     */
    private String summary;

    /**
     * 线路名称	string	@mock=$order('全景线路','迷你路线')
     */
    private String name;
    /**
     * 类型
     */
    private String sourceType;
    /**
     * 线路包含的浏览点
     */
    private int linePointNum;

    public int getLinePointNum() {
        return linePointNum;
    }

    public void setLinePointNum(int linePointNum) {
        this.linePointNum = linePointNum;
    }

    public int getMapGuideSetId() {
        return mapGuideSetId;
    }

    public void setMapGuideSetId(int mapGuideSetId) {
        this.mapGuideSetId = mapGuideSetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
