package com.daqsoft.commonnanning.ui.entity;


import com.example.tomasyb.baselib.adapter.entity.MultiItemEntity;

/**
 * 攻略详情bean
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-10-13.14:20
 * @since JDK 1.8
 */
// 类型：1：段落文字，2：图片，3：段落标题 ，4：攻略标题
public class StraveXqEy implements MultiItemEntity {
    /**
     * 段落文字
     */
    public static final int CONTENTS = 1;
    /**
     * 图片
     */
    public static final int IMG = 2;
    /**
     * 段落标题
     */
    public static final int CONTENTSTITLE = 3;
    /**
     * 攻略标题
     */
    public static final int TRAVETITLE = 4;
    /**
     * 网络标题
     */
    public static final int WEBCONTENT = 5;

    private String content;
    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
