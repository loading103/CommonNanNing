package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * 攻略列表
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-12
 * @since JDK 1.8
 */

public class MyStrategyListBean  {


    /**
     * status : 1
     * operateStatus : {"share":51311,"comment":75026,"show":76578,"recordTwo":62667,
     * "enshrine":88703,"recordOne":12584,"thumb":86870}
     * digest : 测试内容689m
     * createTime : 测试内容y354
     * nickname : 测试内容nqxk
     * contentList : [{"content":"测试内容4n7q","id":"测试内容d15d","relationName":"测试内容2r36",
     * "indexOrder":76288,"type":"测试内容bjct"}]
     * head : 测试内容kj5h
     * givepoint : 73811
     * recommend : 80232
     * coverOneToOne : 测试内容24yx
     * coverTwoToOne : 测试内容g570
     * coverTwoToThree : 测试内容77cy
     * coverFourToThree : 测试内容2425
     * author : 测试内容1e26
     * comment : 0
     * updateTime : 2018-04-13 10:12:21
     * content : 段落文字
     * title : 添加游记攻略了
     * id : 68
     * region :
     * sourceName :
     * collection : 0
     * sourceId :
     * cover :
     * viewCount : 0
     * regionName :
     */

    /**
     * 状态 10：草稿状态（可以编辑），其他状态不可编辑
     */
    private int status;
    /**
     * 当前登录用户操作状态
     */
    private OperateStatusBean operateStatus;
    /**
     * 摘要
     */
    private String digest;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 头像
     */
    private String head;
    /**
     * 点赞量
     */
    private int givepoint;
    /**
     * 是否推荐 1：否 0 ：是
     */
    private int recommend;
    /**
     * 封面图1:1
     */
    private String coverOneToOne;
    /**
     * 封面图2:1
     */
    private String coverTwoToOne;
    /**
     * 封面图2:3
     */
    private String coverTwoToThree;
    /**
     * 封面图4:3
     */
    private String coverFourToThree;
    /**
     * 作者
     */
    private String author;
    /**
     * 评论数
     */
    private int comment;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 标题
     */
    private String title;
    /**
     * 攻略id
     */
    private int id;
    /**
     * 关联地区编码
     */
    private String region;
    /**
     * 关联景区名称
     */
    private String sourceName;
    /**
     * 收藏数
     */
    private int collection;
    /**
     * 关联景区id
     */
    private String sourceId;
    /**
     * 封面图
     */
    private String cover;
    /**
     * 浏览数
     */
    private int viewCount;
    /**
     * 关联地区名称
     */
    private String regionName;
    /**
     * 内容集合
     */
    private List<ContentListBean> contentList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OperateStatusBean getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(OperateStatusBean operateStatus) {
        this.operateStatus = operateStatus;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getGivepoint() {
        return givepoint;
    }

    public void setGivepoint(int givepoint) {
        this.givepoint = givepoint;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

    public static class OperateStatusBean {
        /**
         * share : 51311
         * comment : 75026
         * show : 76578
         * recordTwo : 62667
         * enshrine : 88703
         * recordOne : 12584
         * thumb : 86870
         */

        /**
         * 分享，1：是，0：否
         */
        private int share;
        /**
         * 评论，1：是，0：否
         */
        private int comment;
        /**
         * 查看，1：是，0：否
         */
        private int show;
        /**
         * 去过，1：是，0：否
         */
        private int recordTwo;
        /**
         * 收藏，1：是，0：否
         */
        private int enshrine;
        /**
         * 想去，1：是，0：否
         */
        private int recordOne;
        /**
         * 点赞，1：是，0：否
         */
        private int thumb;

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

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        public int getRecordTwo() {
            return recordTwo;
        }

        public void setRecordTwo(int recordTwo) {
            this.recordTwo = recordTwo;
        }

        public int getEnshrine() {
            return enshrine;
        }

        public void setEnshrine(int enshrine) {
            this.enshrine = enshrine;
        }

        public int getRecordOne() {
            return recordOne;
        }

        public void setRecordOne(int recordOne) {
            this.recordOne = recordOne;
        }

        public int getThumb() {
            return thumb;
        }

        public void setThumb(int thumb) {
            this.thumb = thumb;
        }
    }

    public static class ContentListBean {
        /**
         * content : 测试内容4n7q
         * id : 测试内容d15d
         * relationName : 测试内容2r36
         * indexOrder : 76288
         * type : 测试内容bjct
         */

        /**
         * 内容
         */
        private String content;
        /**
         * 类型为4时：攻略id，其他类型：内容id
         */
        private String id;
        /**
         * 拍摄地点
         */
        private String relationName;
        /**
         * 排序号
         */
        private int indexOrder;
        /**
         * 类型：1：段落文字，2：图片，3：段落标题 ，4：攻略标题
         */
        private String type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRelationName() {
            return relationName;
        }

        public void setRelationName(String relationName) {
            this.relationName = relationName;
        }

        public int getIndexOrder() {
            return indexOrder;
        }

        public void setIndexOrder(int indexOrder) {
            this.indexOrder = indexOrder;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
