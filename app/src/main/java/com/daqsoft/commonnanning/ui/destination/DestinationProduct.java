package com.daqsoft.commonnanning.ui.destination;


/**
 * 根据经纬度获取一定距离范围内的相关产品数
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/7/23 0023
 * @since JDK 1.8
 */
public class DestinationProduct {
    /**
     * 酒店产品数量
     */
    private int HOTEL;
    /**
     * 购物场所产品数量
     */
    private int SHOPPING;
    /**
     * 餐饮
     */
    private int DINING;
    /**
     * 娱乐
     */
    private int RECREATION;
    /**
     * 景区
     */
    private int SCENERY;
    /**
     * 厕所
     */
    private int TOILET;
    /**
     * 停车场
     */
    private int PARKINGLOT;

    public int getTOILET() {
        return TOILET;
    }

    public int getPARKINGLOT() {
        return PARKINGLOT;
    }

    public int getHOTEL() {
        return HOTEL;
    }

    public void setHOTEL(int HOTEL) {
        this.HOTEL = HOTEL;
    }

    public int getSHOPPING() {
        return SHOPPING;
    }

    public void setSHOPPING(int SHOPPING) {
        this.SHOPPING = SHOPPING;
    }

    public int getDINING() {
        return DINING;
    }

    public void setDINING(int DINING) {
        this.DINING = DINING;
    }

    public int getRECREATION() {
        return RECREATION;
    }

    public void setRECREATION(int RECREATION) {
        this.RECREATION = RECREATION;
    }

    public int getSCENERY() {
        return SCENERY;
    }

    public void setSCENERY(int SCENERY) {
        this.SCENERY = SCENERY;
    }
}
