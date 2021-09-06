package com.daqsoft.commonnanning.ui.entity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * 投诉列表实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/23 0023
 * @since JDK 1.8
 */
public class ComplaintListEntity implements Serializable {
    /**
     * //	处理时间	string	@mock=$order('','2018-03-30 18:25:00','','','')
     */
    private String clTime;
    /**
     * //处理结果	string	@mock=$order('','亲，已在处理了哈','','','')
     */
    private String clResult;
    /**
     * //标题	string	@mock=$order('投诉标题','7777','哈哈','哈哈','哈哈')
     */
    private String title;
    /**
     * //投诉id	number	@mock=$order(7,6,5,3,4)
     */
    private String id;
    /**
     * //是否处理 1：已处理 0：未处理	number	@mock=$order(0,1,0,0,0)
     */
    private int tsState;
    /**
     * //创建时间	string	@mock=$order('2018-01-12 16:10:47','2018-01-12 11:49:22','2018-01-12
     * 11:17:53','2018-01-12 11:13:54','2018-01-12 11:13:54')
     */
    private String createTime;
    /**
     * //投诉编码	string	@mock=$order('TS201801120005','TS201801120004','TS201801120003',
     * 'TS201801120001','TS201801120002')
     */
    private String code;
    /**
     * //投诉理由	string	@mock=$order('投诉事由','8888','太脏了妹妹的','太脏了妹妹的','太脏了妹妹的')
     */
    private String reason;
    //        private List<String> credentials;    //投诉凭证（图片）	array<string>
    /**
     * //投诉凭证（图片）	array<string>
     */
    private JsonElement credentials;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTsState() {
        return tsState;
    }

    public void setTsState(int tsState) {
        this.tsState = tsState;
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

    public void setCode(String codes) {
        this.code = codes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    //        public List<String> getCredentials() {
    //            return credentials;
    //        }
    public List<String> getCredentials() {
        try {
            Gson gson = new Gson();
            List<String> list = gson.fromJson(credentials,
                    new TypeToken<List<String>>() {
                    }.getType());
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public void setCredentials(JsonElement credentials) {
        this.credentials = credentials;
    }
}
