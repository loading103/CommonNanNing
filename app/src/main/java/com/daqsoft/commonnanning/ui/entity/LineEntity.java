package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * <p>
 * 线路的实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */

public class LineEntity  {

    /**
     * relatedCount : 0
     * productImages : [{"source":"http://gds.wxtest.daqsoft
     * .com/upload/image/201708/30912a50-c8dp_8-4ece-83b3-6aa1339d932a.jpg","title":""},
     * {"source":"http://gds.wxtest.daqsoft
     * .com/upload/image/201708/890ce3d6-6780-4b25-8104-c0393bfb4e08.jpg","title":""},
     * productId : 846
     * minSellPrice : 0.01
     * daysAndNights : 2天1晚
     * productName : 成都武侯区博物馆熊猫基地市内2日游，纯玩无购物
     * lineCity : 黄平县
     */
    /**
     * 相关游玩景区的门票产品数量
     */
    private int relatedCount;
    /**
     * 线路产品ID
     */
    private int productId;
    private String logoTwoToOne;

    public String getLogoTwoToOne() {
        return logoTwoToOne;
    }

    public void setLogoTwoToOne(String logoTwoToOne) {
        this.logoTwoToOne = logoTwoToOne;
    }

    /**
     * 最小售卖价
     */
    private double minSellPrice;
    /**
     * 线路游的晚数和天数
     */
    private String daysAndNights;
    /**
     * 线路名称
     */
    private String productName;
    /**
     * 出发城市名称
     */
    private String lineCity;
    /**
     * 图片集合
     */
    private List<ProductImagesBean> productImages;
    /**
     * 标签线路
     */
    private String feature;
    /**
     * 评分等级
     */
    private String commentLevel;

    private String source;

    /**
     * 携程ID
     */
    private int forwardId;

    public int getForwardId() {
        return forwardId;
    }

    public void setForwardId(int forwardId) {
        this.forwardId = forwardId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getRelatedCount() {
        return relatedCount;
    }

    public void setRelatedCount(int relatedCount) {
        this.relatedCount = relatedCount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getMinSellPrice() {
        return minSellPrice;
    }

    public void setMinSellPrice(double minSellPrice) {
        this.minSellPrice = minSellPrice;
    }

    public String getDaysAndNights() {
        return daysAndNights;
    }

    public void setDaysAndNights(String daysAndNights) {
        this.daysAndNights = daysAndNights;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLineCity() {
        return lineCity;
    }

    public void setLineCity(String lineCity) {
        this.lineCity = lineCity;
    }

    public List<ProductImagesBean> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImagesBean> productImages) {
        this.productImages = productImages;
    }

    @Override
    public String toString() {
        return "LineEntity{" +
                "relatedCount=" + relatedCount +
                ", productId=" + productId +
                ", minSellPrice=" + minSellPrice +
                ", daysAndNights='" + daysAndNights + '\'' +
                ", productName='" + productName + '\'' +
                ", lineCity='" + lineCity + '\'' +
                ", productImages=" + productImages +
                '}';
    }

    public static class ProductImagesBean {
        /**
         * source : http://gds.wxtest.daqsoft
         * .com/upload/image/201708/30912a50-c8dp_8-4ece-83b3-6aa1339d932a.jpg
         * title :
         */

        private String source;
        private String title;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
