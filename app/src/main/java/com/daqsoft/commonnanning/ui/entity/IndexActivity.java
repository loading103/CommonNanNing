package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * 节庆活动
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-20.16:27
 * @since JDK 1.8
 */

public class IndexActivity {

    /**
     * id : 783
     * title : “奇趣狂欢节”燃趣来袭 西安乐华城掀起暑期最后的狂欢盛宴
     * theme : 当乐华城遇上奇趣横生的竞技冲关游戏，欢乐与刺激同在，您还不动心吗?8月25日、26
     * 日，“奇趣狂欢节”携十余项大型互动游戏项目登录西安乐华城·乐华欢乐世界，全天触动您的神经，一边感受跌宕起伏的过山车探秘之旅，一边如勇者一般感受冲关的乐趣。白天全场仅需220
     * 元，夜场99元，您就可以玩转整个乐华欢乐世界，外加参与奇趣狂欢节。如此难得一遇的机会，赶紧约上您的好友闺蜜一起来挑战吧。
     * beginTime :
     * cover :
     * coverOneToOne :
     * coverTwoToOne :
     * coverTwoToThree :
     * coverFourToThree :
     * endTime :
     * createTime : 2019-03-19 02:34:24
     * openTime :
     * viewCount : 0
     * region : 610117
     * givepoint : 0
     * collection : 0
     * share : 0
     * comment : 0
     * travelType :
     * givepointStatus : 0
     * collectionStatus : 0
     * recommended : 0
     * address : 西安乐华欢乐世界
     */

    private int id;
    private String title;
    private String theme;
    private String beginTime;
    private String cover;
    private String coverOneToOne;
    private String coverTwoToOne;
    private String coverTwoToThree;
    private String coverFourToThree;
    private String endTime;
    private String regionName;
    private String createTime;
    private String openTime;
    private int viewCount;
    private String region;
    private int givepoint;
    private int collection;
    private int share;
    private int comment;
    private String travelType;
    private int givepointStatus;
    private int collectionStatus;
    private int recommended;
    private String address;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 详细内容
     */
    private String content;


    public List<ImgsPicsBean> getImgsPics() {
        return imgsPics;
    }

    public void setImgsPics(List<ImgsPicsBean> imgsPics) {
        this.imgsPics = imgsPics;
    }

    private List<ImgsPicsBean> imgsPics;

    public static class ImgsPicsBean{
        private String imgPath;
        private String imgName;

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverOneToOne() {
        return coverOneToOne;
    }

    public void setCoverOneToOne(String coverOneToOne) {
        this.coverOneToOne = coverOneToOne;
    }

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }

    public String getCoverTwoToThree() {
        return coverTwoToThree;
    }

    public void setCoverTwoToThree(String coverTwoToThree) {
        this.coverTwoToThree = coverTwoToThree;
    }

    public String getCoverFourToThree() {
        return coverFourToThree;
    }

    public void setCoverFourToThree(String coverFourToThree) {
        this.coverFourToThree = coverFourToThree;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getGivepoint() {
        return givepoint;
    }

    public void setGivepoint(int givepoint) {
        this.givepoint = givepoint;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public int getGivepointStatus() {
        return givepointStatus;
    }

    public void setGivepointStatus(int givepointStatus) {
        this.givepointStatus = givepointStatus;
    }

    public int getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(int collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public int getRecommended() {
        return recommended;
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
