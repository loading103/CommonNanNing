package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * Created by huangx on 2018/1/16.
 *
 * 留言详情页
 */

public class MessageDetail {


    /**
     * id : 13
     * title : 阿库娅
     * content : 兔兔泸州医学院中央音乐学院现在我现在
     * lyState : 0
     * createTime : 2018-01-16 10:30:44
     * name : 具体
     * phone : 13588858699
     * sex : 0
     * address :
     * credentials : ["http://file.geeker.com.cn/uploadfile/android_common/1516070074679/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070076769/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070077318/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070077498/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070081754/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070083322/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070085024/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070086018/common.jpg","http://file.geeker.com.cn/uploadfile/android_common/1516070086413/common.jpg"]
     * zipCode :
     * clr :
     * clResult :
     * clTime :
     */

    private String id;
    private String title;
    private String content;
    private String lyState;
    private String createTime;
    private String name;
    private String phone;
    private String sex;
    private String address;
    private String zipCode;
    private String clr;
    private String clResult;
    private String clTime;
    private List<String> credentials;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLyState() {
        return lyState;
    }

    public void setLyState(String lyState) {
        this.lyState = lyState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getClr() {
        return clr;
    }

    public void setClr(String clr) {
        this.clr = clr;
    }

    public String getClResult() {
        return clResult;
    }

    public void setClResult(String clResult) {
        this.clResult = clResult;
    }

    public String getClTime() {
        return clTime;
    }

    public void setClTime(String clTime) {
        this.clTime = clTime;
    }

    public List<String> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<String> credentials) {
        if(credentials != null && credentials.size() > 1) {
            this.credentials = credentials;
        }
    }
}
