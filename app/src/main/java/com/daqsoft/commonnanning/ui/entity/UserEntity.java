package com.daqsoft.commonnanning.ui.entity;

/**
 * 用户登录之后实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */
public class UserEntity {
    /**
     * 用户标识token
     */
    String token;
    /**
     * 用户姓名
     */
    String name;
    /**
     * 用户ID
     */
    String id;
    /**
     * 账号
     */
    String account;
    /**
     * 头像图片
     */
    String head;
    /**
     * 小电商ID
     */
    String ucId;
    /**
     * 小电商token
     */
    String ucToken;


    public String getUcId() {
        return ucId;
    }

    public String getUcToken() {
        return ucToken;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getHead() {
        return head;
    }
}
