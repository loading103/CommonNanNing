package com.daqsoft.commonnanning.ui.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 攻略详情
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-13
 * @since JDK 1.8
 */

public class StrategyDetail  {
    /**
     * 是否可编辑
     */
    private int isEdit;
    List<Content> contents;
    /**
     * 浏览量
     */
    private int viewCount;
    /**
     * 是否推荐
     */
    private int recommended;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 评论数
     */
    private int comment;
    /**
     * 分享
     */
    private int share;
    /**
     * 收藏数
     */
    private int collection;
    /**
     * 点赞数
     */
    private int givepoint;
    /**
     * 用户id
     */
    private int authorId;
    /**
     * 来源 1：后台，2：用户上传
     * 来源
     */
    private int source;
    /**
     * 多媒体文件
     */
    private String media;
    /**
     * 内容
     */
    private String content;
    /**
     * 摘要
     */
    private String digest;
    /**
     * 关联景区
     */
    private String sourceId;
    /**
     * 标签
     */
    private String tags;
    /**
     * SEO描述
     */
    private String seoDesc;
    /**
     * SEO关键词
     */
    private String seoKey;
    /**
     * 内容类型
     */
    private String contentType;
    /**
     * 栏目id
     */
    private String channelId;
    /**
     * 栏目组
     */
    private String channelgroup;
    /**
     * 作者
     * 用户昵称
     */
    private String nickname;
    /**
     * 组织机构id
     */
    private String orgId;
    /**
     * 语言
     */
    private String lang;
    /**
     * 站点编码
     */
    private int siteId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 状态
     */
    private int status;
    /**
     * 推荐数
     */
    private int recommend;
    /**
     * 排序
     */
    private String indexOrder;
    /**
     * 封面图
     */
    private String cover;
    /**
     * 用户头像
     */
    private String head;
    /**
     * 标题
     */
    private String title;
    /**
     * 索引 攻略ID
     */
    private int id;
    /**
     * 关联地区名称
     */
    private String regionName;
    /**
     * 关联地区编码
     */
    private String region;
    /**
     * 关联景区名称
     */
    private String sourceName;
    /**
     * 当前用户操作过的状态
     */
    private OprationStatus oprationStatus;

    public OprationStatus getOprationStatus() {
        return oprationStatus;
    }

    public void setOprationStatus(OprationStatus oprationStatus) {
        this.oprationStatus = oprationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getRecommended() {
        return recommended;
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getComment() {
        return comment;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public int getGivepoint() {
        return givepoint;
    }

    public void setGivepoint(int givepoint) {
        this.givepoint = givepoint;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSeoDesc() {
        return seoDesc;
    }

    public void setSeoDesc(String seoDesc) {
        this.seoDesc = seoDesc;
    }

    public String getSeoKey() {
        return seoKey;
    }

    public void setSeoKey(String seoKey) {
        this.seoKey = seoKey;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelgroup() {
        return channelgroup;
    }

    public void setChannelgroup(String channelgroup) {
        this.channelgroup = channelgroup;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(String indexOrder) {
        this.indexOrder = indexOrder;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public List<TravelsContentBean> createTravelsContentBeans() {
        List<TravelsContentBean> list = new ArrayList<TravelsContentBean>();
        if (this.getContents() != null && !this.getContents().isEmpty()) {
            Collections.sort(this.getContents(), new Comparator<Content>() {
                @Override
                public int compare(Content o1, Content o2) {
                    return o1.getIndexOrder() - o2.getIndexOrder();
                }
            });
            for (Content content : this.getContents()) {
                if (content.getIndexOrder() == 0) {
                    TravelsContentBean bean = new TravelsContentBean();
                    bean.isTitle = true;
                    bean.setTitle(content.getContent());
                    bean.setId(content.getId() + "");
                    list.add(bean);
                } else {
                    TravelsContentBean tempBean = new TravelsContentBean();
                    tempBean.setType(content.getType());
                    tempBean.setTypeContent(content.getType(), content.getContent());
                    tempBean.setId(content.getId() + "");
                    list.add(tempBean);
                }
            }
        }
        return list;
    }

    public static class Content {
        // 关联攻略id	number	@mock=$order(33,33,33)
        private int strategyId;
        //	排序	number	@mock=$order(1,2,3)
        private int indexOrder;
        //	类型（1：段落文字，2：图片，3：段落标题）	number	@mock=$order(1,2,3)
        private int type;
        //	内容	string	@mock=$order('测试内容','http:
        // file.geeker.com.cn/uploadfile/userSuggest/1523156343388/.png','测试段落标题')

        private String content;
        //	内容id	number	@mock=$order(5,6,7)
        private int id;

        public int getStrategyId() {
            return strategyId;
        }

        public void setStrategyId(int strategyId) {
            this.strategyId = strategyId;
        }

        public int getIndexOrder() {
            return indexOrder;
        }

        public void setIndexOrder(int indexOrder) {
            this.indexOrder = indexOrder;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class OprationStatus {
        /**
         * 点赞状态
         */
        private int thumb;
        /**
         * 评论状态
         */
        private int comment;
        /**
         * 去过状态
         */
        private int recordTwo;
        /**
         * 浏览数
         */
        private int show;
        /**
         * 收藏状态
         */
        private int enshrine;
        /**
         * 想去状态
         */
        private int recordOne;
        /**
         * 推荐状态
         */
        private int recommend;
        /**
         * 分享状态
         */
        private int share;

        public int getThumb() {
            return thumb;
        }

        public void setThumb(int thumb) {
            this.thumb = thumb;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getRecordTwo() {
            return recordTwo;
        }

        public void setRecordTwo(int recordTwo) {
            this.recordTwo = recordTwo;
        }

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
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

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public int getShare() {
            return share;
        }

        public void setShare(int share) {
            this.share = share;
        }
    }


}
