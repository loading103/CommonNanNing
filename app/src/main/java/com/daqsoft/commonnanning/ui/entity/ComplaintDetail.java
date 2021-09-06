package com.daqsoft.commonnanning.ui.entity;

import java.io.Serializable;

/**
 * 投诉详情
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-3.
 * @since JDK 1.8
 */

public class ComplaintDetail implements Serializable {
    /**
     * 处理时间	string	@mock=
     */
    private String clTime;
    /**
     * 处理结果	string	@mock=
     */
    private String clResult;
    /**
     * 投诉人	string	@mock=哇的
     */
    private String name;
    /**
     * 性别	number	@mock=1
     */
    private int sex;
    /**
     * 投诉状态 0：未处理 1：已处理	number	@mock=0
     */
    private int tsState;
    /**
     * 投诉凭证	array<string>	@mock=http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1512550568771/2.bmp,http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1512550729868/IMG_1125.JPG
     */
    private String credentials;
    /**
     * 投诉时间	string	@mock=2018-01-12 16:10:47
     */
    private String createTime;
    /**
     * //投诉编码	string	@mock=TS201801120005
     */
    private String code;
    /**
     * 是否公开 0 ：否 1：是	boolean	@mock=1
     */
    private int isOpen;
    /**
     * 被投诉方	string	@mock=被投诉方
     */
    private String unit;
    /**
     * 投诉事由	string	@mock=投诉事由
     */
    private String reason;
    /**
     * 标题
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClTime() {
        return clTime;
    }

    public void setClTime(String clTime) {
        this.clTime = clTime;
    }

    public String getClResult() {
        return clResult;
    }

    public void setClResult(String clResult) {
        this.clResult = clResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getTsState() {
        return tsState;
    }

    public void setTsState(int tsState) {
        this.tsState = tsState;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getCode() {
        return code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCodes() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int isOpen() {
        return isOpen;
    }

    public void setOpen(int open) {
        isOpen = open;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
