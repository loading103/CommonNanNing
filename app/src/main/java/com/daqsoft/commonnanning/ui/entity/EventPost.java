package com.daqsoft.commonnanning.ui.entity;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-12-5.13:43
 * @since JDK 1.8
 */

public class EventPost {
    private String msg;

    public EventPost(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
