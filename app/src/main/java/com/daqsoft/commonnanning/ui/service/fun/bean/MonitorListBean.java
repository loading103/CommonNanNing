package com.daqsoft.commonnanning.ui.service.fun.bean;

/**
 * 探风景列表实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/12/16 0016
 * @since JDK 1.8
 */
public class MonitorListBean {
    /**
     * 封面图
     */
    private String cover;
    /**
     * 索引
     */
    private String id;
    /**
     * 浏览量
     */
    private String viewNum;
    /**
     * 监控名称
     */
    private String name;
    /**
     * 监控路径
     */
    private String monitorPath;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonitorPath() {
        return monitorPath;
    }

    public void setMonitorPath(String monitorPath) {
        this.monitorPath = monitorPath;
    }
}
