package com.daqsoft.commonnanning.ui.entity;

/**
 * 第三方登录是否绑定账号的实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */
public class BindPhoneEntity {

    /**
     * 是否已经绑定，0表示已经绑定
     */
    int status;
    /**
     * 用户登录的token
     */
    String token;
    /**
     * 电商ID
     */
    String ucId;
    /**
     * 电商Token
     */
    String ucToken;
    /**
     * 是否已经存在
     */
    int exist;

    public String getUcId() {
        return ucId;
    }

    public String getUcToken() {
        return ucToken;
    }

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public int getExist() {
        return exist;
    }
}
