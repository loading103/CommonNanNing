package com.daqsoft.commonnanning.ui.robot.entity;

/**
 * 机器人Tag
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-24.10:51
 * @since JDK 1.8
 */

public class RobotTag {
    /**
     * 标签名称
     */
    private String name;
    /**
     * 是否选中
     */
    private boolean isSeclted;
    /**
     * id
     */
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSeclted() {
        return isSeclted;
    }

    public void setSeclted(boolean seclted) {
        isSeclted = seclted;
    }
}
