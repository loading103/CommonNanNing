package com.daqsoft.busquery.bean;

/**
 * 拼音
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-6.16:29
 * @since JDK 1.8
 */

public class MyCharEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 是否选中
     */
    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
